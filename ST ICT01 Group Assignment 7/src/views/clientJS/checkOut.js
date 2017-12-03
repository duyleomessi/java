function checkOut() {
  let address = document.getElementById('address').value.trim()
  let recipientName = document.getElementById('recipientName').value.trim()
  console.log(address, recipientName)

  if (address === '' || recipientName === '') {
    alert('Please fill in the information')
    return;
  }
  var xhttp = new XMLHttpRequest();
  xhttp.onreadystatechange = function() {
    if (this.readyState == 4 && this.status == 200) {
      if(this.responseText === 'success') {
        document.getElementById('checkOutDialogue').style.display='none'
        alert('Thank you for ordering our products\nWe will deliver as soon as possible');
        clearCart();
      } else {
        alert('Sorry for the inconvenience.\nThere are some problems when processing order\nPlease try again later')
      }
    }
  };
  xhttp.open("POST", "/api/checkout", true);
  xhttp.setRequestHeader("Content-type", "application/json");
  xhttp.send(JSON.stringify([recipientName, address, read_cookie('cart')]));
}