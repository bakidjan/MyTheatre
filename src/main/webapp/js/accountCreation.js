/*
* script pour vérifier les données du formulaire de création d'un nouveau compte.
*
* Lorque le formulaire est soumis l'envoi est bloqué si l'identifiant n'est pas valide
* ou si les mots de passe ne sont pas identiques.
*
* La vérification de la validité de l'identifiant se fait à la volée au fur et à mesure
* que l'utilisateur le tappe. Ceci est réalisé à l'aide d'un appel AJAX.
*/

var userIdIsValid = true;
var userEmailIsValid = true;


/**
* met à jour le DOM pour afficher ou effacer le message d'erreur selon l'état
* des inputs du formulaire et pour activer/désactiver le bount d'envoi.
* @returns {undefined}
*/
function majFormulaire () {
  var passwd, passwdConfirm;
  var mess = '';
  var errorMsgDiv = document.getElementById('errorMsg');

  // on récupère les valeurs des mots de passe
  passwd = document.getElementById('passwd').value;
  passwdConfirm = document.getElementById('passwdConfirm').value;

  if (passwd !== passwdConfirm || !userIdIsValid || !userEmailIsValid) {
    if (!userIdIsValid && $('#id').val() !== '') {
      mess = 'Identifiant déjà utilisé';
    }
    if (!userEmailIsValid && $('#email').val() !== '') {
      if (mess !== '') {
        mess = mess + '<br>';
      }
      mess = mess + 'Email déjà utilisé';
    }
    if (passwd !== passwdConfirm) {
      // les mot de passe diffèrent,
      if (mess !== '') {
        mess = mess + '<br>';
      }
      mess = mess + 'Les mots de passe ne sont pas identiques !!!';
    }
  }
  // on affiche le message d'erreur
  errorMsgDiv.innerHTML = mess;
  if (mess === '') {
    errorMsgDiv.className = '';
  } else {
    // on met le style css bootstrap pour les message d'erreur
    errorMsgDiv.className = 'alert alert-danger';
  }

  if (passwd !== '' && passwd === passwdConfirm && userIdIsValid && userEmailIsValid) {
    // on active le bouton pour permettre l'envoi du formulaire
    document.getElementById('validationBtn').disabled = false;
  } else {
    // on désactive le bouton pour empêcher l'envoi du formulaire
    document.getElementById('validationBtn').disabled = true;
  }
}

/**
* fonction callbak (Event handler) invoquée chaque fois que l'utilisateur
* tappe un caractêre dans l'input 'id' du formulaire (événement keyup).
* Cette fonction une requête asynchrone
* pour vérifier si l'identifiant proposé n'est pas déjà utilisé.
*
* @param {type} servData les données retournées par le serveur
* @returns {undefined}
*/
function validateUserId (servData) {
  if ($('#id').val() === '') {
    userIdIsValid = false;
    majFormulaire();
  } else {
    $.ajax({
      url: 'validateId',
      type: 'post',
      dataType: 'text',
      data: $('#accountCreationForm').serialize(),
      success: function (data) {
        // Extrait "true" ou "false" des données retournées par le serveur.
        var message;
        var xmlMessage = $.parseXML(data);
        message = $(xmlMessage).find('valid').text();
        if (message === 'false') {
          userIdIsValid = false;
        } else {
          userIdIsValid = true;
        }
        // mise à jour du DOM
        majFormulaire();
      }
    });
  }
}

/**
* fonction callbak (Event handler) invoquée chaque fois que l'utilisateur
* tappe un caractêre dans l'input 'email' du formulaire (événement keyup).
* Cette fonction une requête asynchrone
* pour vérifier si l'identifiant proposé n'est pas déjà utilisé.
*
* @param {type} servData les données retournées par le serveur
* @returns {undefined}
*/
function validateUserEmail (servData) {
  if ($('#email').val() === '') {
    userEmailIsValid = false;
    majFormulaire();
  } else {
    $.ajax({
      url: 'validateEmail',
      type: 'post',
      dataType: 'text',
      data: $('#accountCreationForm').serialize(),
      success: function (data) {
        // Extrait "true" ou "false" des données retournées par le serveur.
        var message;
        var xmlMessage = $.parseXML(data);
        message = $(xmlMessage).find('valid').text();
        if (message === 'false') {
          userEmailIsValid = false;
        } else {
          userEmailIsValid = true;
        }
        // mise à jour du DOM
        majFormulaire();
      }
    });
  }
}

/**
* fonction exécutée au chargement de la page
*/
$(document).ready(function () {
  // console.log('ready');
  // desactive le bouton d'envoi du formulaire
  $('#validationBtn').prop('disabled', true);
  // associe la fonction callback verifierMotsDePasse à un événement keyup sur le champ texte du formulaire
  $('#id').keyup(validateUserId);
  $('#email').keyup(validateUserEmail);
  // associe la fonction callback verifierMotsDePasse à un événement keyup sur les champs mot de passe
  $('#passwdConfirm').keyup(majFormulaire);
  $('#passwd').keyup(majFormulaire);
});
