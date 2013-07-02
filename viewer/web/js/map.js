'use strict';
(function(hdv, L, $, _) {
	var SettingsControl = L.Control.extend({
		options: {
			position: 'topleft'
		},
		onAdd: function(map) {
			var container = L.DomUtil.create('div', 'leaflet-control-settings leaflet-bar');

			this.toggleSettingsButton = this._createButton("*", "Einstellungen", 'leaflet-control-toggle-settings leaflet-bar-part leaflet-bar-part-top',
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

	var settings = {};
	var settingsService = {
		init: function() {
			$('.settings').on('change', _.bind(this.update, this));
			this.update();
		},
		update: function() {
			var settings = hdv.serialize.toLiteral($('.settings').serializeArray());
			hdv.settings = this.mergeSettings(settings, hdv.defaults);
			$(hdv).triggerHandler('settingsUpdate');
		},
		mergeSettings: function(settings, defaults) {
			settings.product = this.mergeProperty(settings.product, defaults.product);
			settings.account = this.mergeProperty(settings.account, defaults.account);
			return settings;
		},
		mergeProperty: function(value, defaultValue) {
			return !value || value === 'none' ? defaultValue : value;
		},
		resetAccount: function() {
			hdv.settings = hdv.defaults.account;
		}
	};

	var loader = {
		loadStatus: {},
		init: function() {
			$(hdv).on('loaded.areaLayers loaded.data loaded.meta', _.bind(this.done, this));
			$(hdv).on('settingsUpdate', _.bind(this.update, this));
		},
		update: function() {
			this.loadAreaLayers(hdv.settings.areaType);
			this.loadValues(this.getValueFile(hdv.settings.areaType, hdv.settings.year, hdv.settings.product));
			this.loadMetadata(this.getMetadataFile(hdv.settings.areaType, hdv.settings.year));
		},
		done: function() {
			if (this.allLoaded()) {
				$('.ajax-loader').hide();
				$(hdv).triggerHandler('loader.finished');
				// $('.settings select[name="pg"] option[value="' +
				// hdv.defaults.pg + '"]').prop('selected', true);
				// $('.settings select[name="year"] option[value="' +
				// hdv.defaults.year + '"]').prop('selected', true);
			}
		},
		allLoaded: function() {
			return this.areaLayersLoaded(hdv.settings.areaType)
					&& this.valuesLoaded(this.getValueFile(hdv.settings.areaType, hdv.settings.year, hdv.settings.product))
					&& this.metadataLoaded(this.getMetadataFile(hdv.settings.areaType, hdv.settings.year));
		},
		areaLayersLoaded: function(areaType) {
			return this.loadStatus.areaType === areaType;
		},
		loadAreaLayers: function(areaType) {
			if (!this.areaLayersLoaded(areaType)) {
				$('.ajax-loader').show();

				$.getJSON('data/' + areaType + '.geojson', _.bind(function(data) {
					hdv.map.removeLayers(hdv.data.areaLayers);
					hdv.data.areaLayers = [];

					hdv.map.addAreaLayers(data, _.bind(hdv.data.addAreaLayer, hdv.data));

					this.loadStatus.areaType = areaType;
					$(hdv).triggerHandler('loaded.areaLayers');
				}, this));
			}
		},
		getValueFile: function(areaType, year, product) {
			return year + '/' + areaType + '/' + product + '.json';
		},
		valuesLoaded: function(valueFile) {
			return this.loadStatus.values === valueFile;
		},
		loadValues: function(valueFile) {
			if (!this.valuesLoaded(valueFile)) {
				$('.ajax-loader').show();

				$.getJSON('data/' + valueFile, _.bind(function(data) {
					hdv.data.values = data;
					this.loadStatus.values = valueFile;
					$(hdv).triggerHandler('loaded.data');
				}, this));
			}
		},
		getMetadataFile: function(areaType, year) {
			return year + '/' + areaType + '/metadata.json';
		},
		metadataLoaded: function(metadataFile) {
			return this.loadStatus.metadata === metadataFile;
		},
		loadMetadata: function(metadataFile) {
			if (!this.metadataLoaded(metadataFile)) {
				$('.ajax-loader').show();

				$.getJSON('data/' + metadataFile, _.bind(function(data) {
					hdv.data.meta = data;
					this.loadStatus.metadata = metadataFile;
					$(hdv).triggerHandler('loaded.metadata');
				}, this));
			}
		}
	};

	var data = {
		areaLayers: [],
		meta: {},
		values: {},
		getAreaLayer: function(key) {
			return _.find(this.areaLayers, function(area) {
				return area.key == key;
			});
		},
		addAreaLayer: function(feature, layer) {
			this.areaLayers.push({
				'key': feature.properties.KN ? feature.properties.KN : feature.properties.AGS,
				'label': feature.properties.GN ? feature.properties.GN : feature.properties.GEN,
				'attribute': feature.properties.DES,
				'value': layer
			});
		}
	};

	var map = {
		leafletMap: null,
		templates: {},
		init: function() {
			this.leafletMap = L.map('map', {
				center: [hdv.defaults.lat, hdv.defaults.lon],
				zoom: hdv.defaults.zoom,
				minZoom: 8,
				maxZoom: 11,
				attributionControl: false
			});

			this.setupForm(hdv.defaults);
			this.addTileLayer();
			this.addAttributionControl();
			this.setupModals();
			this.addSettingsControl();
			this.setupTemplates();
		},
		setupForm: function(defaults) {
			$('.settings input[name="relation"]').filter('[value="' + hdv.defaults.relation + '"]').prop('checked', true);
			$('.settings input[name="areaType"]').filter('[value="' + hdv.defaults.areaType + '"]').prop('checked', true);
		},
		setupTemplates: function() {
			this.templates.popup = Handlebars.compile($('#popup-template').html());
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
		setupModals: function() {
			var modals = ['info', 'imprint'];
			_.each(modals, function(modalId) {
				$('#' + modalId).modal({
					'show': false
				});
			});
		},
		addSettingsControl: function() {
			this.settingsControl = new SettingsControl().addTo(this.leafletMap);
			if ($(window).width() > 979) {
				this.settingsControl.toggleNav();
			}

			$('.close-nav').on('click', _.bind(function() {
				this.settingsControl.toggleNav();
			}, this));
		},
		removeLayers: function(layers) {
			_.each(layers, _.bind(function(layer) {
				this.leafletMap.removeLayer(layer.value);
			}, this));
		},
		addAreaLayers: function(geojson, callback) {
			L.geoJson(geojson.features, {
				style: {
					'opacity': 0.5,
					'weight': 1
				},
				onEachFeature: callback
			}).addTo(this.leafletMap);
		}
	};

	hdv.map = map;
	hdv.loader = loader;
	hdv.settingsService = settingsService;
	hdv.data = data;
	hdv.settings = settings;
})(hdv, L, $, _);
