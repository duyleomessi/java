$( document ).ready(function() {
  var host = 'http://localhost:8000';
    $('.login-button').click(function() {
      // when user click login button
      // get user's input
      var name = $("input[name='name']").val();
      var email = $("input[name='email']").val();
      var password = $("input[name='password']").val();
      // prepare data for post request
      var data = {
          name: name,
          email: email,
          password: password
      };

      // send the request
      $.ajax({
        type: "POST",
        url: (host + "/signup"),
        data: data,
        success: onSuccess,
        error: onError,
        contentType: "application/x-www-form-urlencoded",
        dataType: "json",
      });

      function onSuccess(data, status, jqXHR){
        // if user are created Successfully
        // show a message and then redirect to login page
        $.jGrowl("Your account has been created", { theme: 'jgrowl-success', life: 2000, close: openLogin});
      }

      function openLogin() {
        // go to login page
        window.location.href = host + "/login";
      }

      function onError(data, status, jqXHR){
        // if user is not created
        // display a message
        console.log(data);
        $.jGrowl("Fail to sign up", { theme: 'jgrowl-fail' });
      }
    });
});
