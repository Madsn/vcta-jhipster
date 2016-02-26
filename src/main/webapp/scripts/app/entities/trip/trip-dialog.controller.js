'use strict';

angular.module('vctaApp').controller('TripDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Trip',
        function($scope, $stateParams, $uibModalInstance, entity, Trip) {

        $scope.trip = entity;
        $scope.load = function(id) {
            Trip.get({id : id}, function(result) {
                $scope.trip = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('vctaApp:tripUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.trip.id != null) {
                Trip.update($scope.trip, onSaveSuccess, onSaveError);
            } else {
                Trip.save($scope.trip, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.datePickerForDate = {};

        $scope.datePickerForDate.status = {
            opened: false
        };

        $scope.datePickerForDateOpen = function($event) {
            $scope.datePickerForDate.status.opened = true;
        };
}]);
