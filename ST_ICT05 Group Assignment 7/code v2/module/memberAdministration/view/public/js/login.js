$( document ).ready(function() {
  var host = location.protocol + "//" + window.location.hostname + (location.port ? ':'+location.port: '');
  $('.login-button').click(function() {
    // when user click login button
    // get user's input
    var email = $("input[name='email']").val();
    var password = $("input[name='password']").val();
    // prepare data
    var data = {
      email: email,
      password: password
    }
    // send request
    $.ajax({
      type: "POST",
      url: host + '/login',
      data: data,
      success: onSuccess,
      error: onError,
      contentType: "application/x-www-form-urlencoded",
      dataType: "json",
    });

    // when login Successfully
    function onSuccess(data, status, jqXHR) {
      console.log(data);
      // save token to local storage
      localStorage.setItem("token", data.token);
      localStorage.setItem("id", data.account._id);
      $.jGrowl("Your account has been created", { theme: 'jgrowl-success', life: 2000});
      window.location.href = host + '/';
    }

    function onError() {
      console.log("error");
    }
  });
});
