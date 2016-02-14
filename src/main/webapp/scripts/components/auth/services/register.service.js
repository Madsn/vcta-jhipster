'use strict';

angular.module('vctaApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


