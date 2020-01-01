package im2ag.m2cci.p01.services;

//import java.util.HashMap;
//import java.util.Map;

import javax.sql.DataSource;
import java.sql.SQLException;

import im2ag.m2cci.p01.dao.InsertionDAO;
import im2ag.m2cci.p01.dao.ConsultationDAO;


/**
* Simule un service de gestion des comptes utulisateur. Dans un application
* complète il faudra enreigster les utilisateurs dans une base de données ou
* un  annuaire. Mais dans un souci de simplicité ici tout est effectué en
* mémoire.
*
* @author Philippe.Genoud@imag.fr
*/
public class LoginManager {

  /** les informations de login/passwd sont stockées dans une map
  *  ceci dans un but de simplicité, bien entendu dans une application
  *  plus réaliste il faudrait utiliser une table dans une base de données.
  */
  //private static final Map<String,String> ACCOUNTS = new HashMap<>();

  //static {
  //  ACCOUNTS.put("toto38","toto38passwd");
  //  ACCOUNTS.put("titi","titipasswd");
  //}

  public static boolean validateUserId (DataSource ds, String login) throws SQLException {
    return login != null && !ConsultationDAO.hasLogin(ds, login);
  }
  public static boolean validateUserEmail (DataSource ds, String email) throws SQLException {
    return email != null && !ConsultationDAO.hasEmail(ds, email);
  }

  /**
  * crée un compte pour un nouvel user id. Cette méthode est synchronisée
  * car on veut pas que deux créations de compte puissent être
  * effectuées de manière concurrente (on aurait alors le risque , certes
  * infime, mais possible d'avoir dex utilisateurs à avec le même user-id).
  *
  * @param userId l'user id pour le compte à créer
  * @param passwd le mot de passe associé à ce user-id
  * @throws demo.services.AccountCreationException
  */
  public static synchronized void createAccount(DataSource ds, String login, String email, String passwd, String nom, String prenom) throws SQLException, AccountCreationException {
    if (!LoginManager.validateUserId(ds, login)) {
      throw new AccountCreationException("l'identifiant " + login + " est déjà utilisé");
    } else if (!LoginManager.validateUserEmail(ds, email)) {
      throw new AccountCreationException("l'email " + email + " est déjà utilisé");
    } else {
      InsertionDAO.insertUtilisateur(ds, login, email, passwd, nom, prenom);
    }
  }
}
