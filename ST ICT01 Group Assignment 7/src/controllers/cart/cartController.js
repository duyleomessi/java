let product = require('../../models/product')
let util = require('../../util/util')

exports.showCart = function (req, res) {
  let productsList = [];

  if (req.headers.cookie) {
    let cookiesObj = util.parseCookies(req.headers.cookie)
    if (cookiesObj.cart) {
      productsList = cookiesObj.cart.split(',').map((item) => {
        return {
          id: item.split(':')[0],
          qty: item.split(':')[1]
        }
      })
    }
  }
  let productsIdList = productsList.map((item) => {
    return item.id
  })
  let callback = function (result) {
    result.forEach((item, index, array) => {
      let qty = productsList.find((product) => {
        return product.id === item[0]
      }).qty
      array[index][3] = qty
    })
    res.render('cart', {
      products: result
    })
  }
  product.getByIdList(productsIdList, callback);
}
