<%@page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE HTML>
<html>

  <head>
    <%@include file="jspf/commonHead.jspf" %>
  </head>

  <body>
    <%@include file="jspf/header.jspf" %>

    <div class="container">
      <form id='accountCreationForm' action="createAccount"  method="post">
        <legend>Créez votre compte</legend>
        <div class="form-group row">
          <label class="col-md-4 col-form-label" for="id">Identifiant *</label>
          <div class="col-md-8 ">
            <input id="id" name="id" placeholder="choisissez un identifiant" class="form-control" type="text" required value='${param.id}'/>
          </div>
        </div>
        <div class="form-group row">
          <label class="col-md-4 col-form-label" for="email">Email *</label>
          <div class="col-md-8 ">
            <input id="email" name="email" placeholder="entrez votre email" class="form-control" type="text" required value='${param.email}' pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z].{1,}$" title="someone@something.here"/>
          </div>
        </div>
        <div class="form-group row">
          <label class="col-md-4 col-form-label" for="passwd">Mot de passe *</label>
          <div class="col-md-8 ">
            <input id="passwd" name="passwd" placeholder="choisissez un mot de passe" class="form-control" type="password" required value='${param.passwd}' pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}" title="Must contain at least one number and one uppercase and lowercase letter, and at least 8 or more characters"/>
          </div>
        </div>
        <div class="form-group row">
          <label class="col-md-4 col-form-label" for="passwdConfirm">Confirmation mot de passe *</label>
          <div class="col-md-8 ">
            <input id="passwdConfirm" name="passwdConfirm" placeholder="confirmez votre mot de passe" class="form-control" type="password" required/>
          </div>
        </div>
        <div class="form-group row">
          <label class="col-md-4 col-form-label">Nom</label>
          <div class="col-md-8 ">
            <input id="nom" name="nom" placeholder="entrez votre nom" class="form-control" type="text" value='${param.nom}'/>
          </div>
        </div>
        <div class="form-group row">
          <label class="col-md-4 ccol-form-label">Prénom</label>
          <div class="col-md-8 ">
            <input id="prénom" name="prenom" placeholder="entrez votre prénom" class="form-control" type="text" value='${param.prenom}'/>
          </div>
        </div>
        <div id="errorMsg">${errorMessage}</div>
        <a role="button" href="." class="btn btn-info">Annuler</a>
        <button type="submit" id="validationBtn" name="validationBtn" class="btn btn-primary">Valider</button>
      </form>
    </div>

    <%@include file="jspf/footer.jspf" %>

  </body>
</html>
