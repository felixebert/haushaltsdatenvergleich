'use strict';
(function(hdv, Handlebars) {
	Handlebars.registerHelper('number', function(n) {
		return hdv.formatter.number(n);
	});

	Handlebars.registerHelper('eachProperty', function(context, options) {
		var result = "";
		for ( var key in context) {
			result = result + options.fn({
				'key': key,
				'value': context[key]
			});
		}
		return result;
	});
})(hdv, Handlebars);