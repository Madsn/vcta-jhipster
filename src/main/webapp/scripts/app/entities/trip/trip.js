'use strict';

angular.module('vctaApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('trip', {
                parent: 'entity',
                url: '/trips',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Trips'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/trip/trips.html',
                        controller: 'TripController'
                    }
                },
                resolve: {
                }
            })
            .state('trip.detail', {
                parent: 'entity',
                url: '/trip/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Trip'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/trip/trip-detail.html',
                        controller: 'TripDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Trip', function($stateParams, Trip) {
                        return Trip.get({id : $stateParams.id});
                    }]
                }
            })
            .state('trip.new', {
                parent: 'trip',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/trip/trip-dialog.html',
                        controller: 'TripDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    date: null,
                                    distance: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('trip', null, { reload: true });
                    }, function() {
                        $state.go('trip');
                    })
                }]
            })
            .state('trip.edit', {
                parent: 'trip',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/trip/trip-dialog.html',
                        controller: 'TripDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Trip', function(Trip) {
                                return Trip.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('trip', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('trip.delete', {
                parent: 'trip',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/trip/trip-delete-dialog.html',
                        controller: 'TripDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Trip', function(Trip) {
                                return Trip.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('trip', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
