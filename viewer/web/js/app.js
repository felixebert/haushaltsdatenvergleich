'use strict';
var hdv = {};

(function(angular) {
	var appModule = angular.module('hdv', ['map']);

	appModule.config(function($routeProvider) {
		$routeProvider.when('/map', {
			templateUrl: 'partials/map.html',
			controller: 'MapCtrl'
		});
		$routeProvider.otherwise({
			redirectTo: '/map'
		});
	});
})(angular);
