package im2ag.m2cci.p01.dao;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.ArrayList;
import javax.sql.DataSource;
import java.lang.IllegalArgumentException;
import java.io.InputStream;
import java.io.IOException;
import java.io.ByteArrayOutputStream;
import java.io.StringWriter;
import javax.json.Json;
import javax.json.stream.JsonGenerator;

import im2ag.m2cci.p01.model.Spectacle;
import im2ag.m2cci.p01.model.Representation;
import im2ag.m2cci.p01.dao.DateString;
import im2ag.m2cci.p01.model.Reduction;

import org.mindrot.jbcrypt.BCrypt;

/**
*
* @author GP01
*/
public class ConsultationDAO {

  private static void addImg (Spectacle spec, InputStream inputStream) throws IOException {
    if (inputStream != null) {
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

      byte[] buffer = new byte[1024];
      int bytesRead = -1;
      while ((bytesRead = inputStream.read(buffer)) != -1) {
        outputStream.write(buffer, 0, bytesRead);
      }
      byte[] imageBytes = outputStream.toByteArray();
      spec.setImg(imageBytes);

      outputStream.close();
      inputStream.close();
    }
  }

  /**
  * Liste des spectacles de MyTheatre
  * @param dataSource data source de la base de donnée
  * @return la liste de tous les spectacles de la base de donnée
  */
  public static List<Spectacle> getAllSpectacles (DataSource dataSource) throws SQLException, IOException {
    List<Spectacle> theatre = new ArrayList<>();
    try (Connection conn = dataSource.getConnection();) {
      Statement stmt = conn.createStatement();
      String myQuery = "SELECT numeroSpec, nomSpec, imgSpec " +
      "                 FROM LesSpectacles " +
      "                 ORDER BY numeroSpec";
      ResultSet rs = stmt.executeQuery(myQuery);

      while (rs.next()) {
        Spectacle spectacle = null;
        spectacle = new Spectacle(rs.getInt("numeroSpec"), rs.getString("nomSpec"));
        ConsultationDAO.addImg(spectacle, rs.getBinaryStream("imgSpec"));
        theatre.add(spectacle);
      }
    }
    return theatre;
  }

  /**
  * Liste des spectacles de MyTheatre avec sélection du genre et de la tranche d'age
  * @param dataSource data source de la base de donnée
  * @param genre genre du spectacle
  * @param trancheAge tranche d'age du spectacle
  * @return la liste de tous les spectacles de la base de donnée
  */
  public static List<Spectacle> getAllSpectacles (DataSource dataSource, String genre, int trancheAge) throws SQLException {
    if (!ConsultationDAO.isGenre(dataSource, genre)) {
      throw new IllegalArgumentException("La base de donnée ne contient pas le genre " + genre);
    }
    if (!ConsultationDAO.isAgeClassification(dataSource, trancheAge)) {
      throw new IllegalArgumentException("La base de donnée ne contient pas la classification d'âge " + trancheAge);
    }

    List<Spectacle> theatre = new ArrayList<>();
    try (Connection conn = dataSource.getConnection();) {
      String myQuery = "SELECT numeroSpec, nomSpec " +
      "                 FROM LesSpectacles " +
      "                 WHERE genreSpec=? AND trancheAgeSpec=? " +
      "                 ORDER BY numeroSpec";

      PreparedStatement stmt = conn.prepareStatement(myQuery);
      stmt.setString(1, genre);
      stmt.setInt(2, trancheAge);
      ResultSet rs = stmt.executeQuery();

      while (rs.next()) {
        Spectacle spectacle = new Spectacle(rs.getInt("numeroSpec"), rs.getString("nomSpec"));
        theatre.add(spectacle);
      }
    }

    return theatre;
  }




  /**
  * liste les genres de la base de donnée
  */
  public static List<String> getAllGenres (DataSource dataSource) throws SQLException {
    List<String> allGenres = new ArrayList<>();
    try (Connection conn = dataSource.getConnection();) {
      Statement stmt = conn.createStatement();
      String myQuery = "SELECT DISTINCT genreSpec " +
      "                 FROM LesSpectacles";
      ResultSet rs = stmt.executeQuery(myQuery);

      while (rs.next()) {
        allGenres.add(rs.getString("genreSpec"));
      }
    }
    return allGenres;
  }

  private static boolean isGenre (DataSource dataSource, String genre) throws SQLException {
    List<String> allGenres = ConsultationDAO.getAllGenres(dataSource);
    return allGenres.contains(genre);
  }

