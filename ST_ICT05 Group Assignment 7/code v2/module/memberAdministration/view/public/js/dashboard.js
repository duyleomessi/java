$(document).ready(function() {
    var token = localStorage.getItem("token");
    var currentId = -1;
    var users = null;
    if(token === null) {
        // if user have not logged
        // display error and return
        onError();
        return;
    }
    // get user list
    $.ajax({
      type: "GET",
      headers: { 'token': token },
      url: host + '/api/users',
      dataType: "json",
      error: onError,
  }).done(printUserList);

  // print the list of users
  function printUserList(data, status, jqXHR) {
      if(data !== null && data.status) {
          // if get user list Successfully
          // get html string
          // because of deadline so we didn't enough time to sanitise our input,
          // therefore, XSS vunerability will happen here
          // be careful if you use this code
          // sorry :((
          users = data.users;
          console.log(data);
          var box = $('.dashboard-box');
          data.users.forEach(function(item, index) {
             // for each user
             var html = getUserHtml(item);
             box.append(html);
          });
      }
  }

  // get html of each row representing information of each user
  function getUserHtml(item) {
      var name = typeof(item.name) !== 'undefined' ? item.name : '';
      var email = typeof(item.email) !== 'undefined' ? item.email : '';
      var admin = typeof(item.admin) !== 'undefined' ? item.admin : '';
      var id = typeof(item._id) !== 'undefined' ? item._id : '';

      var html = '<div class="row no-margin row-data" id=row-id-' + id +'><div class="col-xs-3">' + name
        + '</div><div class="col-xs-4">' + email
        + '</div><div class="col-xs-1">' + admin
        + '</div><div class="col-xs-4 function-button-holder"><button class="edit-button" data-id=' + id
        + '><span class="glyphicon glyphicon-pencil"></span>Edit</button><button class="delete-button" data-id=' + id
        + '><span class="glyphicon glyphicon-trash"></span>Remove</button></div></div>';
      return html;
  }

  // error function when user list cannot be fetched
  function onError() {
    var box = $('.dashboard-box');
    box.append('<div class="row no-margin row-data error-row">You dont have permission to view this page</div>');
  }

  // delete a user
  $(document).on("click", ".delete-button", function() {
    // get user id
    id = $(this).data("id");
    // show modal delete
    $('#deleteModal').modal('show');
  });

  $(document).on("click", "#ok-button", function() {
    // user confirms deleting
    // validate id
    if(id == -1) {
      return;
    }
    // send delete request
    $.ajax({
      type: "DELETE",
      headers: { 'token': token },
      url: host + '/api/user/'+id,
      dataType: "json"
    }).done(deleteUserSuccess);

    id = -1;
    function deleteUserSuccess() {
      // when user is deleted Successfully
      $('#row-id-' + id).css({display: 'none'});
    }
  });

  // update a user
  $(document).on("click", ".edit-button", function() {
    // get user id
    id = $(this).data("id");
    // get data of that user
    var currentUser = getUser(id);
    if (!currentUser) {
      // if we cannot fine a user
      // exit
      return;
    }

    // now fill data to modal
    fillEditModal(currentUser);
    // show modal delete
    $('#edit-modal').modal('show');
  });

  // if user click update button
  $(document).on("click", "#update-button", function() {
    // prepare data
    var data = {};
    var name = $('.edit-modal input[name="name"]').val();
    if(name.length !== 0) {
      data.name = name;
    }
    var email = $('.edit-modal input[name="email"]').val();
    if(email.length !== 0) {
      data.email = email;
    }
    var password = $('.edit-modal input[name="password"]').val();
    if(password.length !== 0) {
      data.password = password;
    }

    data.admin = document.getElementById("admin-checkbox").checked;
    console.log(data);
    $.ajax({
      type: "PUT",
      headers: { 'token': token },
      url: host + '/api/user/'+id,
      dataType: "json",
      data: data,
    }).done(function(){
      location.reload();
    });
  });

  // search for user object based on id
  function getUser(id) {
    var i;
    var len = users.length;
    for(i = 0; i < len; i++) {
      if(typeof(users[i]._id) !== undefined && users[i]._id == id){
        console.log(users[i]);
        return users[i];
      }
    }

    return null;
  }

  // fill data to modal
  function fillEditModal(user) {
    if(typeof(user.name) !== undefined) {
      $('.edit-modal input[name="name"]').val(user.name);
    }

    if(typeof(user.email) !== undefined) {
      $('.edit-modal input[name="email"]').val(user.email);
    }

    if(typeof(user.admin) !== undefined && user.admin) {
      $('.edit-modal input[name="admin"]').prop('checked', true);
    }
  }
});
