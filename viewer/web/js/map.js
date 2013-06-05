'use strict';

(function(angular, hdv, L, $, _) {
	var mapModule = angular.module('map', []);

	Number.prototype.formatMoney = function(c, d, t) {
		var n = this, c = isNaN(c = Math.abs(c)) ? 2 : c, d = d == undefined ? "." : d, t = t == undefined ? "," : t, s = n < 0 ? "-" : "", i = parseInt(n = Math
				.abs(+n || 0).toFixed(c))
				+ "", j = (j = i.length) > 3 ? j % 3 : 0;
		return s + (j ? i.substr(0, j) + t : "") + i.substr(j).replace(/(\d{3})(?=\d)/g, "$1" + t) + (c ? d + Math.abs(n - i).toFixed(c).slice(2) : "");
	};

	var map = {
		layers: [],
		getLayer: function(key) {
			return _.find(this.layers, function(layer) {
				return layer.key == key;
			});
		},
		init: function() {
			var leafletMap = L.map('map', {
				center: [51.463, 7.18],
				zoom: 10
			});

			L.tileLayer('http://{s}.tile.cloudmade.com/036a729cf53d4388a8ec345e1543ef53/44094/256/{z}/{x}/{y}.png', {
				attribution: '&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors',
				maxZoom: 18
			}).addTo(leafletMap);

			$.getJSON('js/gemeinden.json', _.bind(function(json) {
				L.geoJson(json.features, {
					style: {
						'opacity': 0.5,
						'weight': 2,
						'fillColor': '#FF0000'
					},
					onEachFeature: _.bind(function(feature, layer) {
						this.layers.push({
							'key': feature.properties.KN,
							'label': feature.properties.GN,
							'value': layer
						});
					}, this)
				}).addTo(leafletMap);

				$.getJSON('js/hdv.json', _.bind(function(data) {
					var boundary = 0;
					_.each(data.accountsPerAreas, function(area) {
						var total = 0;
						_.each(area.accounts, function(account) {
							if (account.key == 553) {
								console.log(account);
								if (account.i != null && account.s != null) {
									total += account.i - account.s;
								} else if (account.s != null) {
									total -= account.s;
								}
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
						opacity = opacity < 0 ? 0 : opacity;
						layer.value.setStyle({
							'fillOpacity': opacity
						});
						layer.value.bindPopup("<strong>" + layer.label + "</strong><br />Summe Friedhofs- und Bestattungswesen: "
								+ area.total.formatMoney(0, ',', '.') + " €");
					}, this));
				}, this));
			}, this));
		}
	};

	mapModule.controller('MapCtrl', function($scope) {
		map.init();
	});
})(angular, hdv, L, $, _);