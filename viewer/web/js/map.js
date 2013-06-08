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
		areaLayers: [],
		data: {},
		getAreaLayer: function(key) {
			return _.find(this.areaLayers, function(area) {
				return area.key == key;
			});
		},
		init: function() {
			this.leafletMap = L.map('map', {
				center: [51.463, 7.18],
				zoom: 9,
				minZoom: 8,
				maxZoom: 11
			});

			$(hdv).on('map.loaded', _.bind(this.refreshComparison, this));
			$('.settings').on('change', _.bind(this.refreshComparison, this));

			return this;
		},
		loadAreaLayers: function(type) {
			$.getJSON('js/' + type + '.json', _.bind(this.addAreaLayers, this));
			return this;
		},
		addAreaLayers: function(geojson) {
			L.geoJson(geojson.features, {
				style: {
					'opacity': 0.5,
					'weight': 2
				},
				onEachFeature: _.bind(this.addAreaLayer, this)
			}).addTo(this.leafletMap);

			$(hdv).triggerHandler('map.loaded');
		},
		addAreaLayer: function(feature, layer) {
			this.areaLayers.push({
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
			this.refreshAccountSelectList();

			$(hdv).triggerHandler('map.loaded');
		},
		refreshAccountSelectList: function() {
			var selectList = $('select[name="pg"]');
			_.each(this.data.accounts, function(account) {
				selectList.append($("<option />").val(account.key).text(account.label));
			});
		},
		nullSafeNumber: function(number) {
			return number === null ? 0 : number;
		},
		getAccountTotal: function(account) {
			var total = 0;
			if (account) {
				total += this.nullSafeNumber(account[0]) - this.nullSafeNumber(account[1]);
			}
			return total;
		},
		getLayerStyle: function(total, min, max) {
			var opacity;
			var fillColor;
			if (total <= 0) {
				opacity = Math.round(total / min * 100) / 100;
				fillColor = opacity < 0 ? '' : '#FF0000';
			} else {
				opacity = Math.round(total / max * 100) / 100;
				fillColor = opacity < 0 ? '' : '#00C957';
			}

			return {
				'fillOpacity': opacity,
				'fillColor': fillColor
			};
		},
		displayAccount: function(accountKey) {
			var currentAccount = this.data.accounts[accountKey];
			_.each(this.data.areas, _.bind(function(area) {
				var areaLayer = this.getAreaLayer(area.key);
				var total = this.getAccountTotal(area.accounts[accountKey]);
				var style = this.getLayerStyle(total, currentAccount.dmin, currentAccount.dmax);

				areaLayer.value.setStyle(style);
				areaLayer.value.bindPopup("<strong>" + area.name + "</strong><br />" + currentAccount.label + ": " + total.formatMoney(0, ',', '.') + " €");
			}, this));
		},
		refreshComparison: function() {
			if (!_.isEmpty(this.data) && !_.isEmpty(this.areaLayers)) {
				var settings = hdv.serialize.toLiteral($('.settings').serializeArray());
				if (settings.pg === 'all') {
					$('select[name="pg"]').val(241);
					settings.pg = 241;
				}
				this.displayAccount(settings.pg);
			}
		}
	};

	hdv.map = map;
})(hdv, L, $, _);