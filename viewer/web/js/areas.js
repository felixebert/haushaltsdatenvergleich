'use strict';

(function(hdv, $, _, Handlebars) {
	var nullSafeNumber = function(number) {
		return (number === null || number === undefined || parseInt(number, 10) === NaN) ? 0 : number;
	};

	var areaValue = {
		of: function(accountValues, account) {
			var value = 0;
			if (!_.isEmpty(accountValues)) {
				value = nullSafeNumber(accountValues[account]);
			}

			return value;
		},
		getCurrentLabel: function() {
			var account = $('.settings select[name="account"] option:selected').text();
			var relation = $('.settings input[name="relation"]:checked').data('label');
			return this.getLabel(account, relation);
		},
		inRelationTo: function(value, relationValue) {
			if (relationValue) {
				return this.getValueInRelationTo(value, relationValue);
			}
			return value;
		},
		getValueInRelationTo: function(value, relation) {
			return Math.round((value / relation) * 100) / 100;
		},
		getLabel: function(accountName, relationLabel) {
			return accountName + (relationLabel ? ' ' + relationLabel : '');
		}
	};

	var areas = {
		init: function() {
			$(hdv).on('loader.finished', _.bind(this.update, this));
		},
		getRelationValue: function(areaMeta, relationName) {
			return areaMeta[relationName];
		},
		getAreaMeta: function(areaKey) {
			return _.find(hdv.data.meta.areas, function(area) {
				return area.key == areaKey;
			});
		},
		getTemplateObject: function(valueLabel, layer, areaMeta, value, accountValue) {
			return {
				'valueLabel': valueLabel,
				'areaLabel': layer.label,
				'area': areaMeta,
				'value': value,
				'accountValue': accountValue,
				'keyForBalance': hdv.balance.getKeyForArea(areaMeta.key, layer.attribute)
			};
		},
		refreshLayer: function(areaKey, accountValues, log10Boundaries, valueLabel, isSpending, settings) {
			var layer = hdv.data.getAreaLayer(areaKey);
			if (layer) {
				var areaMeta = this.getAreaMeta(areaKey);
				var value = areaValue.of(accountValues, settings.account);
				var comparisonValue = areaValue.inRelationTo(value, this.getRelationValue(areaMeta, settings.relation));

				var templateObject = this.getTemplateObject(valueLabel, layer, areaMeta, comparisonValue, accountValues[settings.account]);

				layer.value.setStyle(hdv.layerStyle.forValue(comparisonValue, log10Boundaries, isSpending));
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

			_.each(_.keys(hdv.data.values.areas), _.bind(function(areaKey) {
				var accountValues = hdv.data.values.areas[areaKey];
				this.refreshLayer(areaKey, accountValues, log10Boundaries, valueLabel, isSpending, settings);
			}, this));
		},
		update: function() {
			this.refreshLayers(hdv.settings);
		}

	};

	hdv.areas = areas;
	hdv.areaValue = areaValue;
	areas.init();
})(hdv, $, _, Handlebars);
