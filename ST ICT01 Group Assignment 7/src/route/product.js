let router = require('express').Router()
let detailProduct = require('../controllers/detailProduct/detail')
let searchProductController = require('../controllers/searchProduct/searchProductController')

router.get('/:id', (req, res) => detailProduct.productDetail(req, res))
router.get('/', (req, res) => {
  searchProductController.listSearchProduct(req, res);
})

module.exports = router
