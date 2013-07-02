'use strict';
var hdv = {
	defaults: {
		lat: 51.463,
		lon: 7.18,
		zoom: 9,
		product: 999,
		account: 400,
		relation: 'none',
		areaLayer: 'gemeinden',
		year: 2009
	},
	init: function() {
		this.initDefaults();
		hdv.map.init(this.defaults);
		hdv.loader.init();
		hdv.settingsService.init();
	},
	initDefaults: function() {
		_.extend(hdv.defaults, this.extractDefaultsInSearchQuery(window.location.search));
	},
	extractDefaultsInSearchQuery: function(search) {
		var defaults = {};
		if (search) {
			var params = search.substr(1).split('&');
			$.each(params, function(index, elem) {
				var keyValue = elem.split("=");
				if (keyValue.length == 2)
					defaults[keyValue[0]] = keyValue[1];
			});
		}
		return defaults;
	}
};
