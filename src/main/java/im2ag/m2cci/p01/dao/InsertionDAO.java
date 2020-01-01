package im2ag.m2cci.p01.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLIntegrityConstraintViolationException;

import org.mindrot.jbcrypt.BCrypt;

import im2ag.m2cci.p01.model.Reduction;
import im2ag.m2cci.p01.model.DossierDAchat;
import im2ag.m2cci.p01.model.Ticket;

import im2ag.m2cci.p01.dao.DateString;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import java.sql.ResultSet;

/**
*
* @author GP01
*/
public class InsertionDAO {

  /**
  * Insert un Utilisateur
  *
  * @param dataSource data source de la base de donnée
  */
  public static void insertUtilisateur(DataSource dataSource, String login, String email, String motDePasse, String nom, String prenom) throws SQLException {
    try (Connection conn = dataSource.getConnection();) {
      String myQuery = "INSERT INTO LesUtilisateurs (loginUtil, emailUtil, motDePasseUtil, nomUtil, prenomUtil) "
      + "               VALUES (?, ?, ?, ?, ?)";
      PreparedStatement pstmt = conn.prepareStatement(myQuery);
      pstmt.setString(1, login);
      pstmt.setString(2, email);
      pstmt.setString(3, InsertionDAO.hashPassword(motDePasse));
      pstmt.setString(4, nom);
      pstmt.setString(5, prenom);

      pstmt.execute();
    }
  }

  /**
  * Function to hash a password see http://www.mindrot.org/projects/jBCrypt/
  */
  private static String hashPassword(String password) {
    return BCrypt.hashpw(password, BCrypt.gensalt(12));
  }

  /**
  * Insert un Dossier
  * @param dataSource data source de la base de donnée
  */
  public static DossierDAchat insertDossierDAchat (DataSource dataSource, String login, boolean estPaye) throws SQLException {
    DossierDAchat dossier = null;
    try (Connection conn = dataSource.getConnection();) {
      String myQuery = "INSERT INTO LesDossiersDAchat_base (loginUtil, dateDAchatDos, estPayeDos) "
      + "               VALUES (?, ?, ?)";
      PreparedStatement pstmt = conn.prepareStatement(myQuery);
      pstmt.setString(1, login);
      // date
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
      String date = sdf.format(new Date());
      pstmt.setString(2, date);

      pstmt.setInt(3, (estPaye) ? 1 : 0);

      pstmt.execute();

      /* récupère le numéro de dossier */
      myQuery = "SELECT numeroDos, dateDAchatDos, estPayeDos "
      + "        FROM LesDossiersDAchat_base "
      + "        WHERE loginUtil" + ((login == null) ? " IS NULL " : "=? ")
      + "              AND dateDAchatDos=? AND estPayeDos=?";
      pstmt = conn.prepareStatement(myQuery);
      int no = 0;
      if (login != null) {
        pstmt.setString(++no, login);
      }
      pstmt.setString(++no, date);
      pstmt.setInt(++no, (estPaye) ? 1 : 0);

      ResultSet rs = pstmt.executeQuery();
      if (rs.next()) {
        dossier = new DossierDAchat(rs.getInt("numeroDos"), rs.getString("dateDAchatDos"), (rs.getInt("estPayeDos") == 1 ? true : false), 0);
      }
    }
    return dossier;
  }

  /**
  * Insert un Ticket
  * @param dataSource data source de la base de donnée
  */
  public static Ticket insertTicket (DataSource dataSource, int idPla, int idRep, int numeroDos, Reduction reduc) throws SQLException, AchatConcurrentException {
    Ticket ticket = null;
    try (Connection conn = dataSource.getConnection();) {
      conn.setAutoCommit(false);  // début d'une transaction

      String myQuery1 = "INSERT INTO LesTickets(idPla, idRep, numeroDos) "
      + "                VALUES (?, ?, ?)";
      String myQuery2 = "INSERT INTO LesTicketsBeneficiaires(numeroTick, nomReduc) "
      + "                VALUES (?, ?)";
      String myQuery3 = "SELECT numeroTick, nomZone, rangPla, numeroPla"
      + "                FROM LesTickets T"
      + "                  JOIN LesPlaces P"
      + "                    ON T.idPla=P.idPla"
      + "                  JOIN LesZones Z"
      + "                    ON P.numeroZone=Z.numeroZone"
      + "                WHERE T.idPla=? AND idRep=? AND numeroDos=?";
      try (PreparedStatement pstmt = conn.prepareStatement(myQuery1);
      PreparedStatement pstmt3 = conn.prepareStatement(myQuery3);) {
        pstmt.setInt(1, idPla);
        pstmt.setInt(2, idRep);
        pstmt.setInt(3, numeroDos);

        pstmt.execute();

        pstmt3.setInt(1, idPla);
        pstmt3.setInt(2, idRep);
        pstmt3.setInt(3, numeroDos);
        ResultSet rs = pstmt3.executeQuery();
        if (rs.next()) {
          ticket = new Ticket(rs.getInt("numeroTick"), reduc, rs.getInt("numeroPla"), rs.getInt("rangPla"), rs.getString("nomZone"));
        }

        if (reduc != null) {
          try (PreparedStatement pstmt2 = conn.prepareStatement(myQuery2);) {
            pstmt2.setInt(1, ticket.getNumero());
            pstmt2.setString(2, reduc.getNomReduction());
            pstmt2.execute();
          }
        }
        conn.commit();   // valide la transaction
      } catch (SQLException ex) {
        conn.rollback();   // annule la transaction
        // vérifie si l'erreur est liée à la contrainte PK_PlacesVendues
        // dans ce cas une exception AchatConcurrentException est relancée
        switch (conn.getMetaData().getDatabaseProductName()) {
          case "SQLite":
          if (ex.getMessage().contains("PRIMARY KEY constraint failed")) {
            throw new AchatConcurrentException("places déjà achetées ", ex);
          }
          break;
          default:  // testé pour Oracle et PostgreSQL
          ex = ex.getNextException();  // on prend la cause
          if (ex instanceof SQLIntegrityConstraintViolationException || ex.getMessage().contains("pk_placesvendues")) {
            // certains drivers ne supportent pas encore le type SQLIntegrityConstraintViolationException
            throw new AchatConcurrentException("places déjà achetées ", ex);
          }

        }
        // l'exception ne concerne pas la contrainte PK_PlacesVendues
        // elle est relancée telle quelle
        throw ex;

      } finally {
        conn.setAutoCommit(true); // remet la connexion en mode autocommit
      }
    }
    return ticket;
  }
}
