'use strict';
(function(hdv, Handlebars) {
	Handlebars.registerHelper('number', function(n) {
		return hdv.formatter.number(n);
	});
})(hdv, Handlebars);