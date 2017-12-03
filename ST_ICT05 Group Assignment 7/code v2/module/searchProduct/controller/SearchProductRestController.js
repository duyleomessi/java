/**
 * Created by congtt on 10/30/17
 * This controller sastified most of SOLID principles. 
 */
'use strict'

var express = require('express');
var SByname = require('./Strategy/SearchProductByName');
var SByCate = require('./Strategy/SearchProductByCategory');
var SStrategy = require('./Strategy/SearchProductStrategy');


var SearchProductController = express.Router()

var searchProduct = function (req, res) {
    var sStrategy = new SStrategy();

    if (req.params.searchName == 'product') {
        var sByName = new SByname();
        sStrategy.setSearchAlgorithm(sByName);
        sStrategy.search(req.params.name, function (products) {
            if (Object.keys(products).length == 0) {

                res.json({
                    status: false,
                    error: "product " + req.params.name + " not found"
                });
                return;
            }

            res.json({
                status: true,
                products: products
            });
            return;
        })
    }

    if (req.params.searchName == 'category') {
        var sByCate = new SByCate();
        sStrategy.setSearchAlgorithm(sByCate);
        sStrategy.search(req.params.name, function (category) {
            if (!category) {
                // not found
                res.json({
                    status: false,
                    error: "Category " + req.params.name + " not found"
                });
                return;
            }
            // found
            // get product list
            res.json({
                status: true,
                products: category.products
            });
        })
    }
}

SearchProductController.get('/search/:searchName/:name', searchProduct);

module.exports = SearchProductController;