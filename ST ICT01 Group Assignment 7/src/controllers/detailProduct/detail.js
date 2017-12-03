let product = require('../../models/product')

exports.productDetail = function (req, res) {
  let id = req.params.id
  let callback = function (result) {
    // console.log(result)
    if (result.length === 0) {
      res.redirect('/')
    } else {
      res.render('productDetail', {
        products: result[0]
      })
    }
  }
  product.getById(id, callback)
}
