'use strict'

var normalizedPath = require("path").join(__dirname, "module");
var fs = require('fs');
fs.readdirSync(normalizedPath).forEach(function (dir) {
    var obj;


    try {

        fs.readFile('./module/' + dir + '/package.json', 'utf8', function (err, data) {
            if (!err) {
                obj = JSON.parse(data);
                if (obj['enable'] == true) {
                    console.log('Load module ' + obj['name'] + ' ....')
                    require('./module/' + dir + '/' + obj['main'])
                }
            }
        });
    }
    catch (e) {
        console.log(e);
    }


});