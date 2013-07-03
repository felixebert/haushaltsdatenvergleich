'use strict';
var hdv = {};

(function(hdv, $, _, Handlebars) {
	hdv.init = function(options) {
		hdv.defaultService.init(options);
		hdv.map.init();
		hdv.loader.init();
		hdv.settingsService.init();
	};
})(hdv, $, _, Handlebars);
