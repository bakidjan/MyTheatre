package im2ag.m2cci.p01.ctrler;

import im2ag.m2cci.p01.services.AccountCreationException;
import im2ag.m2cci.p01.services.LoginManager;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
* Contrôleur gérant la demande de création d'un nouveau compte utlisateur.
*
* @author Philippe Genoud (LIG Steamer - Université Grenoble Alpes)
*/
@WebServlet(name = "AccountCreationCtlrer", urlPatterns = {"/createAccount"})
public class AccountCreationCtlrer extends HttpServlet {

  @Resource(name = "jdbc/MyTheatre")
  DataSource ds;

  /**
  * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
  * methods.
  *
  * @param request servlet request
  * @param response servlet response
  * @throws ServletException if a servlet-specific error occurs
  * @throws IOException if an I/O error occurs
  */
  protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    // récupération des paramètres de la requête
    String proposedId = request.getParameter("id");
    String proposedEmail = request.getParameter("email");
    String passwd = request.getParameter("passwd");
    String passwdConfirm = request.getParameter("passwdConfirm");
    String nom = request.getParameter("nom");
    String prenom = request.getParameter("prenom");

    // appel du service pour verifier si les paramètres de conenxion proposés
    // sont valides
    String error = "";
    try {
      if (proposedId != null && !LoginManager.validateUserId(this.ds, proposedId)) {
        error = "Identifiant déjà utilisé";
      }
      if (proposedEmail != null && !LoginManager.validateUserEmail(this.ds, proposedEmail)) {
        String error2 = "Email déjà utilisé";
        error = (error.isEmpty()) ? error2 : (error + "<br>" + error2);
      }
      if (passwdConfirm != null && !passwdConfirm.equals(passwd)) {
        String error3 = "Les mots de passe ne sont pas identiques";
        error = (error.isEmpty()) ? error3 : (error + "<br>" + error3);
      }
      if (!error.isEmpty()) {
        // les paramètres ne sont pas corrects retour sur la page d'acceuil
        // avec un message d'erreur
        error = "<div class='alert alert-danger'>" + error + "</div>";
        request.setAttribute("errorMessage", error);
        request.getRequestDispatcher("/WEB-INF/jsp/accountCreation.jsp").forward(request, response);
        return;    // le contrôleur a terminé son travail il ne faut surtout pas exécuter ce qui suit
      }
      // les paramètres son corrects (identifiant non encore utilisé et
      // mots de passes identiques on peu créer le compte.
      try {
        LoginManager.createAccount(this.ds, proposedId, proposedEmail, passwd, nom, prenom);
        response.sendRedirect(".");
      } catch (AccountCreationException ex) {
        if (proposedId != null) {
          request.setAttribute("errorMessage", ex.getMessage());
        }
        request.getRequestDispatcher("/WEB-INF/jsp/accountCreation.jsp").forward(request, response);
      }
    } catch (SQLException ex) {
      Logger.getLogger(AccountCreationCtlrer.class.getName()).log(Level.SEVERE, null, ex);
      throw new ServletException(ex);
    }
  }

  // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
  /**
  * Handles the HTTP <code>GET</code> method.
  *
  * @param request servlet request
  * @param response servlet response
  * @throws ServletException if a servlet-specific error occurs
  * @throws IOException if an I/O error occurs
  */
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    processRequest(request, response);
  }

  /**
  * Handles the HTTP <code>POST</code> method.
  *
  * @param request servlet request
  * @param response servlet response
  * @throws ServletException if a servlet-specific error occurs
  * @throws IOException if an I/O error occurs
  */
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    processRequest(request, response);
  }

  /**
  * Returns a short description of the servlet.
  *
  * @return a String containing servlet description
  */
  @Override
  public String getServletInfo() {
    return "Short description";
  }// </editor-fold>

}
