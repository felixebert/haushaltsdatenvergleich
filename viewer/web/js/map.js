'use strict';

(function(angular, hdv, L, _) {
	var mapModule = angular.module('map', []);

	hdv.map = {
		init: function() {
			var map = L.map('map', {
				center: [51.463, 7.18],
				zoom: 10
			});
			L.tileLayer('http://{s}.tile.openstreetmap.de/tiles/osmde/{z}/{x}/{y}.png', {
				attribution: '&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors',
				maxZoom: 18
			}).addTo(map);

			var myStyle = {
				"color": "#ff7800",
				"weight": 5,
				"opacity": 1
			};

			L.geoJson(hdv.communes.features, {
				style: myStyle
			}).addTo(map);
		}
	};

	mapModule.controller('MapCtrl', function($scope) {
		hdv.map.init();
	});
})(angular, hdv, L, _);