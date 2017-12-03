function addToCart(id, name) {
  let cart = read_cookie('cart')
  if (cart) {
    cart = cart.split(',')
    var isNew = true
    cart.forEach((item, index, array) => {
      if (parseInt(item.split(':')[0]) === id) {
        array[index] = `${ item.split(':')[0] }:${ parseInt(item.split(':')[1]) + 1 }`
        isNew = false
      }
    })
    if (isNew) {
      cart.push(`${ id }:1`)
    }
    cart.join(',')
    createCookie('cart', cart)
  } else {
    createCookie('cart', `${ id }:1`)
  }
  console.log(read_cookie('cart'))
  alert(`Added ${ name } to cart`)
}

function changeCart(id, value) {
  value = parseInt(value)
  if (value < 0) {
    value = 1
  }
  let cart = read_cookie('cart')
  cart = cart.split(',')
  cart.forEach((item, index, array) => {
    if (item.split(':')[0] === id) {
      if (value === 0) {
        array.splice(index, 1);
      } else {
        array[index] = `${ item.split(':')[0] }:${ value }`
      }
    }
  })
  cart.join(',')
  createCookie('cart', cart)
  console.log(read_cookie('cart'))
}

function clearCart() {
  createCookie('cart','1',-1)
  top.location.href = '/cart'
}