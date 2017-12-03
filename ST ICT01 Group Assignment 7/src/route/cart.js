let router = require('express').Router()
// let detailProduct = require('../controllers/detailProduct/detail')
let cartController = require('../controllers/cart/cartController')

// router.get('/:id', (req, res) => detailProduct.productDetail(req, res))
router.get('/', (req, res) => {
  cartController.showCart(req, res);
})

module.exports = router
