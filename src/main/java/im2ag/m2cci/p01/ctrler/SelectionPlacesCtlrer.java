/*
* Copyright (C) 2017 Philippe GENOUD - Université Grenoble Alpes - Lab LIG-Steamer
*
* This program is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package im2ag.m2cci.p01.ctrler;

import im2ag.m2cci.p01.dao.ConsultationDAO;
import im2ag.m2cci.p01.model.Reduction;
import im2ag.m2cci.p01.model.Representation;
import im2ag.m2cci.p01.model.Spectacle;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

/**
* L'utilisateur a choisi un spectacle. Cette servlet récupère dans la BD les infos
* détaillées associées  à ce spectacle, et redirige l'utilisateur vers la page
* qui permettra de choisir interactivment ses places.
*
* @author Philippe GENOUD - Université Grenoble Alpes - Lab LIG-Steamer
*/
@WebServlet(name = "SelectionPlacesCtlrer", urlPatterns = {"/selectionPlaces"})
public class SelectionPlacesCtlrer extends HttpServlet {

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

    // récupère dans la requête le numéro du representation sélectionné par l'utilisateur
    int noRepresentation = Integer.parseInt(request.getParameter("idRep"));
    // récupère dans la requête le numéro du spectacle sélectionné par l'utilisateur
    int noSpectacle = Integer.parseInt(request.getParameter("noSpectacle"));
    // récupère dans la BD les informations associées au spectacle

    HttpSession session = request.getSession();
    // test de l'utilisateur
    if (session.getAttribute("userId") == null && session.getAttribute("userEmail") == null) {
      request.getRequestDispatcher("/WEB-INF/jsp/choixIdentification.jsp").forward(request, response);
      return;
    }

    try {
      Spectacle spec = ConsultationDAO.getSpectacle(this.ds, noSpectacle);
      Representation rep = spec.getRepresentation(noRepresentation);
      // stocke le spectacle en session pour qu'il puisse être utilisé lors des prochaines requêtes
      session.setAttribute("representation", rep);
      session.setAttribute("spectacle", spec);
      session.setMaxInactiveInterval(60*30); // valide 30 minutes
      // redirection vers la vue affichant le plan du theatre pour que l'utiisateur choisisse ses places
      request.getRequestDispatcher("/WEB-INF/jsp/choixPlaces.jsp").forward(request, response);
    }
    catch (SQLException ex) {
      ex.getMessage();
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
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
  throws ServletException, IOException {
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
  }// </editor-fold>

}
