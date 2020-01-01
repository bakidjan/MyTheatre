<%--
Document   : erreurachat
Page d'erreur affichée lorsque l'utilisateur n'a pu effectuer son achat
parceque certaines des places qu'il voulait acheter viennent d'être vendues
(la méthode acheterPlace de la DAO PlaceDAO a levé une exception de type
AchatConcurrentException).
Author     : Philippe Genoud - LIG Steamer - Université Grenoble Alpes
--%>
<%@page contentType="text/html" pageEncoding="UTF-8" isErrorPage="true"%>
<%@page import="im2ag.m2cci.p01.model.Spectacle"%>
<!DOCTYPE html>
<html>
  <head>
    <%@include file="jspf/commonHead.jspf" %>
  </head>
  <body>
    <%@include file="jspf/header.jspf" %>

    <div class="container">
      <h2>Votre achat a échoué</h2>
      <p>
        Certaines des places que vous désiriez acheter pour le spectacle <%=(Spectacle) session.getAttribute("spectacle")%>
        viennent d'être vendues.
      </p>
      <div>
        <ul>
          <li>
            <p><a href="selectionPlaces?idRep=${param.idRep}&noSpectacle=${param.noSpectacle}">Recommencez votre achat</a></p>
          </li>
          <li>
            <p>Abandonner et <a href="." role="button" class="btn btn-info">retourner à l'accueil</a></p>
          </li>
        </ul>
      </div>
    </div>

    <%@include file="jspf/footer.jspf" %>
  </body>
</html>
