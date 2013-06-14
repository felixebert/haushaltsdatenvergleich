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
		getValueOfArea: function(area, settings) {
			var inOutSum = this.getInOutSum(area.accounts, settings.accounts);
			var inOut = inOutSum;
			if (settings.relation !== 'none') {
				inOut = this.getInOutInRelationTo(inOutSum, area[settings.relation]);
			}
			return this.getValue(inOut, settings.compare);
		},
		getValue: function(inOut, compare) {
			if (compare === 'in') {
				return this.nullSafeNumber(inOut[0]);
			}
			if (compare === 'out') {
				return this.nullSafeNumber(inOut[1]);
			}
			return this.nullSafeNumber(inOut[0]) - this.nullSafeNumber(inOut[1]);
		},
		getInOutSum: function(areaAccountsInOut, accounts) {
			var inOut = [0, 0];
			_.each(accounts, _.bind(function(account) {
				var accountInOut = areaAccountsInOut[account];
				if (accountInOut !== undefined) {
					inOut[0] += this.nullSafeNumber(accountInOut[0]);
					inOut[1] += this.nullSafeNumber(accountInOut[1]);
				}
			}, this));

			return inOut;
		},
		getInOutInRelationTo: function(inOut, relation) {
			return [this.getValueInRelationTo(inOut[0], relation), this.getValueInRelationTo(inOut[1], relation)];
		},
		getValueInRelationTo: function(value, relation) {
			return Math.round(value / relation);
		},
		/**
		 * @param value -
		 *            value of the layer
		 * @param boundary -
		 *            boundary array [max / min]
		 * @param compare -
		 *            what to compare? (in / out / sum)
		 */
		getLayerStyle: function(value, log10Boundary, compare) {
			return {
				'fillOpacity': this.getOpacity(value, log10Boundary),
				'fillColor': this.getFillColor(value, compare)
			};
		},
		getFillColor: function(value, compare) {
			if (value == 0) {
				return '#888';
			} else {
				return value <= 0 || compare === 'out' ? '#FF0000' : '#00C957';
			}
		},
		getOpacity: function(value, log10Boundary) {
			if (value === 0) {
				return 0.25;
			}
			var opacity = Math.round(0.75 * this.getOpacityFactor(value, log10Boundary) * 100) / 100;
			return Math.max(0.1, opacity);
		},
		getOpacityFactor: function(value, log10Boundary) {
			return Math.round((hdv.calc.safeLog10(value) - log10Boundary[1]) / (log10Boundary[0] - log10Boundary[1]) * 100) / 100;
		},
		refreshLayers: function(settings) {
			var boundaries = hdv.accountBoundaries.findAccordingTo(settings);
			var log10Boundaries = hdv.accountBoundaries.toLog10(boundaries);

			_.each(hdv.map.data.areas, _.bind(function(area) {
				var areaLayer = hdv.map.getAreaLayer(area.key);
				if (areaLayer) {
					var areaValue = this.getValueOfArea(area, settings);
					var boundary = hdv.accountBoundaries.forValue(areaValue, log10Boundaries);

					var style = this.getLayerStyle(areaValue, boundary, settings.compare);
					var html = "<strong>" + areaLayer.label + "</strong><br />" + hdv.formatter.currency(areaValue) + " €";

					areaLayer.value.setStyle(style);
					areaLayer.value.bindPopup(html);
				} else {
					console.error('no layer for area ' + area.key);
				}
			}, this));
		},
		refresh: function() {
			var settings = hdv.serialize.toLiteral($('.settings').serializeArray());
			settings.accounts = hdv.accounts.getSelectedAccounts(settings.pb, settings.pg);
			settings.boundaryAccount = hdv.accounts.getTopAccount(settings.pb, settings.pg);

			this.refreshLayers(settings);
		}
	};

	hdv.areas = areas;
	areas.init();
})(hdv, $, _);