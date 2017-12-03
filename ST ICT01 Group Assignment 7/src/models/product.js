let csv = require('fast-csv');
let util = require('../util/util');

function getAllProducts (callback) {
  let result = [];
	csv.fromPath('./src/static/product.csv')
  .on('data', (data) => {
    result.push(data);
  })
  .on('end', () => {
    callback(result);
  })
}

function getFeaturedProducts (callback) {
  let result = [];
  csv.fromPath('./src/static/product.csv')
  .on('data', (data) => {
    result.push(data);
  })
  .on('end', () => {
    productsIndex = [];
    while (productsIndex.length < 10) {
      let rand = Math.floor((Math.random() * result.length))
      if (!productsIndex.includes(rand)) {
        productsIndex.push(rand)
      }
    }
    // console.log(productsIndex)
    let products = productsIndex.map((i) => {
      return result[i]
    })
    callback(products);
  })
}

function getByKeyword (keyword, callback) {
  keyword = util.fixVietnamese(keyword)
  let result = [];
  csv.fromPath('./src/static/product.csv')
  .on('data', (data) => {
    if (util.fixVietnamese(data[1]).includes(keyword)) {
      result.push(data);
    }
  })
  .on('end', () => {
    callback(result);
  })
}

function getById (id, callback) {
  let result = [];
  csv.fromPath('./src/static/product.csv')
  .on('data', (data) => {
    if (data[0] === id) {
      result.push(data);
    }
  })
  .on('end', () => {
    callback(result);
  })
}

function getByIdList (idList, callback) {
  let result = [];
  csv.fromPath('./src/static/product.csv')
  .on('data', (data) => {
    if (idList.includes(data[0])) {
      result.push(data);
    }
  })
  .on('end', () => {
    callback(result);
  })
}

module.exports = {
  getByKeyword,
  getAllProducts,
  getById,
  getFeaturedProducts,
  getByIdList
}
