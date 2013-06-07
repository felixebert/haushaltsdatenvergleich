'use strict';

(function(hdv, L, $, _) {
	Number.prototype.formatMoney = function(c, d, t) {
		var n = this, c = isNaN(c = Math.abs(c)) ? 2 : c, d = d == undefined ? "." : d, t = t == undefined ? "," : t, s = n < 0 ? "-" : "", i = parseInt(n = Math
				.abs(+n || 0).toFixed(c))
				+ "", j = (j = i.length) > 3 ? j % 3 : 0;
		return s + (j ? i.substr(0, j) + t : "") + i.substr(j).replace(/(\d{3})(?=\d)/g, "$1" + t) + (c ? d + Math.abs(n - i).toFixed(c).slice(2) : "");
	};

	var map = {
		leafletMap: null,
		areas: [],
		data: {},
		getArea: function(key) {
			return _.find(this.areas, function(area) {
				return area.key == key;
			});
		},
		init: function() {
			this.leafletMap = L.map('map', {
				center: [51.463, 7.18],
				zoom: 10
			});

			L.tileLayer('http://{s}.tile.cloudmade.com/036a729cf53d4388a8ec345e1543ef53/44094/256/{z}/{x}/{y}.png', {
				attribution: '&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors',
				maxZoom: 18
			}).addTo(this.leafletMap);

			return this;
		},
		loadAreas: function(type) {
			$.getJSON('js/' + type + '.json', _.bind(this.addAreas, this));
			return this;
		},
		addAreas: function(geojson) {
			L.geoJson(geojson.features, {
				style: {
					'opacity': 0.5,
					'weight': 2
				},
				onEachFeature: _.bind(this.addArea, this)
			}).addTo(this.leafletMap);
		},
		addArea: function(feature, layer) {
			this.areas.push({
				'key': feature.properties.KN,
				'label': feature.properties.GN,
				'value': layer
			});
		},
		loadData: function(year) {
			$.getJSON('js/' + year + '.json', _.bind(this.setData, this));
			return this;
		},
		setData: function(data) {
			this.data = data;
			this.displayAccount(521);
		},
		displayAccount: function(accountToUse) {
			var currentAccount = this.data.accountMap[accountToUse];
			_.each(this.data.accountsPerAreas, _.bind(function(area) {
				var layer = this.getArea(area.areaKey);

				var total = 0;
				_.each(area.accounts, function(account) {
					if (account.key == accountToUse) {
						if (account.i != null && account.s != null) {
							total += account.i - Math.abs(account.s);
						} else if (account.s != null) {
							total -= Math.abs(account.s);
						}
					}
				});

				var opacity;
				var fillColor;
				if (total <= 0) {
					opacity = Math.round(total / currentAccount.dmin * 100) / 100;
					fillColor = opacity < 0 ? '' : '#FF0000';
				} else {
					opacity = Math.round(total / currentAccount.dmax * 100) / 100;
					fillColor = opacity < 0 ? '' : '#00C957';
				}

				layer.value.setStyle({
					'fillOpacity': opacity,
					'fillColor': fillColor
				});
				layer.value.bindPopup("<strong>" + layer.label + "</strong><br />" + this.data.accountMap[accountToUse].label + ": "
						+ total.formatMoney(0, ',', '.') + " €");
			}, this));
		}
	};

	hdv.map = map;
})(hdv, L, $, _);