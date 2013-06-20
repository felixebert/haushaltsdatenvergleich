'use strict';
var hdv = {
	defaults: {
		lat: 51.463,
		lon: 7.18,
		zoom: 9,
		pg: 523,
		compare: 'in',
		relation: 'none',
		areaLayer: 'gemeinden',
		year: 2009
	},
	initDefaults: function() {
		var search = window.location.search;
		if (search != "") {
			var params = search.substr(1).split('&');
			$.each(params, function(index, elem) {
				var keyValue = elem.split("=");
				if (keyValue.length == 2)
					hdv.defaults[keyValue[0]] = keyValue[1];
			});
		}
	}
};
