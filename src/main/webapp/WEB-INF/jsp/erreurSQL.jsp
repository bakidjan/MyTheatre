<%--
Document   : erreurSQL
Page d'erreur affichée lorsqu'un problème avec la BD est intervenu.
Author     : Philippe Genoud - LIG Steamer - Université Grenoble Alpes
--%>
<%@page import="java.io.PrintWriter"%>
<%@page import="java.io.StringWriter"%>
<%@page contentType="text/html" pageEncoding="UTF-8" isErrorPage="true"%>
<!DOCTYPE html>
<html>
  <head>
    <%@include file="jspf/commonHead.jspf" %>
  </head>
  <body>
    <%@include file="jspf/header.jspf" %>

    <h1>Erreur du serveur CyberTheatre</h1>
    <p>Un problème est survenu avec la base de données</p>
    <p>L'application est momentanément indispnible</p>
    <p>Réessayez plus tard ou contactez l'administrateur de l'application</p>
    <p>
      Message:
      <%=exception.getMessage()%>
    </p>
    <p>
      StackTrace:
      <%
      StringWriter stringWriter = new StringWriter();
      PrintWriter printWriter = new PrintWriter(stringWriter);
      exception.printStackTrace(printWriter);
      out.println(stringWriter);
      printWriter.close();
      stringWriter.close();
      %>
    </p>
    <p><a href=".">retour à l'accueil</a></p>

    <%@include file="jspf/footer.jspf" %>
  </body>
</html>
