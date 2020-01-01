package im2ag.m2cci.p01.ctrler;

import im2ag.m2cci.p01.dao.ConsultationDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
@WebServlet(name = "AccountValidationCtlrer", urlPatterns = {"/validateAccount"})
public class AccountValidationCtlrer extends HttpServlet {

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
    String inputId = request.getParameter("inputId");
    String inputPassword = request.getParameter("inputPassword");
    String inputEmail = request.getParameter("inputEmail");

    // appel du service pour verifier si les paramètres de conenxion proposés
    // sont valides
    String error = "";
    try {
      HttpSession session = request.getSession();
      session.setMaxInactiveInterval(60*30); // valide 30 minutes
      if (inputEmail != null) {
        session.setAttribute("userEmail", inputEmail);
      } else {
        if (inputId == null || !ConsultationDAO.hasLogin(this.ds, inputId)) {
          error = "Identifiant incorrect";
        }
        if (inputPassword == null || !ConsultationDAO.isValidatePassword(this.ds, inputId, inputPassword)) {
          String error2 = "Mot de passe incorrect";
          error = (error.isEmpty()) ? error2 : (error + "<br>" + error2);
        }
        if (!error.isEmpty()) {
          // les paramètres ne sont pas corrects retour sur la page d'acceuil
          // avec un message d'erreur
          error = "<div class='alert alert-danger'>" + error + "</div>";
          request.setAttribute("errorMessage", error);
          request.getRequestDispatcher("/WEB-INF/jsp/accountValidation.jsp").forward(request, response);
          return;    // le contrôleur a terminé son travail il ne faut surtout pas exécuter ce qui suit
        } else {
          session.setAttribute("userId", inputId);
        }
        // les paramètres son corrects (identifiant non encore utilisé et
        // mots de passes identiques on peu créer le compte.
      }

      String requestURL = request.getHeader("Referer");
      if (requestURL.contains("validateAccount")) {
        requestURL = "."; // return to index
      }
      response.sendRedirect(requestURL);

    } catch (SQLException ex) {
      Logger.getLogger(AccountValidationCtlrer.class.getName()).log(Level.SEVERE, null, ex);
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
