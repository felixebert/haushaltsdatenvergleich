'use strict';
(function(hdv, L, $, _) {
	var SettingsControl = L.Control.extend({
		options: {
			position: 'topleft'
		},
		onAdd: function(map) {
			var container = L.DomUtil.create('div', 'leaflet-control-settings leaflet-bar leaflet-bar-part');

			this.toggleSettingsButton = this._createButton("&#9776;", "Einstellungen", 'leaflet-control-toggle-settings leaflet-bar-part leaflet-bar-part-top',
					container, this.onToggleSettingsClick, this);
			this._bindToggleButton(this.toggleSettingsButton, this.toggleNav);

			this.toggleInfoButton = this._createButton("i", "Information", 'leaflet-control-info leaflet-bar-part leaflet-bar-part-bottom', container);
			this._bindToggleButton(this.toggleInfoButton, this.toggleInfo);

			return container;
		},
		_createButton: function(html, title, className, container, fn, context) {
			var link = L.DomUtil.create('a', className, container);
			link.innerHTML = html;
			link.href = '#';
			link.title = title;

			return link;
		},
		_bindToggleButton: function(button, fn) {
			var stop = L.DomEvent.stopPropagation;
			L.DomEvent.on(button, 'click', stop);
			L.DomEvent.on(button, 'mousedown', stop);
			L.DomEvent.on(button, 'dblclick', stop);
			L.DomEvent.on(button, 'click', L.DomEvent.preventDefault);
			L.DomEvent.on(button, 'click', function(e) {
				fn.call(this);
			}, this);
		},
		toggleNav: function() {
			$('#nav').toggleClass('hide');
			$(this.toggleSettingsButton).toggleClass('leaflet-control-active');
		},
		toggleInfo: function() {
			$('#info').modal('toggle');
		}
	});

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
				maxZoom: 11,
				attributionControl: false
			});

			this.addTileLayer();
			this.addAttributionControl();
			this.initModals();
			this.addSettingsControl();

			this.setupEvents();
			this.reload();
		},
		setupEvents: function() {
			$(hdv).on('map.loaded.areaLayers map.loaded.data', _.bind(this.fireMapIsReady, this));
			$('.settings').on('change', _.bind(this.reload, this));
		},
		addTileLayer: function() {
			L.tileLayer('http://{s}.tile.cloudmade.com/036a729cf53d4388a8ec345e1543ef53/44094/256/{z}/{x}/{y}.png', {
				'maxZoom': 18
			}).addTo(this.leafletMap);
		},
		addAttributionControl: function() {
			var attribution = '<a class="imprint">Impressum</a>';
			L.control.attribution().setPrefix(null).addAttribution(attribution).addTo(this.leafletMap);

			$('.imprint').on('click', function() {
				$('#imprint').modal('toggle');
			});
		},
		initModals: function() {
			$('#info').modal({
				'show': false
			});
			$('#imprint').modal({
				'show': false
			});
		},
		addSettingsControl: function() {
			this.settingsControl = new SettingsControl().addTo(this.leafletMap);
			if ($(window).width() > 979) {
				this.settingsControl.toggleNav();
			}
		},
		fireMapIsReady: function() {
			if (!_.isEmpty(this.data) && !_.isEmpty(this.areaLayers)) {
				$('.ajax-loader').hide();
				$(hdv).triggerHandler('map.ready');
			}
		},
		loadAreaLayers: function(type) {
			if (this.loadedAreaLayers !== type) {
				$('.ajax-loader').show();

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
				$('.ajax-loader').show();
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