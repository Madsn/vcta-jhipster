'use strict';

angular.module('vctaApp')
	.controller('TripDeleteController', function($scope, $uibModalInstance, entity, Trip) {

        $scope.trip = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Trip.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
