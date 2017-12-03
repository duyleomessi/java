/**
 * Created by congtt on 10/30/17.
 * This class is used for searching product by category.
 */
'use strict'

var Catagory = require('../../model/category')

var searchProductByCategory = function () {

    this.search = function (name, callback) {
        // find category
        Catagory.findOne({'name': new RegExp('^' + name + '$', "i")}).populate('products').exec().then(callback);
    }


}

module.exports = searchProductByCategory;