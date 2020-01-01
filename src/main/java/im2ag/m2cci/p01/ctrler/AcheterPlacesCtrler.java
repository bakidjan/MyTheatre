///*
// * Copyright (C) 2017 Philippe GENOUD - Université Grenoble Alpes - Lab LIG-Steamer
// *
// * This program is free software: you can redistribute it and/or modify
// * it under the terms of the GNU General Public License as published by
// * the Free Software Foundation, either version 3 of the License, or
// * (at your option) any later version.
// *
// * This program is distributed in the hope that it will be useful,
// * but WITHOUT ANY WARRANTY; without even the implied warranty of
// * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// * GNU General Public License for more details.
// *
// * You should have received a copy of the GNU General Public License
// * along with this program.  If not, see <http://www.gnu.org/licenses/>.
// */
package im2ag.m2cci.p01.ctrler;

import im2ag.m2cci.p01.dao.AchatConcurrentException;
import im2ag.m2cci.p01.dao.InsertionDAO;
import im2ag.m2cci.p01.dao.ConsultationDAO;
import im2ag.m2cci.p01.model.Representation;
import im2ag.m2cci.p01.model.DossierDAchat;
import im2ag.m2cci.p01.model.Reduction;
import java.io.IOException;
import java.sql.SQLException;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import java.util.List;
import java.util.ArrayList;

/**
* Enregistre dans la BD les places sélectionnées par l'utilisateur
* et redirige sur la page confirmant l'achat.
*
* @author Philippe GENOUD - Université Grenoble Alpes - Lab LIG-Steamer
*/
@WebServlet(name = "AcheterPlacesCtrler", urlPatterns = {"/acheterPlaces"})
public class AcheterPlacesCtrler extends HttpServlet {

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

    boolean estPaye = Boolean.parseBoolean(request.getParameter("estPaye"));
    String[] places = request.getParameterValues("place");
    String[] reductionTMP = request.getParameterValues("reduction");
    String[] reduction = new String[places.length];
    int nbrTot = 0;
    for (String red : reductionTMP) {
      int location = red.lastIndexOf('_');
      String name = red.substring(0, location);
      int nombre = Integer.parseInt(red.substring(location + 1, red.length()));
      for (int i = 0; i < nombre; i++) {
        reduction[nbrTot++] = name;
      }
    }

    try {
      // récupère dans la session le spectacle précédemment sélectionné
      HttpSession session = request.getSession();
      Representation representation = (Representation) session.getAttribute("representation");

      // on récupere la session  de l'utilisateur
      // test si l'utilisteur est enregistré ou pas :
      DossierDAchat dossier = InsertionDAO.insertDossierDAchat(this.ds, (String) session.getAttribute("userId"), estPaye);
      // récupère les places séléctionnées par l'utilisateur
      for (int i = 0; i < places.length; i++) {
        Reduction red = null;
        if (!reduction[i].equals("none")) {
          red = new Reduction(reduction[i], ConsultationDAO.getValueOfReduction(this.ds, reduction[i]));
        }
        dossier.addTicket(InsertionDAO.insertTicket(this.ds,  Integer.parseInt(places[i]), representation.getId(), dossier.getNumero(), red));
      }
      dossier.setPrixTotal(ConsultationDAO.getPrixTotal(this.ds, dossier.getNumero()));

      session.setAttribute("dossierDAchat", dossier);
      request.getRequestDispatcher("/WEB-INF/jsp/confirmationAchat.jsp").forward(request, response);
      // redirection vers la page confirmant l'achat.
    } catch (SQLException ex) {
      throw new ServletException(ex.getMessage(), ex);
    } catch (AchatConcurrentException ex) {
      request.getRequestDispatcher("/WEB-INF/jsp/echecAchat.jsp").forward(request, response);
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
