'use strict';

(function(hdv, L, $, _) {
	var map = {
		leafletMap: null,
		areaLayers: [],
		data: {},
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

			$(hdv).on('map.loaded.areaLayers map.loaded.data', _.bind(this.fireMapIsReady, this));

			return this;
		},
		fireMapIsReady: function() {
			if (!_.isEmpty(this.data) && !_.isEmpty(this.areaLayers)) {
				$(hdv).triggerHandler('map.ready');
			}
		},
		loadAreaLayers: function(type) {
			$.getJSON('js/' + type + '.geojson', _.bind(this.addAreaLayers, this));
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

			$(hdv).triggerHandler('map.loaded.areaLayers');
		},
		addAreaLayer: function(feature, layer) {
			this.areaLayers.push({
				'key': feature.properties.KN,
				'label': feature.properties.GN,
				'value': layer
			});
		},
		getAreaLayer: function(key) {
			return _.find(this.areaLayers, function(area) {
				return area.key == key;
			});
		},
		loadData: function(file) {
			$.getJSON('js/' + file + '.json', _.bind(this.setData, this));
			return this;
		},
		setData: function(data) {
			this.data = data;

			$(hdv).triggerHandler('map.loaded.data');
		}
	};

	hdv.map = map;
})(hdv, L, $, _);