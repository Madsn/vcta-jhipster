'use strict';

angular.module('vctaApp')
    .controller('TripDetailController', function ($scope, $rootScope, $stateParams, entity, Trip) {
        $scope.trip = entity;
        $scope.load = function (id) {
            Trip.get({id: id}, function(result) {
                $scope.trip = result;
            });
        };
        var unsubscribe = $rootScope.$on('vctaApp:tripUpdate', function(event, result) {
            $scope.trip = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
