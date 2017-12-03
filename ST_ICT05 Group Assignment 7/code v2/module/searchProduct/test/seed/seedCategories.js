var Category = require('../../model/category')

exports.seedCategories = function seedCategories() {
    Category.find({}).exec(function (err, collection) {
        if (collection.length === 0) {
            Category.create({ name: 'Category 1' });
            Category.create({ name: 'Category 2' });
            Category.create({ name: 'Category 3' });
            Category.create({ name: 'Category 4' });
        }
    });
}