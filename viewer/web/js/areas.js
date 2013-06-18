'use strict';

(function(hdv, $, _) {
	var nullSafeNumber = function(number) {
		return number === null ? 0 : number;
	};

	var areaValue = {
		ofArea: function(area, settings) {
			var inOutSum = this.getInOutSum(area.accounts, settings.accounts);
			var inOut = inOutSum;
			if (settings.relation !== 'none') {
				inOut = this.getInOutInRelationTo(inOutSum, area[settings.relation]);
			}
			return this.getValue(inOut, settings.compare);
		},
		getValue: function(inOut, compare) {
			if (compare === 'in') {
				return nullSafeNumber(inOut[0]);
			}
			if (compare === 'out') {
				return nullSafeNumber(inOut[1]);
			}
			return nullSafeNumber(inOut[0]) - nullSafeNumber(inOut[1]);
		},
		getInOutSum: function(areaAccountsInOut, accounts) {
			var inOut = [0, 0];
			_.each(accounts, _.bind(function(account) {
				var accountInOut = areaAccountsInOut[account];
				if (accountInOut !== undefined) {
					inOut[0] += nullSafeNumber(accountInOut[0]);
					inOut[1] += nullSafeNumber(accountInOut[1]);
				}
			}, this));

			return inOut;
		},
		getInOutInRelationTo: function(inOut, relation) {
			return [this.getValueInRelationTo(inOut[0], relation), this.getValueInRelationTo(inOut[1], relation)];
		},
		getValueInRelationTo: function(value, relation) {
			return Math.round(value / relation);
		}
	};

	var areas = {
		init: function() {
			$(hdv).on('map.ready', _.bind(this.refresh, this));
			$('.settings').on('change', _.bind(this.refresh, this));
		},
		/**
		 * @param value
		 *            value of the layer
		 * @param boundary
		 *            boundary array [max / min]
		 * @param compare
		 *            what to compare? (in / out / sum)
		 */
		getLayerStyle: function(value, log10Boundary, compare) {
			var opacity = this.getOpacity(value, log10Boundary);
			return {
				'fillOpacity': opacity,
				'fillColor': this.getFillColor(value, compare)
			};
		},
		getFillColor: function(value, compare) {
			if (value == 0) {
				return '#888';
			} else if (value <= 0 || compare === 'out') {
				return '#FF0000';
			} else {
				return '#00C957';
			}
		},
		getOpacity: function(value, log10Boundary) {
			if (value === 0) {
				return 0.25;
			}
			var opacity = Math.round(0.75 * this.getOpacityFactor(value, log10Boundary) * 100) / 100;
			return Math.max(0.2, opacity);
		},
		getOpacityFactor: function(value, log10Boundary) {
			return Math.round((hdv.calc.safeLog10(value) - log10Boundary[1]) / (log10Boundary[0] - log10Boundary[1]) * 100) / 100;
		},
		refreshLayers: function(settings) {
			var boundaries = hdv.accountBoundaries.findAccordingTo(settings);
			var log10Boundaries = hdv.accountBoundaries.toLog10(boundaries);

			_.each(hdv.map.data.areas, _.bind(function(area) {
				var layer = hdv.map.getAreaLayer(area.key);
				if (layer) {
					var value = areaValue.ofArea(area, settings);
					var boundary = hdv.accountBoundaries.forValue(value, log10Boundaries);

					var style = this.getLayerStyle(value, boundary, settings.compare);
					var html = "<strong>" + layer.label + "</strong><br />" + hdv.formatter.currency(value) + " €";

					layer.value.setStyle(style);
					layer.value.bindPopup(html);
				} else {
					console.error('no layer for area ' + area.key);
				}
			}, this));
		},
		refresh: function() {
			if (_.isEmpty(hdv.map.data) || _.isEmpty(hdv.map.areaLayers)) {
				return false;
			}
			var settings = hdv.serialize.toLiteral($('.settings').serializeArray());
			settings.accounts = hdv.accounts.getSelectedAccounts(settings.pb, settings.pg);
			settings.boundaryAccount = hdv.accounts.getTopAccount(settings.pb, settings.pg);

			this.refreshLayers(settings);
		}
	};

	hdv.areas = areas;
	hdv.areaValue = areaValue;
	areas.init();
})(hdv, $, _);