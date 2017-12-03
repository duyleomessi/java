/**
 * Created by congtt on 10/30/17.
 * This class is used for searching product by name.
 */
'use strict'

var Product = require('../../model/product')


var SearchProductByName = function () {
    this.search = function (name, callback) {
        // get Product name
        var promise = Product.find({ 'name': new RegExp(name, "i") }).populate('category', 'name').exec();
        promise.then(callback);
    }
}

module.exports = SearchProductByName;