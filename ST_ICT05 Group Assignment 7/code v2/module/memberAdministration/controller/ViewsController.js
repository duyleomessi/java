'use strict'

var express = require('express');


var ViewsController = express.Router();

ViewsController.get('/', function (req, res) {
    res.render('index.jade',
        {title: 'Home'}
    );
    //res.end('ok');
});

ViewsController.get('/login', function (req, res) {
    var data = {
        title: 'Login',
        next_link: '/signup',
        text_of_link: 'Create new account',
        script: '/static/js/login.js'
    };
    res.render('login/login.jade', data);
});

ViewsController.get('/signup', function (req, res) {
    var data = {
        title: 'Sign up',
        next_link: '/login',
        text_of_link: 'Log in',
        script: '/static/js/signup.js'
    };
    res.render('login/login.jade', data);

});

ViewsController.get('/dashboard', function (req, res) {
    var data = {
        title: 'Dashboard',
        script: '/static/js/dashboard.js'
    }
    res.render('dashboard/dashboard.jade', data);
})

module.exports = ViewsController;
