'use strict';

(function(angular, hdv, L, $, _) {
	var mapModule = angular.module('map', []);

	var map = {
		layers: [],
		getLayer: function(key) {
			var result = _.find(this.layers, function(layer) {
				return layer.key == key;
			});
			return result ? result.value : null;
		},
		init: function() {
			var leafletMap = L.map('map', {
				center: [51.463, 7.18],
				zoom: 10
			});

			L.tileLayer('http://{s}.tile.openstreetmap.de/tiles/osmde/{z}/{x}/{y}.png', {
				attribution: '&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors',
				maxZoom: 18
			}).addTo(leafletMap);

			$.getJSON('js/gemeinden.json', _.bind(function(json) {
				L.geoJson(json.features, {
					style: {
						opacity: 0,
						color: '#0000ff'
					},
					onEachFeature: _.bind(function(feature, layer) {
						var key = feature.properties.KN;
						this.layers.push({
							'key': key,
							'value': layer
						});
					}, this)
				}).addTo(leafletMap);

				$.getJSON('js/data.json', _.bind(function(data) {
					var boundary = 0;
					_.each(data.accountsPerAreas, function(area) {
						var total = 0;
						_.each(area.accounts, function(account) {
							if (account.i != null && account.s != null) {
								total += account.i - account.s;
							}
						});
						area.total = total;

						if (total < boundary) {
							boundary = total;
						}
					});

					_.each(data.accountsPerAreas, _.bind(function(area) {
						var layer = this.getLayer(area.areaKey);
						var opacity = Math.round(area.total / boundary * 100) / 100;
						layer.setStyle({
							'opacity': opacity < 0 ? 0 : opacity
						});
					}, this));
				}, this));
			}, this));
		}
	};

	mapModule.controller('MapCtrl', function($scope) {
		map.init();
	});
})(angular, hdv, L, $, _);