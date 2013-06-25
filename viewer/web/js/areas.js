'use strict';

(function(hdv, $, _, Handlebars) {
	var nullSafeNumber = function(number) {
		return number === null ? 0 : number;
	};

	var areaValue = {
		ofArea: function(area, settings) {
			var inOut = this.getInOut(area.accounts, settings.account);
			if (settings.relation !== 'none') {
				inOut = this.getInOutInRelationTo(inOut, area[settings.relation]);
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
		getInOut: function(areaAccountsInOut, account) {
			var inOut = [0, 0];
			var accountInOut = areaAccountsInOut[account];
			if (accountInOut !== undefined) {
				inOut[0] += nullSafeNumber(accountInOut[0]);
				inOut[1] += nullSafeNumber(accountInOut[1]);
			}

			return inOut;
		},
		getInOutInRelationTo: function(inOut, relation) {
			return [this.getValueInRelationTo(inOut[0], relation), this.getValueInRelationTo(inOut[1], relation)];
		},
		getValueInRelationTo: function(value, relation) {
			return Math.round((value / relation) * 100) / 100;
		}
	};

	var colors = {
		red: ["#fee0d2", "#fcbba1", "#fc9272", "#fb6a4a", "#ef3b2c", "#cb181d", "#a50f15", "#67000d"],
		green: ["#e5f5e0", "#c7e9c0", "#a1d99b", "#74c476", "#41ab5d", "#238b45", "#006d2c", "#00441b"]
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
			return {
				'fillOpacity': 0.65,
				'fillColor': this.getFillColor(value, compare, log10Boundary)
			};
		},
		getFillColor: function(value, compare, log10Boundary) {
			if (value == 0) {
				return '#EEE';
			}

			var colorScheme = (value <= 0 || compare === 'out') ? colors.red : colors.green;
			var factor = this.getComparisonFactor(value, log10Boundary);
			var colorIndex = Math.max(0, Math.round((colorScheme.length - 1) * factor));
			return colorScheme[colorIndex];
		},
		getOpacity: function(value, log10Boundary) {
			if (value === 0) {
				return 0.25;
			}
			var opacity = Math.round(0.75 * this.getComparisonFactor(value, log10Boundary) * 100) / 100;
			return Math.max(0.2, opacity);
		},
		getComparisonFactor: function(value, log10Boundary) {
			if (log10Boundary[0] === log10Boundary[1]) {
				return 1;
			}
			return Math.round((hdv.calc.safeLog10(value) - log10Boundary[1]) / (log10Boundary[0] - log10Boundary[1]) * 100) / 100;
		},
		refreshLayers: function(settings) {
			var boundaries = hdv.accountBoundaries.findAccordingTo(settings);
			var log10Boundaries = hdv.accountBoundaries.toLog10(boundaries);
			var valueLabel = this.getCurrentValueLabel();

			_.each(hdv.map.data.areas, _.bind(function(area) {
				this.refreshLayer(area, log10Boundaries, valueLabel, settings);
			}, this));
		},
		refreshLayer: function(area, log10Boundaries, valueLabel, settings) {
			var layer = hdv.map.getAreaLayer(area.key);
			if (layer) {
				var value = areaValue.ofArea(area, settings);
				var boundary = hdv.accountBoundaries.forValue(value, log10Boundaries);

				layer.value.setStyle(this.getLayerStyle(value, boundary, settings.compare));
				layer.value.bindPopup(hdv.map.templates.popup(this.getTemplateObject(valueLabel, layer, area, value, settings)));
			} else {
				console.error('no layer for area ' + area.key);
			}
		},
		getTemplateObject: function(valueLabel, layer, area, value, settings) {
			return {
				'valueLabel': valueLabel,
				'areaLabel': layer.label,
				'area': area,
				'value': value,
				'accountInOut': area.accounts[settings.account],
				'keyForBalance': this.getKeyForBalance(area.key, layer.attribute)
			};
		},
		getKeyForBalance: function(areaKey, areaAttribute) {
			if (areaKey.length > 5) {
				return areaKey;
			}
			return areaAttribute === 'Kreis' ? areaKey + '001' : areaKey + '000';
		},
		refresh: function() {
			if (_.isEmpty(hdv.map.data) || _.isEmpty(hdv.map.areaLayers)) {
				return false;
			}

			var settings = hdv.serialize.toLiteral($('.settings').serializeArray());
			settings.account = hdv.accounts.getSelectedAccount(settings.pg);

			this.refreshLayers(settings);
		},
		getCurrentValueLabel: function() {
			var compare = $('.settings input[name="compare"]:checked').data('label');
			var relation = $('.settings input[name="relation"]:checked').data('label');
			return compare + (relation ? ' ' + relation : '');
		}

	};

	hdv.areas = areas;
	hdv.areaValue = areaValue;
	areas.init();
})(hdv, $, _, Handlebars);
