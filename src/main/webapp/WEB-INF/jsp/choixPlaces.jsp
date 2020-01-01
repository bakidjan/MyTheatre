<%--
Document   : achatPlaces
Cette page utilise le plugin jQuery jQuery Seat Charts pour afficher un
plan de salle sur lequel l'utilisateur peut sélectionner ses places et
les acheter.
Cette page utilise JQuery pour à intervalles réguliers rafraichir le plan de
salle afin de mettre à jour la liste des places disponibles.

Author     : Philippe GENOUD - Université Grenoble Alpes - Lab LIG-Steamer
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="im2ag.m2cci.p01.model.Spectacle"%>
<%@page import="im2ag.m2cci.p01.model.Representation"%>
<%@page import="im2ag.m2cci.p01.model.Reduction"%>
<%@page import="im2ag.m2cci.p01.dao.ConsultationDAO"%>
<%@page import="java.util.List"%>
<!DOCTYPE html>
<html>
  <head>
    <%@include file="jspf/commonHead.jspf" %>
    <script src="js/achatPlaces.js" type="text/javascript"></script>
    <script src="js/jQuery-Seat-Charts/jquery.seat-charts.min.js" type="text/javascript"></script>
    <link href='http://fonts.googleapis.com/css?family=Lato:400,700' rel='stylesheet' type='text/css'/>
    <link href="js/jQuery-Seat-Charts/jquery.seat-charts.css" rel="stylesheet" type="text/css"/>
    <link href="css/stylesTheatre.css" rel="stylesheet" type="text/css"/> <!-- a fusionner avec style.css -->
  </head>
  <body>

    <%@include file="jspf/header.jspf" %>

    <%
    Spectacle spectacle = (Spectacle) session.getAttribute("spectacle");
    Representation representation = (Representation) session.getAttribute("representation");
    %>

    <div id="wrapper" class="container">
      <h2>
        <%=spectacle.getNom()%> - Représentation du <%=representation.getDateString()%> à <%=representation.getHeure()%> heures
      </h2>
      <div id="map-container" class="row">
        <!-- Le div qui contient le plan de la salle -->
        <div id="seat-map" class="col-md-10">
          <div class="front-indicator">Scène</div>
        </div>

        <div id="legend" class="col-md-2"></div>
      </div>


      <!-- Le div qui contient le récapitulatif des places sélectionnées par
      l'utilisateur -->
      <div id="commande" classe="row">
        <div class="col">
          <div class="row">
            <h3>Votre sélection</h3>
          </div>
          <div class="row">
            <%List<Reduction> listReduction = ConsultationDAO.getAllReductions(dataSource);%>
            <div class="col">
              <div class="form-group row">
                <label class="col-sm-4 col-form-label">Sans réduction</label>
                <div class="col-sm-8">
                  <input class="inputNumber form-control" name="none" type="number" value="0" min="0" max="" onchange="hasAllReduc()"/>
                </div>
              </div>
              <%for (Reduction reduction : listReduction) {%>
              <div class="form-group row">
                <label class="col-sm-4 col-form-label">Réduction <%=reduction.getNomReduction()%> (-<%=reduction.getValeurReduction()%>%)</label>
                <div class="col-sm-8">
                  <input class="inputNumber form-control" name="<%=reduction.getNomReduction()%>" type="number" value="0" min="0" max="" onchange="hasAllReduc()"/>
                </div>
              </div>
              <%}%>
            </div>
          </div>
          <div class="form-group row">
            <div class="col-sm-3">
              Nbre de places : <strong><span id="nbplaces">0</span></strong>
            </div>
            <div class="col-sm-3">
              Prix : <strong><span id="prix">0</span> €</strong>
            </div>
            <div class="col-sm-3">
              Promotion : <strong><span id="promotion"><%=representation.getPromotion()%></span>%</strong>
            </div>
            <div class="col-sm-3">
              Prix Total : <strong><span id="prixtotal">0</span> €</strong>
            </div>
          </div>
          <div class="form-group row">
            <div class="col">
              <div class="form-check-inline">
                <label class="form-check-label">
                  <input type="radio" class="form-check-input" name="estPaye" value="true" checked required/>Payer
                </label>
              </div>
              <div class="form-check-inline">
                <label class="form-check-label">
                  <input type="radio" class="form-check-input" name="estPaye" value="false" required/>Reserver 24h
                </label>
              </div>
              <button id="achatBtn" class="btn btn-info" disabled>
                <i id="btnIcon" class="fas fa-shopping-cart"></i><span id="textBtn"> Acheter</span>
              </button>
            </div>
          </div>
        </div>
      </div>

      <hr>
        <div>
          <p>Abandon et <a href="." role="button" class="btn btn-info">
            <i class="fas fa-home"></i> retour à l'accueil</a></p>
          </div>
        </div>
      </div>


      <%@include file="jspf/footer.jspf" %>

      <script>
      $('.form-check-input').on('change', function () {
        let estPaye = document.querySelector('input[name="estPaye"]:checked').value
        let btnAchat = document.getElementById('textBtn');
        let btnIcon = $('#btnIcon');
        if (estPaye === 'false') {
          btnAchat.innerHTML = ' Réserver';
          btnIcon.addClass('fa-save').removeClass('fa-shopping-cart');
        } else {
          btnAchat.innerHTML = ' Acheter';
          btnIcon.addClass('fa-shopping-cart').removeClass('fa-save');
        }
      });
      </script>
    </body>
  </html>
