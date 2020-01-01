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
        <h2>
          <a role="button" href="." class="btn btn-info">
            <i class="fas fa-home"></i>
          </a>
          Veuillez choisir un mode d'identification
        </h2>
      </div>
      <div class="row">
        <div class="col-md-6">
          <form action="validateAccount"  method="post">
            <legend>Continuer comme anonyme</legend>
            <div class="form-group row">
              <label class="col-md-4 col-form-label" for="email">Email *</label>
              <div class="col-md-8 ">
                <input id="email" name="inputEmail" placeholder="entrez votre email" class="form-control" type="text" required pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z].{1,}$" title="someone@something.here"/>
              </div>
            </div>
            <button type="submit" name="validationBtn" class="btn btn-primary">
              <i class="fas fa-envelope"></i> Valider l'email
            </button>
          </form>
        </div>
        <div class="col-md-6">
          <div class="form-group row">
            <div class="col">
              <form action="validateAccount"  method="post">
                <legend>Continuer avec mon compte</legend>
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
                <div class="row">
                  <button type="submit" name="validationBtn" class="btn btn-primary">
                    <i class="fas fa-lock-open"></i> Se connecter
                  </button>
                </div>
              </form>
            </div>
          </div>
          <div class="form-group row">
            <div class="col">
              <form action="createAccount">
                <div class="input-group input-group-md">
                  <div class="input-group-prepend">
                    <label class="input-group-text">Pas de compte ?</label>
                  </div>
                  <button type="submit" class="btn btn-primary">
                    <i class="fas fa-user-plus"></i> S'inscrire
                  </button>
                </div>
              </form>
            </div>
          </div>
        </div>
      </div>
    </div>

    <%@include file="jspf/footer.jspf" %>

  </body>
</html>
