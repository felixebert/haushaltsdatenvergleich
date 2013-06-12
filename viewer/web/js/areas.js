'use strict';

(function(hdv, $, _) {
	var areas = {
		init: function() {
			$(hdv).on('map.ready', _.bind(this.refresh, this));
			$('.settings').on('change', _.bind(this.refresh, this));
		},
		nullSafeNumber: function(number) {
			return number === null ? 0 : number;
		},
		getAccountTotal: function(account) {
			var total = 0;
			if (account) {
				total += this.nullSafeNumber(account[0]) - this.nullSafeNumber(account[1]);
			}
			return total;
		},
		getLayerStyle: function(total, min, max) {
			var boundary = total <= 0 ? min : max;
			return {
				'fillOpacity': this.getOpacity(total, boundary),
				'fillColor': this.getFillColor(total)
			};
		},
		getFillColor: function(value) {
			if (value == 0) {
				return '#888';
			} else {
				return value <= 0 ? '#FF0000' : '#00C957';
			}
		},
		getOpacity: function(value, boundary) {
			return value === 0 ? 0.25 : Math.round(0.75 * this.getOpacityFactor(value, boundary) * 100) / 100;
		},
		getOpacityFactor: function(value, boundary) {
			return Math.round((this.getBaseLog(value)) / this.getBaseLog(boundary) * 100) / 100;
		},
		getBaseLog: function(number) {
			return Math.log(Math.abs(number)) / Math.log(10);
		},
		displayAccount: function(accountKey) {
			var currentAccount = hdv.map.data.accounts[accountKey];
			_.each(hdv.map.data.areas, _.bind(function(area) {
				var areaLayer = hdv.map.getAreaLayer(area.key);
				var total = this.getAccountTotal(area.accounts[accountKey]);
				var style = this.getLayerStyle(total, currentAccount.dmin, currentAccount.dmax);

				areaLayer.value.setStyle(style);
				areaLayer.value
						.bindPopup("<strong>" + areaLayer.label + "</strong><br />" + currentAccount.label + ": " + hdv.formatter.currency(total) + " €");
			}, this));
		},
		refreshLayers: function(settings) {
			this.displayAccount(255);
		},
		refresh: function() {
			var settings = hdv.serialize.toLiteral($('.settings').serializeArray());
			settings.accounts = hdv.accounts.getSelectedAccounts(settings.pb, settings.pg);

			this.refreshLayers(settings);
		}
	};

	hdv.areas = areas;
	areas.init();
})(hdv, $, _);