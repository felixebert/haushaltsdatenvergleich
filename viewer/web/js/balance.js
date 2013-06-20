'use strict';

(function(hdv, $, _, Handlebars) {
	var balance = {
		template: null,
		init: function(key) {
			this.template = Handlebars.compile($('#balance-template').html());

			var handleSuccess = function(data) {
				this.render(key, data);
			};

			var handleError = function() {
				$('.load-error').text('Keine Bilanz für Gemeindeschlüssel ' + key + ' vorhanden!');
				$('.alert').show();
			};

			var postLoad = function() {
				$('.ajax-loader').hide();
			};

			$.getJSON('data/bilanz/' + key + '.json').done(_.bind(handleSuccess, this)).fail(handleError).always(postLoad);
		},
		render: function(key, data) {
			$('h1').text('Bilanz von ' + data.label);
			$('.assets').html(this.template(data.assets));
			$('.liabilities').html(this.template(data.liabilities));
		}
	};

	hdv.balance = balance;
})(hdv, $, _, Handlebars);