let product = require('../../models/product')
let util = require('../../util/util')
let sort = require('./sort')
let paging = require('./paging')

exports.listAllProduct = function (req, res) {
  let callback = function (result) {
    res.render('index', {
      products: result
    })
  }
  product.getAllProducts(callback);
}

exports.listFeaturedProducts = function (req, res) {
  let callback = function (result) {
    res.render('index', {
      products: result
    })
  }
  product.getFeaturedProducts(callback);
}

exports.listSearchProduct = function (req, res) {
  if (req.query.name) {
    let page = -1;
    if (!req.query.page) {
      page = 0
    } else {
      page = req.query.page - 1
    }
    let sortType = req.query.sort
    if (!sortType)
      sortType = ''
    let callback = function (result) {
      let maxPage = paging.getMaxPage(result)
      result = sort.sorting(sortType, result)
      result = paging.paging(page, result)
      res.render('productSearch', {
        products: result,
        currentPage: page + 1,
        maxPage: maxPage,
        name: req.query.name,
        sortType: sortType
      })
    }
    product.getByKeyword(req.query.name, callback);
  } else {
    res.redirect('/')
  }
}
