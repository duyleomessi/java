'use strict'

var mongoose = require('mongoose'),
    Schema = mongoose.Schema,
    autoIncrement = require('./db');

var ProductSchema = new Schema({
    _id: Number,
    name: {
        type: String
    },
    category: { type: Number, ref: 'Category' }
});

ProductSchema.plugin(autoIncrement.plugin, 'Product');

module.exports = mongoose.model('Product', ProductSchema);