  /**
  * liste les ages de la base de donnée
  */
  public static List<Integer> getAllAgeClassifications (DataSource dataSource) throws SQLException {
    List<Integer> allCat = new ArrayList<>();
    try (Connection conn = dataSource.getConnection();) {
      Statement stmt = conn.createStatement();
      String myQuery = "SELECT DISTINCT trancheAgeSpec " +
      "                 FROM LesSpectacles";
      ResultSet rs = stmt.executeQuery(myQuery);

      while (rs.next()) {
        allCat.add(rs.getInt("trancheAgeSpec"));
      }
    }
    return allCat;
  }

  private static boolean isAgeClassification (DataSource dataSource, int trancheAge) throws SQLException {
    List<Integer> allCat = ConsultationDAO.getAllAgeClassifications(dataSource);
    return allCat.contains(trancheAge);
  }

  /* construct the list with the result of the query */
  private static void makeListWithRepresentations (List<Spectacle> theatre, ResultSet rs) throws SQLException, IOException {
    if (theatre == null) {
      theatre = new ArrayList<>();
    }
    if (!theatre.isEmpty()) {
      theatre.clear();
    }
    Spectacle spectacle = null;
    while (rs.next()) {
      spectacle = new Spectacle(rs.getInt("numeroSpec"), rs.getString("nomSpec"), rs.getString("genreSpec"), rs.getInt("trancheAgeSpec"), rs.getString("nomAut"), rs.getString("prenomAut"));
      ConsultationDAO.addImg(spectacle, rs.getBinaryStream("imgSpec"));
      if (!theatre.contains(spectacle)) {
        theatre.add(spectacle);
      }
      ConsultationDAO.addRepresentation(theatre.get(theatre.indexOf(spectacle)), rs.getInt("idRep"), rs.getString("dateRep"), rs.getInt("heureRep"), rs.getInt("nbPlacesDispoRep"), rs.getInt("promotionRep"));
    }
  }

  /**
  * Liste des spectacles de MyTheatre avec les représentations
  *
  * @param dataSource data source de la base de donnée
  * @return la liste de tous les spectacles avec représentation de la base de
  * donnée
  */
  public static List<Spectacle> getAllRepresentations(DataSource dataSource) throws SQLException, IOException {
    List<Spectacle> theatre = new ArrayList<>();
    try (Connection conn = dataSource.getConnection();) {
      Statement stmt = conn.createStatement();
      String myQuery = "SELECT S.numeroSpec, nomSpec, imgSpec, idRep, dateRep, heureRep, nbPlacesDispoRep, genreSpec, trancheAgeSpec, promotionRep, nomAut, prenomAut "
      + "               FROM LesSpectacles S "
      + "                  JOIN LesRepresentations R "
      + "                     ON S.numeroSpec=R.numeroSpec "
      + "               ORDER BY dateRep";
      ResultSet rs = stmt.executeQuery(myQuery);
      ConsultationDAO.makeListWithRepresentations(theatre, rs);
    }

    return theatre;
  }

  /**
  * Liste des spectacles de MyTheatre avec les représentations pour une date
  *
  * @param dataSource data source de la base de donnée
  * @param date date sélectionnée (format yyyy-mm-dd)
  * @return la liste de tous les spectacles avec représentation de la base de
  * donnée
  */
  public static List<Spectacle> getAllRepresentationsWithDate(DataSource dataSource, String date, String genre, String trancheAge, String nomSpectacle) throws SQLException, IllegalArgumentException, IOException {
    List<Spectacle> theatre = new ArrayList<>();
    try (Connection conn = dataSource.getConnection();) {
      String myQuery = "SELECT S.numeroSpec, nomSpec, imgSpec, idRep, dateRep, heureRep, nbPlacesDispoRep, genreSpec, trancheAgeSpec, promotionRep, nomAut, prenomAut "
      + "               FROM LesSpectacles S "
      + "                  JOIN LesRepresentations R "
      + "                     ON S.numeroSpec=R.numeroSpec "
      + "               WHERE dateRep=? "
      + ((genre.isEmpty()) ? "" : " AND genreSpec=? ")
      + ((trancheAge.isEmpty()) ? "" : " AND trancheAgeSpec=? ")
      + (nomSpectacle.isEmpty() ? "" : " AND nomSpec=?")
      + "               ORDER BY dateRep";

      PreparedStatement stmt = conn.prepareStatement(myQuery);

      DateString tmpDate = new DateString(date); // test format
      int no = 0;
      stmt.setString(++no, date);
      if (!genre.isEmpty()) {
        stmt.setString(++no, genre);
      }
      if (!trancheAge.isEmpty()) {
        stmt.setString(++no, trancheAge);
      }

      if (!nomSpectacle.isEmpty()) {
        stmt.setString(++no, nomSpectacle);
      }
      ResultSet rs = stmt.executeQuery();
      ConsultationDAO.makeListWithRepresentations(theatre, rs);
    }

    return theatre;
  }

