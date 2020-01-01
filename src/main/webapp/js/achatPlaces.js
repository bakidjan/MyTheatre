/*
* Copyright (C) 2018 genoud
*
* This program is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

$(document).ready(function () {

  let $detailCategorie = $('#detail-categories');
  let $nbPlaces = $('#nbplaces');
  let $prixTotal = $('#prixtotal');
  let $prix = $('#prix');
  let $promotion = parseInt(document.getElementById('promotion').innerHTML);


  let seatNumber = 1; // numéro utilisé pour associer un label aux sièges
  let seatId = 1; // numero utilisé pour associer un id aux sièges

  let sc = $('#seat-map').seatCharts({
    map: [
      'AAAAAAAAAA___AAAAA___AAAAAAAAAA',
      'AAAAAAAAAA___AAAAA___AAAAAAAAAA',

      'BBBBBBBBBB___________BBBBBBBBBB_',
      'BBBBBBBBBB___________BBBBBBBBBB_',
      '___BBBBB________________BBBBB___',

      '___CCCCCCCCCCCCCCCCCCCCCCCCC____',
      '___CCCCCCCCCCCCCCCCCCCCCCCCC____'
    ],
    seats: {
      A: {
        classes: 'Orchestre', // votre classe CSS spécifique
        category: 'A',
        price: 25
      },
      B: {
        classes: 'Balcon', // votre classe CSS spécifique
        category: 'B',
        price: 20
      },
      C: {
        classes: 'Poulailler', // votre classe CSS spécifique
        category: 'C',
        price: 15
      }
    },
    naming: {
      top: false,
      getLabel: function (character, row, column) {
        return seatNumber++;
      },
      getId: function (character, row, column) {
        return seatId++;
      }
    },
    legend: {
      node: $('#legend'),
      items: [
        ['A', 'available', 'Orchestre'],
        ['B', 'available', 'Balcon'],
        ['C', 'available', 'Poulailler'],
        [, 'unavailable', 'Place non disponible']
      ]
    },
    click: function () {
      if (this.status() === 'available') {
        /*
        * Une place disponible a été sélectionnée
        * Mise à jour du nombre de places et du prix total
        *
        * Attention la fonction .find  ne prend pas en compte
        * la place qui vient d'être selectionnée, car la liste des
        * places sélectionnées ne sera modifiée qu'après le retour de cette fonction.
        * C'est pourquoi il est nécessaire d'ajouter 1 au nombre de places et le prix
        * de la place sélectionnée au prix calculé.
        */
        let nbPlacesTmp = sc.find('selected').length + 1;
        $nbPlaces.text(nbPlacesTmp);
        $prix.text(calculerPrixTotal(sc) + this.data().price);
        $prixTotal.text(Math.round((calculerPrixTotal(sc) + this.data().price) * (1 - $promotion / 100) * 100) / 100);
        if (hasAllReduc()) {
          $('#achatBtn').prop('disabled', false);
        }
        return 'selected';
      } else if (this.status() === 'selected') {
        // la place est désélectionnée
        let nbPlaceSelectees = sc.find('selected').length - 1;
        $nbPlaces.text(nbPlaceSelectees);
        if (nbPlaceSelectees === 0) {
          $('#achatBtn').prop('disabled', true);
          $prix.text(0);
          $prixTotal.text(0);
        } else {
          $prix.text(calculerPrixTotal(sc) - this.data().price);
          $prixTotal.text(Math.round((calculerPrixTotal(sc) - this.data().price) * (1 - $promotion / 100) * 100) / 100);
        }
        return 'available';
      } else if (this.status() === 'unavailable') {
        // la place a déjà été achetée.
        return 'unavailable';
      } else {
        return this.style();
      }
    }
  });



  $('#achatBtn').click(function () {
    acheter(sc);
  });

  majPlanSalle();
  setInterval(majPlanSalle, 10000); // le plan de salle est mis à jour toutes les 10 secondes

  /**
  * met à jour le status des places. Cette mise à jour est effectuée par un
  * appel ajax au service d'url placesNonDisponibles.
  * La réponse de ce service est un objet JSON contenant un tableau bookings
  * @returns {undefined}
  */
  function majPlanSalle () {
    $.ajax({
      type: 'post',
      url: 'placesNonDisponibles',
      dataType: 'json',
      success: function (reponse) {
        // iteration sur toutes les places vendues contenue dans le tableau bookings
        // de l'objet reponse
        $.each(reponse.placesVendues, function (index, placeVendue) {
          // mettre à jour le status de l'objet Seat correspondant à la place vendue
          sc.status('' + placeVendue.placeId, 'unavailable'); // le premier paramètre
          // de status est l'identifiant de la place (siège) pour laquellle on souhaite
          // modifier le status. Ce paramètre est un chaîne, placeVendue.placeID est
          // de type number (entier), ''+ placeVendue.placeId permet de le convertir
          // en chaîne de caractères (on aurait aussi pu utiliser placeVendue.placeId.toString())
        });
        let nbPlaceSelectees = sc.find('selected').length;
        $('#nbplaces').text(nbPlaceSelectees);
        if (nbPlaceSelectees === 0) {
          $('#achatBtn').prop('disabled', true);
          $prix.text(0);
          $prixTotal.text(0);
        } else {
          if (hasAllReduc()) {
            $('#achatBtn').prop('disabled', false);
          }
          $prix.text(calculerPrixTotal(sc));
          $prixTotal.text(Math.round((calculerPrixTotal(sc)) * (1 - $promotion / 100) * 100) / 100);
        }
      }
    });
  }
});

function majPanier (sc) {
  let nbPlaceSelectees = sc.find('selected').length;
  $('#nbplaces').text(nbPlaceSelectees);
  if (nbPlaceSelectees === 0) {
    $('#achatBtn').prop('disabled', true);
    $prixTotal.text(0);
  } else {
    if (hasAllReduc()) {
      $('#achatBtn').prop('disabled', false);
    }
    $prixTotal.text(calculerPrixTotal(sc));
  }
}


function calculerPrixTotal (sc) {
  let total = 0;
  // retrouver toutes les places sélectionnées et sommer leur prix
  sc.find('selected').each(function () {
    total += this.data().price;
  });
  return total;
}

function acheter (sc) {
  let params = 'estPaye=' + document.querySelector('input[name="estPaye"]:checked').value;
  sc.find('selected').each(function () {
    params = params + '&place=' + this.node().attr('id');  // this est un objet de type Seat
    // this.node() donne l'objet JQuery correspondant à l'élément HTML matérialisant le siège
    // .attr('id') donne la valeur de la propriété 'id' de cet élément
  });
  let inputRedu = $('.inputNumber');
  for (let i = 0; i < inputRedu.length; i++) {
    params = params + '&reduction=' + inputRedu[i].name + '_' + inputRedu[i].value;
  }
  location.replace('acheterPlaces?' + params);
}

function hasAllReduc () {
  let inputRedu = $('.inputNumber');
  let tot = 0;
  let valueTot = parseInt($('#nbplaces')[0].textContent);
  let btn = document.getElementById('achatBtn')
  for (let i = 0; i < inputRedu.length; i++) {
    tot += parseInt(inputRedu[i].value);
    inputRedu[i].max = valueTot;
  }
  if (tot === valueTot) {
    btn.disabled = false;
    btn.title = ''
  } else {
    btn.disabled = true;
    btn.title = 'Le nombre de réduction n\'est pas égal au nombre de places sélectionnées';
  }
  return tot === valueTot;
}
