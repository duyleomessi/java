/**
 * Created by samhv on 11/30/16.
 */
'use strict'

var mongoose = require('mongoose'),
    Schema = mongoose.Schema,
    autoIncrement = require('./db');

var CategorySchema = new Schema({
    _id: Number,
    name: {
        type: String,
        unique: true
    },
    products: [{ type: Number, ref: 'Product' }]
});

CategorySchema.plugin(autoIncrement.plugin, 'Category');

module.exports = mongoose.model('Category', CategorySchema);