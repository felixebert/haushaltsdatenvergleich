'use strict';

(function(hdv, $, _, Handlebars) {
	var nullSafeNumber = function(number) {
		return (number === null || number === undefined) ? 0 : number;
	};

	var areaValue = {
		of: function(area, settings) {
			return this.findValue(area.products, settings.pg, settings.account);
		},
		inRelationTo: function(value, settings) {
			if (settings.relation !== 'none') {
				return this.getValueInRelationTo(value, area[settings.relation]);
			}
			return value;
		},
		findValue: function(valuesByProductAndAccount, product, account) {
			var value = 0;
			var accountValues = valuesByProductAndAccount[product];
			if (!_.isEmpty(accountValues)) {
				value = nullSafeNumber(accountValues[account]);
			}

			return value;
		},
		getValueInRelationTo: function(value, relation) {
			return Math.round((value / relation) * 100) / 100;
		},
		getCurrentLabel: function() {
			var account = $('.settings select[name="account"] option:selected').text();
			var relation = $('.settings input[name="relation"]:checked').data('label');
			return this.getLabel(account, relation);
		},
		getLabel: function(accountName, relationLabel) {
			return accountName + (relationLabel ? ' ' + relationLabel : '');
		}
	};

	var areas = {
		init: function() {
			$(hdv).on('map.ready', _.bind(this.refresh, this));
			$('.settings').on('change', _.bind(this.refresh, this));
		},
		getTemplateObject: function(valueLabel, layer, area, value, accountValue) {
			return {
				'valueLabel': valueLabel,
				'areaLabel': layer.label,
				'area': area,
				'value': value,
				'accountValue': accountValue,
				'keyForBalance': hdv.balance.getKeyForArea(area.key, layer.attribute)
			};
		},
		refreshLayer: function(area, log10Boundaries, valueLabel, isSpending, settings) {
			var layer = hdv.map.getAreaLayer(area.key);
			if (layer) {
				var value = areaValue.of(area, settings);
				var comparisonValue = areaValue.inRelationTo(value, settings);

				var boundary = hdv.accountBoundaries.forValue(comparisonValue, log10Boundaries);
				var templateObject = this.getTemplateObject(valueLabel, layer, area, comparisonValue, value);

				layer.value.setStyle(hdv.layerStyle.forValue(comparisonValue, boundary, isSpending));
				layer.value.bindPopup(hdv.map.templates.popup(templateObject));
			} else {
				console.error('no layer for area ' + area.key);
			}
		},
		refreshLayers: function(settings) {
			var boundaries = hdv.accountBoundaries.findAccordingTo(settings);
			var log10Boundaries = hdv.accountBoundaries.toLog10(boundaries);
			var valueLabel = areaValue.getCurrentLabel();
			var isSpending = hdv.accounts.isSpending(settings.account);

			_.each(hdv.map.data.areas, _.bind(function(area) {
				this.refreshLayer(area, log10Boundaries, valueLabel, isSpending, settings);
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
