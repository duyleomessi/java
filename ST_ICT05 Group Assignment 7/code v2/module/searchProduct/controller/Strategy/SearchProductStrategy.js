/**
 * Created by congtt on 10/30/17.
 * This class is used for determining the way to search product
 */
'use strict'

var SearchProductStrategy = function () {
    this.searchAlgorithm = undefined;
    
    this.setSearchAlgorithm = function (algorithm) {
        this.searchAlgorithm = algorithm;
    };
    
    this.search = function (name, callback) {
        return this.searchAlgorithm.search(name, callback);
    };
};

module.exports = SearchProductStrategy;