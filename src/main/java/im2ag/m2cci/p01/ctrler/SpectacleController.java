/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package im2ag.m2cci.p01.ctrler;

import im2ag.m2cci.p01.dao.ConsultationDAO;
import im2ag.m2cci.p01.model.Spectacle;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
* contrôleur pour gérer la liste des representations pour un spectacle
*
* @author GP01
*/
@WebServlet(name = "SpectacleController", urlPatterns = {"/spectacle"})
public class SpectacleController extends HttpServlet {

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
    String date1 = request.getParameter("date1");
    String date2 = request.getParameter("date2");
    String genre[] = request.getParameterValues("genre");
    String spectacleNom [] = request.getParameterValues ("spectacleNom");
    if (genre == null) {
      genre = new String[1];
      genre[0] = "";
    }
    String trancheAge[] = request.getParameterValues("public");
    if (trancheAge == null) {
      trancheAge = new String[1];
      trancheAge[0] = "";
    }

    if (spectacleNom == null) {
        spectacleNom = new String [1];
        spectacleNom[0] = "" ;
    }
    
    try {
      Index rep = null;
      List<Spectacle> listSpec = new ArrayList<>();

      if (date1 == null && date2 == null) {
        int numero = Integer.parseInt(request.getParameter("numero"));
        String date = request.getParameter("date");
        Spectacle repSpectacle = ConsultationDAO.getSpectacleWithDate(ds, numero, date);
        listSpec.add(repSpectacle);
      } else if (date1 != null && date2.isEmpty()) {
        
        
        for (String g : genre) {
          for (String t : trancheAge) {
              for (String ns : spectacleNom)
            listSpec.addAll(ConsultationDAO.getAllRepresentationsWithDate(this.ds, date1, g, t, ns));
          }
        }
      } else {
        for (String g : genre) {
          for (String t : trancheAge) {
              for (String ns : spectacleNom)
            listSpec.addAll(ConsultationDAO.getAllRepresentationsBetweenTwoDates(this.ds, date1, date2, g, t, ns));
          }
        }
      }
      rep = new Index(listSpec);
      if (rep.getNbRepresentations() == 0) {
        request.getRequestDispatcher("/WEB-INF/jsp/pageVide.jsp").forward(request, response);
      } else {
        request.setAttribute("representations", rep);
        request.getRequestDispatcher("/WEB-INF/jsp/representations.jsp").forward(request, response);
      }

    } catch (SQLException ex) {
      Logger.getLogger(SpectacleController.class.getName()).log(Level.SEVERE, null, ex);
      throw new ServletException(ex);
    } catch (IllegalArgumentException ex) {
      request.getRequestDispatcher("/WEB-INF/jsp/choixDatesNonValide.jsp").forward(request, response);
    }
  }

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
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
  throws ServletException, IOException {
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
  }
}