  public static List<Spectacle> getAllRepresentationsBetweenTwoDates(DataSource dataSource, String date1, String date2, String genre, String trancheAge, String nomSpectacle) throws SQLException, IllegalArgumentException, IOException {
    DateString tmpDate1 = new DateString(date1); // test format
    if (tmpDate1.after(date2)) {
      throw new IllegalArgumentException("Veuillez inverser \"date1\" et \"date2\"");
    }

    List<Spectacle> theatre = new ArrayList<>();
    try (Connection conn = dataSource.getConnection();) {
      String myQuery = "SELECT S.numeroSpec, nomSpec, imgSpec, idRep, dateRep, heureRep, nbPlacesDispoRep, genreSpec, trancheAgeSpec, promotionRep, nomAut, prenomAut "
      + "               FROM LesSpectacles S "
      + "                  JOIN LesRepresentations R "
      + "                     ON S.numeroSpec=R.numeroSpec "
      + "               WHERE strftime('%Y-%m-%d', dateRep) BETWEEN ? AND ? "
      + ((genre.isEmpty()) ? "" : " AND genreSpec=? ")
      + ((trancheAge.isEmpty()) ? "" : " AND trancheAgeSpec=? ")
      + (nomSpectacle.isEmpty() ? "" : " AND nomSpec=?")
      + "               ORDER BY dateRep";


      PreparedStatement stmt = conn.prepareStatement(myQuery);
      int no = 0;
      stmt.setString(++no, date1);
      stmt.setString(++no, date2);
      if (!genre.isEmpty()) {
        stmt.setString(++no, genre);
      }
      if (!trancheAge.isEmpty()) {
        stmt.setString(++no, trancheAge);
      }
      if (!nomSpectacle.isEmpty()) {
        stmt.setString(++no, nomSpectacle);
      }

      ResultSet rs = stmt.executeQuery();
      ConsultationDAO.makeListWithRepresentations(theatre, rs);
    }

    return theatre;
  }

  /**
  * Spectacle avec la liste des représentation
  *
  * @param dataSource data source de la base de donnée
  * @param numeroRepresentation le numéro de la representation
  * @return la liste de toutes les représentations du spectacle
  * @throws java.sql.SQLException
  */

  public static Spectacle getSpectacle(DataSource dataSource, int numeroSpectacle) throws SQLException {
    Spectacle spectacle = null;
    try (Connection conn = dataSource.getConnection();) {
      String myQuery = "SELECT S.numeroSpec, nomSpec, idRep, dateRep, heureRep, nbPlacesDispoRep, genreSpec, trancheAgeSpec, promotionRep, nomAut, prenomAut "
      + "               FROM LesSpectacles S "
      + "                  JOIN LesRepresentations R "
      + "                     ON S.numeroSpec=R.numeroSpec "
      + "               WHERE S.numeroSpec=? "
      + "               ORDER BY S.numeroSpec";

      PreparedStatement stmt = conn.prepareStatement(myQuery);
      stmt.setInt(1, numeroSpectacle);
      ResultSet rs = stmt.executeQuery();

      while (rs.next()) {
        if (spectacle == null) {
          spectacle = new Spectacle(rs.getInt("numeroSpec"), rs.getString("nomSpec"), rs.getString("genreSpec"), rs.getInt("trancheAgeSpec"), rs.getString("nomAut"), rs.getString("prenomAut"));
        }
        ConsultationDAO.addRepresentation(spectacle, rs.getInt("idRep"), rs.getString("dateRep"), rs.getInt("heureRep"), rs.getInt("nbPlacesDispoRep"), rs.getInt("promotionRep"));
      }
    }

    return spectacle;
  }

