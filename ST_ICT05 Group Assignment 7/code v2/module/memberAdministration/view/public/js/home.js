// get host address
var host = location.protocol + "//" + window.location.hostname + (location.port ? ':'+location.port: '');
var token = localStorage.getItem("token");
$(document).ready(function() {
  if (localStorage.getItem("token") === null){
    // if local storage has no token keys
    // update UI imediately
    updateUI(null, null, null);
    return;
  }
  // prepare data
  var data = {
    token: token,
  }
  // check if user has logged in
  $.ajax({
    type: "POST",
    url: host + '/login',
    data: data,
    contentType: "application/x-www-form-urlencoded",
    dataType: "json",
  }).done(updateUI);

  function updateUI(data, status, jqXHR) {
    if (data !== null && data.status) {
      // if logged in
      // set the link of header to dash board
      if(data.account.admin) {
        // if user is admin
        // show dashboard
        $('#location-button > a').html("Dash board");
        $('#location-button').click(function() {
          window.location.href = host + '/dashboard';
        });
      } else {
        // if he is not admin
        // hide dashboard
        $('#location-button').css({display: 'none'});
      }
      // show logout button
      $('#logout-button').css({display: 'block' });
      $('#logout-button').click(destroyLocalStorage);
    } else {
      // if not logged in
      // set the link to log in page
      $('#location-button > a').html("Login");
      $('#location-button').click(function() {
        window.location.href = host + '/login';
      });
    }
  }

  function destroyLocalStorage() {
    // delete all local storage data
    localStorage.clear();
    location.reload();
  }
});
