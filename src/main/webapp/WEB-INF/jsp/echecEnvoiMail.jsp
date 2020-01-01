<%--
Document   : AchatReussi
Created on : 25 févr. 2016, 09:03:36
Author     : Philippe Genoud - UGA Université Grenoble Alpes - Lab. LIG Steamer

Cette page JSP correspond à la page HTML retournée à l'utilisateur lorsque
l'achat a été effectué avec succès.
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="im2ag.m2cci.p01.model.Spectacle"%>
<%@page import="im2ag.m2cci.p01.model.Representation"%>
<%@page import="im2ag.m2cci.p01.model.DossierDAchat"%>
<%@page import="java.util.List"%>
<!DOCTYPE html>
<html>
  <head>
    <%@include file="jspf/commonHead.jspf" %>
  </head>
  <body>
    <%@include file="jspf/header.jspf" %>

    <%
    Spectacle spectacle = (Spectacle) session.getAttribute("spectacle");
    Representation representation = (Representation) session.getAttribute("representation");
    DossierDAchat dossier = (DossierDAchat) session.getAttribute("dossierDAchat");
    %>

    <div class="container">
      <h2>Désolé!<br>Votre achat n'as pu être pris en compte.</h2>
      <p>
        Votre ticket avec <%=dossier.getNbTicket()%> place(s)<br/>
        pour l'épreuve  <strong><%=spectacle.getNom()%></strong><br/>
        du <%=representation.getDateString()%> à <%=representation.getHeure()%> heures<br/>
        n'a pas pu vous être envoyé par email à l'adresse <code>${param.email}</code>
      </p>
      <p>
        <a href="." role="button" class="btn btn-info">Acheter un autre ticket</a>
      </p>
    </div>

    <%@include file="jspf/footer.jspf" %>
  </body>
</html>
