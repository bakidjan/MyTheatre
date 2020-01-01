<!--
Document   : spectactles_events
Created on : Feb 21, 2019, 10:01:07 AM
Author     : mehdi
-->

<%@page import="im2ag.m2cci.p01.model.Representation"%>
<%@page import="im2ag.m2cci.p01.ctrler.Index"%>
<%@page import="im2ag.m2cci.p01.model.Spectacle"%>
<%@page import="im2ag.m2cci.p01.model.Representation"%>
<%@page import="java.util.Iterator"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <%@include file="jspf/commonHead.jspf" %>
  </head>
  <body>
    <div id="backtop" class="btn btn-info">&#9650;</div>

    <!-- button to display otherRow -->
    <%if (request.getParameter("numero") != null) {%>
    <button id="btnShowRep" class="btn btn-info" onclick="showRep()">Voir d'autres représentations</button>
    <%}%>

    <%@include file="jspf/header.jspf" %>

    <%
    Index index = (Index) request.getAttribute("representations");
    Iterator<Representation> repIt = index.iterator();
    %>

    <div class="container">

      <div class="row">
        <div class="col-md-1">
          <a role="button" href="." class="btn btn-info">
            <i class="fas fa-home"></i>
          </a>
        </div>
        <div class="col-md-8">
          <h2>Liste des représentations</h2>
        </div>
        <div class="col-md-3">
          <div class="row float-right">
            <a id="shareBtn" href="https://api.addthis.com/oexchange/0.8/forward/email/offer?url=" target="_blank" class="btn btn-info" role="button">
              <i class="fas fa-envelope"></i> Envoyer à un ami
            </a>

          </div>
        </div>
      </div>

      <div class="row">
        <table class="table table-hover">
          <tr>
            <th>Spectacle</th>
            <th>Auteur</th>
            <th>Date</th>
            <th>Heure</th>
            <th>Places restantes</th>
            <th>Genre</th>
            <th>Public</th>
            <th>Promotion</th>
          </tr>
          <%
          int noRow = 0;
          while (repIt.hasNext()) {
            Representation rep = repIt.next();
            Spectacle spec = index.getSpectacle(rep);%>
            <tr class="<%=(request.getParameter("numero") == null || ++noRow == 1) ? "firstRow" : "otherRow"%>">
            <td> <%=spec.getNom()%> </td>
            <td> <%=spec.getAuteur()%></td>
            <td> <%=rep.getDateString()%> </td>
            <td> <%=rep.getHeure()%>:00 </td>
            <td> <%=rep.getNbPlacesDispo()%> </td>
            <td> <%=Character.toUpperCase(spec.getGenre().charAt(0)) + spec.getGenre().substring(1)%></td>
            <td> <%=(spec.getTrancheAge() == 0) ? "tout public" : "moins de " + spec.getTrancheAge() + " ans"%></td>
            <td><%=rep.getPromotion()%>% </td>
            <td class="hide-td"><a class="btn btn-info btn-validRep" role="button" href="./selectionPlaces?idRep=<%=rep.getId()%>&noSpectacle=<%=spec.getNumero()%>">Acheter</a></td>
          </tr>
          <%}%>
        </table>
      </div>

      <%@include file="jspf/footer.jspf" %>

      <script>
      /* hide btn valid */
      $('tr').click(function () {
        $(this).addClass('selected').siblings().removeClass('selected');
        $(this).children('td.hide-td').css('visibility', 'visible');
        $('tr').not(this).children('td.hide-td').css('visibility', 'hidden');
      });


      /* set new url to share btn */
      let btn = document.getElementById('shareBtn');
      let docURL = document.URL;
      let docURLr = docURL.replace(/&/g, '%26');
      let url = btn.href + docURLr + '&pubid=ra-42fed1e187bae420&title=Ma%20liste%20de%20choix&ct=1';
      btn.href = url;

      /* show other rep (otherRow) */
      let button = $('#btnShowRep');
      button.css('display', 'block');
      function showRep () {
        $('.otherRow').css('display', 'table-row');
        button.css('display', 'none');
      }
      </script>

    </body>
  </html>
