<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="im2ag.m2cci.p01.ctrler.Index"%>
<%@page import="im2ag.m2cci.p01.dao.DateString"%>
<%@page import="im2ag.m2cci.p01.dao.ConsultationDAO"%>
<%@page import="im2ag.m2cci.p01.model.Spectacle"%>
<%@page import="im2ag.m2cci.p01.model.Representation"%>
<%@page import="java.util.List"%>
<%@page import="javax.sql.DataSource"%>
<%@page import="javax.naming.Context"%>
<%@page import="javax.naming.InitialContext"%>
<%@page import="java.util.Iterator"%>
<!DOCTYPE HTML>
<html>

  <head>
    <%@include file="WEB-INF/jsp/jspf/commonHead.jspf" %>
  </head>

  <body>
    <div class="loader"></div>

    <div id="backtop" class="btn btn-info">&#9650;</div>

    <button id="btnShowRep" class="btn btn-info" onclick="showRep()">See More</button>

    <div class="page">

      <%@include file="WEB-INF/jsp/jspf/header.jspf" %>

      <div class="container">
        <div class="row">
          <div class="col">
            <h3>Spectacles à l'affiche</h3>
          </div>
        </div>

        <%
        DateString dateString = new DateString(); // date d'aujourd'hui
        String date1 = dateString.getDateString();
        int month = Integer.parseInt(dateString.getMonth());
        String year = dateString.getYear();
        if (month >= 9 && month <= 12) {
          year = Integer.toString(Integer.parseInt(year) + 1);
        }
        String date2 = year + "-08-31";
        List<Spectacle> allSpectacles = ConsultationDAO.getAllRepresentationsBetweenTwoDates(dataSource, date1, date2, "", "", "");

        Index index = new Index (allSpectacles);
        Iterator<Representation> repIt = index.iterator();
        int nbRepresentation = 0;
        %>

        <div class="row">
          <%while (repIt.hasNext() && ++nbRepresentation <= 16) {%>
          <%Representation rep = repIt.next();%>
          <%Spectacle spec = index.getSpectacle(rep);%>
          <div class="spectacle col-md-6">
            <div class="row">
              <div class="col-md-4">
                <img src="data:image/png;base64,<%=spec.getImg()%>" class="img-fluid rounded" width="130px" height="90px"/>
              </div>
              <div class="col-md-8">
                <a href="./spectacle?numero=<%=spec.getNumero()%>&date=<%=rep.getDateStringArg()%>"><%=spec.getNom()%></a><br/>
                Le <%=rep.getDateString()%> à <%=rep.getHeure()%> heures.
              </div>
            </div>
          </div>
          <%}%>
        </div>

        <div id="hidenRep" class="row">
          <%while (repIt.hasNext()) {%>
          <%Representation rep = repIt.next();%>
          <%Spectacle spec = index.getSpectacle(rep);%>
          <div class="spectacle col-md-6">
            <div class="row">
              <div class="col-md-4">
                <img src="data:image/png;base64,<%=spec.getImg()%>" class="img-fluid rounded" width="130px" height="90px"/>
              </div>
              <div class="col-md-8">
                <a href="./spectacle?numero=<%=spec.getNumero()%>&date=<%=rep.getDateStringArg()%>"><%=spec.getNom()%></a><br/>
                Le <%=rep.getDateString()%> à <%=rep.getHeure()%> heures.
              </div>
            </div>
          </div>
          <%}%>
        </div>

        <%@include file="WEB-INF/jsp/jspf/footer.jspf" %>

      </div>

      <script>
      $(document).ready(function () {
        $('.page').css('display', 'block');
        $('.loader').css('display', 'none');
      });

      /**
      * permet l'affichage de TOUTES les représentations dans l'index
      * par le bouton
      */
      let hidenRep = document.getElementById('hidenRep');

      function showRep () {
        let button = document.getElementById('btnShowRep');
        if (hidenRep.style.display === 'flex') {
          hidenRep.style.display = 'none';
          button.innerHTML = 'See More';
        } else {
          hidenRep.style.display = 'flex';
          button.innerHTML = 'See Less';
        }
      }

      /* affichage du bouton btnShowRep quand la flèche backToTop s'afiche */
      $(document).on('scroll', function () {
        let scrollHeight = $(document).height();
        let scrollPosition = $(window).height() + $(window).scrollTop();
        if (!$('#backtop').hasClass('mcOut') || scrollHeight === scrollPosition || hidenRep.style.display === 'flex') {
          $('#btnShowRep').css('display', 'block');
        } else {
          $('#btnShowRep').css('display', 'none');
        }
      });
      </script>
    </body>

  </html>