  public static Spectacle getSpectacleWithDate(DataSource dataSource, int numero, String date) throws SQLException, IllegalArgumentException {
    DateString tmpDate = new DateString(date); // test format
    Spectacle spectacle = null;
    try (Connection conn = dataSource.getConnection();) {
      String myQuery = "SELECT S.numeroSpec, nomSpec, idRep, dateRep, heureRep, nbPlacesDispoRep, genreSpec, trancheAgeSpec, promotionRep, nomAut, prenomAut "
      + "               FROM LesSpectacles S "
      + "                  JOIN LesRepresentations R "
      + "                     ON S.numeroSpec=R.numeroSpec "
      + "               WHERE S.numeroSpec=? AND dateRep >=? "
      + "               ORDER BY S.numeroSpec";

      PreparedStatement stmt = conn.prepareStatement(myQuery);
      stmt.setInt(1, numero);
      stmt.setString(2, date);
      ResultSet rs = stmt.executeQuery();

      while (rs.next()) {
        if (spectacle == null) {
          spectacle = new Spectacle(rs.getInt("numeroSpec"), rs.getString("nomSpec"), rs.getString("genreSpec"), rs.getInt("trancheAgeSpec"), rs.getString("nomAut"), rs.getString("prenomAut"));
        }
        ConsultationDAO.addRepresentation(spectacle, rs.getInt("idRep"), rs.getString("dateRep"), rs.getInt("heureRep"), rs.getInt("nbPlacesDispoRep"), rs.getInt("promotionRep"));
      }
    }

    return spectacle;
  }

  /** add a representation to the spectacle */
  private static void addRepresentation(Spectacle spectacle, int id, String date, int heure, int nbPlacesDispo, int promotion) throws IllegalArgumentException {
    DateString tmpDate = new DateString(date); // test format
    spectacle.addRepresentation(id, date, heure, nbPlacesDispo, promotion);
  }

  /**
  * Récupère de la DB les numeros de zones occupées par le spectacle
  * Ajoute les zones à l'objet spectacle
  */
  public static void setZonesOccupees (DataSource dataSource, Spectacle spectacle) throws SQLException {
    try (Connection conn = dataSource.getConnection();) {
      String myQuery = "SELECT numeroZone "
      + "               FROM LesOccupations "
      + "               WHERE numeroSpec=? ";

      PreparedStatement stmt = conn.prepareStatement(myQuery);
      stmt.setInt(1, spectacle.getNumero());
      ResultSet rs = stmt.executeQuery();

      while (rs.next()) {
        spectacle.addZoneOccupee(rs.getInt("numeroZone"));
      }
    }
  }

  /**
  * test si un login est présent dans la base de donnée
  */
  public static boolean hasLogin (DataSource dataSource, String login) throws SQLException {
    boolean isPresent;
    try (Connection conn = dataSource.getConnection();) {
      String myQuery = "SELECT loginUtil "
      + "               FROM LesUtilisateurs "
      + "               WHERE loginUtil=?";
      PreparedStatement stmt = conn.prepareStatement(myQuery);
      stmt.setString(1, login);
      ResultSet rs = stmt.executeQuery();
      isPresent = rs.next();
    }
    return isPresent;
  }
  /* return l'email */
  public static String getUserEmail (DataSource dataSource, String login) throws SQLException {
    String userEmail = "";
    try (Connection conn = dataSource.getConnection();) {
      String myQuery = "SELECT emailUtil "
      + "               FROM LesUtilisateurs "
      + "               WHERE loginUtil=?";
      PreparedStatement stmt = conn.prepareStatement(myQuery);
      stmt.setString(1, login);
      ResultSet rs = stmt.executeQuery();
      if (rs.next()) {
        userEmail = rs.getString("emailUtil");
      }
    }
    return userEmail;
  }
  /**
  * test si un email est présent dans la base de donnée
  */
  public static boolean hasEmail (DataSource dataSource, String email) throws SQLException {
    boolean isPresent = false;
    try (Connection conn = dataSource.getConnection();) {
      String myQuery = "SELECT emailUtil "
      + "               FROM LesUtilisateurs "
      + "               WHERE emailUtil=?";
      PreparedStatement stmt = conn.prepareStatement(myQuery);
      stmt.setString(1, email);
      ResultSet rs = stmt.executeQuery();
      isPresent = rs.next();
    }
    return isPresent;
  }
  /**
  * test si un mot de passe est valide
  */
  public static boolean isValidatePassword (DataSource dataSource, String login, String password) throws SQLException {
    String hashPassword = null;
    try (Connection conn = dataSource.getConnection();) {
      String myQuery = "SELECT motDePasseUtil "
      + "               FROM LesUtilisateurs "
      + "               WHERE loginUtil=?";
      PreparedStatement stmt = conn.prepareStatement(myQuery);
      stmt.setString(1, login);
      ResultSet rs = stmt.executeQuery();

      while (rs.next()) {
        hashPassword = rs.getString("motDePasseUtil");
      }
    }
    return (hashPassword == null) ? false : BCrypt.checkpw(password, hashPassword);
  }

