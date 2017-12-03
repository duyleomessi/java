let product = require('../../models/product')
let util = require('../../util/util')

exports.products_suggestion = function (req, res) {
	if (req.query.query === '') {
    res.status(200).json([]);
  } else {
    let callback = function (result) {
      res.status(200).json(result);
    }
    product.getByKeyword(req.query.query, callback);
  }
 }
