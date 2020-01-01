function setToday () {
  let dateDeb = document.getElementById('DateDeb');
  let dateFin = document.getElementById('DateFin');

  let today = new Date();
  let dd = today.getDate();
  let mm = today.getMonth() + 1; /* January is 0! */
  let yyyy = today.getFullYear();

  if (dd < 10) {
    dd = '0' + dd;
  }
  if (mm < 10) {
    mm = '0' + mm;
  }
  today = yyyy + '-' + mm + '-' + dd;
  dateDeb.value = today;
  dateDeb.setAttribute('min', today);

  /* fin de saison */
  let YYYY;
  if (mm >= 9 && mm <= 12) {
    YYYY = yyyy + 1;
  } else {
    YYYY = yyyy;
  }
  let endDay = YYYY + '-08-31';
  dateDeb.setAttribute('max', endDay);
  dateFin.setAttribute('max', endDay);
}
function setDatePlusUn () {
  let dateDeb = document.getElementById('DateDeb');
  let dateFin = document.getElementById('DateFin');

  let day = new Date(dateDeb.value);
  let dd = day.getDate() + 1;
  let mm = day.getMonth() + 1; /* January is 0! */
  let yyyy = day.getFullYear();

  if (dd < 10) {
    dd = '0' + dd;
  }
  if (mm < 10) {
    mm = '0' + mm;
  }
  let nextDay = yyyy + '-' + mm + '-' + dd;
  dateFin.value = nextDay;
  dateFin.setAttribute('min', nextDay);
}
