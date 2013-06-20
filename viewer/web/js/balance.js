'use strict';

(function(hdv, $, _, Handlebars) {
	var balance = {
		template: null,
		init: function(key) {
			this.template = Handlebars.compile($('#balance-template').html());

			$.getJSON('data/bilanz/' + key + '.json', _.bind(function(data) {
				this.render(key, data);
			}, this));
		},
		render: function(key, data) {
			$('h1').text('Bilanz von ' + data.label);
			$('.assets').html(this.template(data.assets));
			$('.liabilities').html(this.template(data.liabilities));

			$('.ajax-loader').hide();
		}
	};

	hdv.balance = balance;
})(hdv, $, _, Handlebars);