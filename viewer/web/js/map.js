'use strict';
(function(hdv, L, $, _) {
	var map = {
		leafletMap: null,
		areaLayers: [],
		data: {},
		loadedAreaLayers: null,
		loadedData: null,
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

			$('.settings').on('change', _.bind(this.reload, this));
			this.reload();

			return this;
		},
		fireMapIsReady: function() {
			if (!_.isEmpty(this.data) && !_.isEmpty(this.areaLayers)) {
				$(hdv).triggerHandler('map.ready');
			}
		},
		loadAreaLayers: function(type) {
			if (this.loadedAreaLayers !== type) {
				_.each(this.areaLayers, _.bind(function(areaLayer) {
					this.leafletMap.removeLayer(areaLayer.value);
				}, this));
				this.areaLayers = [];
				this.loadedAreaLayers = null;

				$.getJSON('data/' + type + '.geojson', _.bind(function(data) {
					this.addAreaLayers(data);
					this.loadedAreaLayers = type;
					$(hdv).triggerHandler('map.loaded.areaLayers');
				}, this));
			}
		},
		addAreaLayers: function(geojson) {
			L.geoJson(geojson.features, {
				style: {
					'opacity': 0.5,
					'weight': 1
				},
				onEachFeature: _.bind(this.addAreaLayer, this)
			}).addTo(this.leafletMap);
		},
		addAreaLayer: function(feature, layer) {
			this.areaLayers.push({
				'key': feature.properties.KN ? feature.properties.KN : feature.properties.AGS,
				'label': feature.properties.GN ? feature.properties.GN : feature.properties.GEN,
				'value': layer
			});
		},
		getAreaLayer: function(key) {
			return _.find(this.areaLayers, function(area) {
				return area.key == key;
			});
		},
		loadData: function(areaType, year) {
			var jsonFile = 'finanzen-' + areaType + '-' + year;
			if (this.loadedData !== jsonFile) {
				this.data = {};
				this.loadedData = null;

				$.getJSON('data/' + jsonFile + '.json', _.bind(function(data) {
					this.setData(data);
					this.loadedData = jsonFile;
					$(hdv).triggerHandler('map.loaded.data');
				}, this));
			}
		},
		setData: function(data) {
			this.data = data;
		},
		reload: function() {
			var settings = hdv.serialize.toLiteral($('.settings').serializeArray());
			this.loadAreaLayers(settings.areaLayer);
			this.loadData(settings.areaLayer, settings.year);
		}
	};

	hdv.map = map;
})(hdv, L, $, _);