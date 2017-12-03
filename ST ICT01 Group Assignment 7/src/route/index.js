let router = require('express').Router()
let product = require('../controllers/searchProduct/searchProductController')

router.get('/', (req, res) => {
  product.listFeaturedProducts(req, res);
})

module.exports = router
