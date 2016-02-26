'use strict';

angular.module('vctaApp').controller('TeamDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Team',
        function($scope, $stateParams, $uibModalInstance, entity, Team) {

        $scope.team = entity;
        $scope.load = function(id) {
            Team.get({id : id}, function(result) {
                $scope.team = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('vctaApp:teamUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.team.id != null) {
                Team.update($scope.team, onSaveSuccess, onSaveError);
            } else {
                Team.save($scope.team, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
