<%--
Document   : achat
Confirmation de l'achat si celui-ci a réussi.
Author     : Philippe GENOUD - Université Grenoble Alpes - Lab LIG-Steamer
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="im2ag.m2cci.p01.dao.ConsultationDAO"%>
<%@page import="im2ag.m2cci.p01.model.Spectacle"%>
<!DOCTYPE html>
<html>
  <head>
    <%@include file="jspf/commonHead.jspf" %>
  </head>
  <body>
    <%@include file="jspf/header.jspf" %>

    <%
    String userEmail = "";
    if (session != null) {
      if (session.getAttribute("userEmail") != null) {
        userEmail = (String) session.getAttribute("userEmail");
      } else if (session.getAttribute("userId") != null) {
        userEmail = ConsultationDAO.getUserEmail(dataSource, (String) session.getAttribute("userId"));
      }
    }
    Spectacle spectacle = (Spectacle) session.getAttribute("spectacle");
    %>

    <div class="container">

      <h2>Merci de votre achat pour le spectacle <%=spectacle.getNom()%></h2>
      <form id="achatForm" action="sendTicket" method="POST">
        <legend>Informations Client</legend>
        <div class="form-group row">
          <label class="col-md-4 col-form-label" for="email">Email *</label>
          <div class="col-md-8 ">
            <input type="email" name="email" id="email" placeholder="entrez votre email" class="form-control" value="<%=userEmail%>" required/>
          </div>
        </div>
        <button id="envoyer" type="submit" name="validationBtn" class="btn btn-primary">
          <i class="fas fa-paper-plane"></i> M'envoyer mon(mes) ticket(s)
        </button>
      </form>
      <br/><br/>
      <div id="waitMessage">
        <img src="images/animatedCircle.gif" alt="cercle animé"/>
        <p>
          Patience...<br>
          Votre ticket est en cours de prépararation.
        </p>
      </div>

    </div>

    <%@include file="jspf/footer.jspf" %>

    <script>
    $(document).ready(function () {
      $('#waitMessage').hide();

      $('#achatForm').submit(function () {
        $('#waitMessage').show();
      });
    });
    </script>
  </body>
</html>
