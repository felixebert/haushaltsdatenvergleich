'use strict';

(function(hdv, L, $, _) {
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

			L.tileLayer('http://{s}.tile.cloudmade.com/036a729cf53d4388a8ec345e1543ef53/44094/256/{z}/{x}/{y}.png', {
				attribution: '&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors',
				maxZoom: 18
			}).addTo(this.leafletMap);

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
					'weight': 1
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
			_.each(_.keys(this.data.tree), _.bind(function(groupKey) {
				var groupAccount = this.data.accounts[groupKey];
				var optGroup = $('<optgroup />').attr('label', groupAccount.label);
				_.each(this.data.tree[groupKey], _.bind(function(accountKey) {
					var account = this.data.accounts[accountKey];
					optGroup.append($("<option />").val(account.key).text(account.label));
				}, this));
				selectList.append(optGroup);
			}, this));
		},
		refreshAccountGroupSelectList: function(group) {
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
			var boundary = total <= 0 ? min : max;
			return {
				'fillOpacity': this.getOpacity(total, boundary),
				'fillColor': this.getFillColor(total)
			};
		},
		getFillColor: function(value) {
			if (value == 0) {
				return '#888';
			} else {
				return value <= 0 ? '#FF0000' : '#00C957';
			}
		},
		getOpacity: function(value, boundary) {
			return value === 0 ? 0.25 : Math.round(0.75 * this.getOpacityFactor(value, boundary) * 100) / 100;
		},
		getOpacityFactor: function(value, boundary) {
			return Math.round((this.getBaseLog(value)) / this.getBaseLog(boundary) * 100) / 100;
		},
		getBaseLog: function(number) {
			return Math.log(Math.abs(number)) / Math.log(10);
		},
		displayAccount: function(accountKey) {
			var currentAccount = this.data.accounts[accountKey];
			_.each(this.data.areas, _.bind(function(area) {
				var areaLayer = this.getAreaLayer(area.key);
				var total = this.getAccountTotal(area.accounts[accountKey]);
				var style = this.getLayerStyle(total, currentAccount.dmin, currentAccount.dmax);

				areaLayer.value.setStyle(style);
				areaLayer.value
						.bindPopup("<strong>" + areaLayer.label + "</strong><br />" + currentAccount.label + ": " + hdv.formatter.currency(total) + " €");
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