  /**
  * recherche pour un spectacle donné la liste des places qui ont déja été
  * vendues et la retourne sous la forme d'une chaîne JSON.
  *
  * Par exemple si les places vendues sont les places de numéros 1, 2, 43 et
  * 154 le code JSON retournée au client sera :
  * <pre>
  * {"placesVendues":[
  *    {"placeId":1,"rang":1,"colonne":3},
  *    {"placeId":2,"rang":1,"colonne":4},
  *    {"placeId":43,"rang":2,"colonne":19},
  *    {"placeId":71,"rang":3,"colonne":19},{"placeId":100,"rang":4,"colonne":20},
  *    {"placeId":154,"rang":7,"colonne":15},
  *   ]
  * }
  * </pre>
  *     *
  * @param ds la source de données pour les connexions JDBC
  * @param spectacleId l'identifiant du spectacle
  * @return la chaîne JSON
  * @throws SQLException si problème avec JDBC
  */
  public static String placesVenduesAsJSON(DataSource ds, int representationId) throws SQLException {
    String myQuery = "SELECT numeroZone, idPla, rangPla, numeroPla "
    + "               FROM LesPlaces "
    + "               WHERE idPla IN (SELECT idPla "
    + "                               FROM LesRepresentations R "
    + "                                 JOIN LesSpectacles S "
    + "                                   ON R.numeroSpec=S.numeroSpec "
    + "                                 JOIN LesOccupations O "
    + "                                   ON S.numeroSpec=O.numeroSpec "
    + "                                 JOIN LesPlaces P "
    + "                                   ON P.numeroZone=O.numeroZone "
    + "                               WHERE R.idRep=?) "
    + "               OR idPla IN (SELECT idPla "
    + "                            FROM LesTickets "
    + "                            WHERE idRep=?);";

    try (Connection conn = ds.getConnection();
    PreparedStatement pstmt = conn.prepareStatement(myQuery);) {
      pstmt.setInt(1, representationId);
      pstmt.setInt(2, representationId);
      ResultSet rs = pstmt.executeQuery();
      StringWriter sw = new StringWriter();
      try (JsonGenerator gen = Json.createGenerator(sw)) {
        gen.writeStartObject()
        .writeStartArray("placesVendues");
        while (rs.next()) {
          gen.writeStartObject()
          .write("numeroZone", rs.getInt("numeroZone"))
          .write("placeId", rs.getInt("idPla"))
          .write("rang", rs.getInt("rangPla"))
          .write("colonne", rs.getInt("numeroPla"))
          .writeEnd();
        }
        gen.writeEnd()
        .writeEnd();
      }
      return sw.toString();
    }
  }
  public static List<Reduction> getAllReductions (DataSource dataSource) throws SQLException {
    List<Reduction> allReductions = new ArrayList<>();
    try (Connection conn = dataSource.getConnection();) {
      Statement stmt = conn.createStatement();
      String myQuery = "SELECT nomReduc, tauxReduc " +
      "                 FROM LesReductions";
      ResultSet rs = stmt.executeQuery(myQuery);

      while (rs.next()) {
        allReductions.add(new Reduction(rs.getString("nomReduc"), rs.getInt("tauxReduc")));
      }
    }
    return allReductions;
  }

  public static int getPrixTotal (DataSource dataSource, int numeroDossier) throws SQLException {
    int prix = 0;
    String myQuery = "SELECT prixDos "
    + "               FROM LesDossiersDAchat "
    + "               WHERE numeroDos=?";
    try (Connection conn = dataSource.getConnection();
    PreparedStatement pstmt = conn.prepareStatement(myQuery);) {
      pstmt.setInt(1, numeroDossier);

      ResultSet rs = pstmt.executeQuery();

      if (rs.next()) {
        prix = rs.getInt("prixDos");
      }
    }
    return prix;
  }

  public static int getValueOfReduction (DataSource dataSource, String nom) throws SQLException {
    int val = 0;
    String myQuery = "SELECT tauxReduc "
    + "              FROM LesReductions "
    + "              WHERE nomReduc=?";
    try (Connection conn = dataSource.getConnection();
    PreparedStatement pstmt = conn.prepareStatement(myQuery);) {
      pstmt.setString(1, nom);

      ResultSet rs = pstmt.executeQuery();

      if (rs.next()) {
        val = rs.getInt("tauxReduc");
      }
    }
    return val;
  }

}
