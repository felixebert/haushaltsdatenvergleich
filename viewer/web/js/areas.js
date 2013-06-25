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

	var areas = {
		init: function() {
			$(hdv).on('map.ready', _.bind(this.refresh, this));
			$('.settings').on('change', _.bind(this.refresh, this));
		},
		getCurrentValueLabel: function() {
			var compare = $('.settings input[name="compare"]:checked').data('label');
			var relation = $('.settings input[name="relation"]:checked').data('label');
			return compare + (relation ? ' ' + relation : '');
		},
		getTemplateObject: function(valueLabel, layer, area, value, settings) {
			return {
				'valueLabel': valueLabel,
				'areaLabel': layer.label,
				'area': area,
				'value': value,
				'accountInOut': area.accounts[settings.account],
				'keyForBalance': hdv.balance.getKeyForArea(area.key, layer.attribute)
			};
		},
		refreshLayer: function(area, log10Boundaries, valueLabel, settings) {
			var layer = hdv.map.getAreaLayer(area.key);
			if (layer) {
				var value = areaValue.ofArea(area, settings);
				var boundary = hdv.accountBoundaries.forValue(value, log10Boundaries);

				layer.value.setStyle(hdv.layerStyle.forValue(value, boundary, this.hasNegativeMeaning(settings.compare)));
				layer.value.bindPopup(hdv.map.templates.popup(this.getTemplateObject(valueLabel, layer, area, value, settings)));
			} else {
				console.error('no layer for area ' + area.key);
			}
		},
		hasNegativeMeaning: function(compare) {
			return compare === 'out' ? true : false;
		},
		refreshLayers: function(settings) {
			var boundaries = hdv.accountBoundaries.findAccordingTo(settings);
			var log10Boundaries = hdv.accountBoundaries.toLog10(boundaries);
			var valueLabel = this.getCurrentValueLabel();

			_.each(hdv.map.data.areas, _.bind(function(area) {
				this.refreshLayer(area, log10Boundaries, valueLabel, settings);
			}, this));
		},
		refresh: function() {
			if (_.isEmpty(hdv.map.data) || _.isEmpty(hdv.map.areaLayers)) {
				return false;
			}

			var settings = hdv.serialize.toLiteral($('.settings').serializeArray());
			settings.account = hdv.accounts.getSelectedAccount(settings.pg);

			this.refreshLayers(settings);
		}

	};

	hdv.areas = areas;
	hdv.areaValue = areaValue;
	areas.init();
})(hdv, $, _, Handlebars);
