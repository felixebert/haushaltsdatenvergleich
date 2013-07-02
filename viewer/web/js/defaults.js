'use strict';

(function(hdv, $, _, Handlebars) {
	var defaults = {
		lat: 51.463,
		lon: 7.18,
		zoom: 9,
		product: 999,
		account: 7,
		relation: 'none',
		areaType: 'gemeinden',
		year: 2009
	};

	var defaultService = {
		init: function() {
			_.extend(hdv.defaults, this.parseSearchQuery(window.location.search));
		},
		parseSearchQuery: function(search) {
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

	hdv.defaults = defaults;
	hdv.defaultService = defaultService;
})(hdv, $, _, Handlebars);
