let order = require('../models/order')

function createOrder(req, res) {
  let callback = function (result) {
    res.send('success')
  }
  order.saveOrder(req.body, callback)
}

module.exports = {
  createOrder
}
