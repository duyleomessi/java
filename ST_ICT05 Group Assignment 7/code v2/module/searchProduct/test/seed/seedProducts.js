/**
 * Created by samhv on 11/30/16.
 */
var Category = require('../../model/category')
var Product = require('../../model/product')


exports.seedProducts = function () {
    Product.find({}).exec(function (err, collection) {
        if (collection.length === 0) {
            createProduct('Category 1', 'Product 1');
            createProduct('Category 1', 'Product 2');
            createProduct('Category 2', 'Product 3');
            createProduct('Category 2', 'Product 4');
            createProduct('Category 3', 'Product 5');
            createProduct('Category 3', 'Product 6');
            createProduct('Category 4', 'Product 7');
            createProduct('Category 4', 'Product 8');
            createProduct('Category 1', 'Product 4');
            createProduct('Category 1', 'Product 3');
            createProduct('Category 2', 'Product 2');
            createProduct('Category 2', 'Product 1');
            createProduct('Category 3', 'Product 8');
            createProduct('Category 3', 'Product 7');
            createProduct('Category 4', 'Product 6');
            createProduct('Category 4', 'Product 5');
        }
    });


    function createProduct(categoryName, productName) {
        Category.findOne({name: categoryName}).exec()
            .then(function (category) {

                var product = new Product({name: productName, category: category._id});

                product.save(function (err, product) {
                    if (!err) {
                        category.products.push(product._id);
                        category.markModified('products')
                        category.save();
                    }
                });
            });
    }
};


