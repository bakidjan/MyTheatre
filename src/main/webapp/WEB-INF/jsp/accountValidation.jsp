<%@page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE HTML>
<html>

  <head>
    <%@include file="jspf/commonHead.jspf" %>
  </head>

  <body>
    <%@include file="jspf/header.jspf" %>

    <div class="container">
      <div class="row">

        <div class="col-md-8">
          <form action="validateAccount"  method="post">
            <legend>Se connecter</legend>
            <div class="form-group row">
              <label class="col-md-4 col-form-label" for="id">Identifiant *</label>
              <div class="col-md-8 ">
                <input name="inputId" placeholder="choisissez un identifiant" class="form-control" type="text" required value='${param.inputId}'/>
              </div>
            </div>
            <div class="form-group row">
              <label class="col-md-4 col-form-label" for="passwd">Mot de passe *</label>
              <div class="col-md-8 ">
                <input name="inputPassword" placeholder="choisissez un mot de passe" class="form-control" type="password" required value='${param.inputId}'/>
              </div>
            </div>
            <div>${errorMessage}</div>
            <a role="button" href="." class="btn btn-info">Annuler</a>
            <button type="submit" name="validationBtn" class="btn btn-primary">Valider</button>
          </form>
        </div>

        <div class="col-md-4">
          <form action="createAccount">
            <legend>Pas de compte ?</legend>
            <button type="submit" class="btn btn-primary">
              <i class="fas fa-user-plus"></i> S'inscrire
            </button>
          </form>
        </div>

      </div>
    </div>

    <%@include file="jspf/footer.jspf" %>

  </body>
</html>
