'use strict';

angular.module('vctaApp')
    .controller('TeamDetailController', function ($scope, $rootScope, $stateParams, entity, Team) {
        $scope.team = entity;
        $scope.load = function (id) {
            Team.get({id: id}, function(result) {
                $scope.team = result;
            });
        };
        var unsubscribe = $rootScope.$on('vctaApp:teamUpdate', function(event, result) {
            $scope.team = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
