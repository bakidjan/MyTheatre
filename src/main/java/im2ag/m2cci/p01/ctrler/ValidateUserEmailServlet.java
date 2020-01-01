package im2ag.m2cci.p01.ctrler;

import im2ag.m2cci.p01.services.LoginManager;
import java.io.IOException;
import java.io.PrintWriter;
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
* Servlet de validation d'une propistion d'identifiant d'utilisateur
*
* Si l'identifiant proposé en paramètre de la requête est déjà utilisé la
* servlet renvoie une code xml <valid>false</valid>, sinon renvoie le code xml
* <valid>true</valid>
*
* @author Philippe Genoud (LIG Steamer - Université Grenoble Alpes)
*/
@WebServlet(name = "ValidateUserEmailServlet", urlPatterns = {"/validateEmail"})
public class ValidateUserEmailServlet extends HttpServlet {

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
    try (PrintWriter out = response.getWriter();) {

      // récupère la valuer du paramètre "email" du formulaire
      String targetEmail = request.getParameter("email");

      //  renvoie un message XML qui sera soit  "<valid>true</valid>" soit
      //  <valid>false</valid>"  selon que email est ou nnom déjà utilisé.
      //  noter que le type MIME du contenu est "text/xml".
      try {
        String rep;
        if (targetEmail != null && LoginManager.validateUserEmail(this.ds, targetEmail)) {
          rep = "<valid>true</valid>";
        } else {
          rep = "<valid>false</valid>";
        }

        response.setContentType("text/html;charset=UTF-8");
        response.setContentType("text/xml");
        response.setHeader("Cache-Control", "no-cache");
        response.getWriter().write(rep);
      } catch (SQLException ex) {
        Logger.getLogger(ValidateUserEmailServlet.class.getName()).log(Level.SEVERE, null, ex);
        throw new ServletException(ex);
      }
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
