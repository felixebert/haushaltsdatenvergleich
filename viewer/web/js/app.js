'use strict';
var hdv = {};

(function(hdv, $, _, Handlebars) {
	hdv.init = function() {
		hdv.defaultService.init();
		hdv.map.init();
		hdv.loader.init();
		hdv.settingsService.init();
	};
})(hdv, $, _, Handlebars);
