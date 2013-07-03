'use strict';

(function(hdv, $, _, Handlebars) {
	var nullSafeNumber = function(number) {
		return (number === null || number === undefined || isNaN(parseInt(number, 10))) ? 0 : number;
	};

	var areaValue = {
		of: function(accountValues, account) {
			var value = 0;
			if (!_.isEmpty(accountValues)) {
				value = nullSafeNumber(accountValues[account]);
			}

			return value;
		},
		rawValueOf: function(accountValues, account) {
			var value = '-';
			if (!_.isEmpty(accountValues) && accountValues[account] !== undefined) {
				value = accountValues[account];
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
			$(hdv).on('accountSelectList.finished', _.bind(this.update, this));
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
		refreshLayer: function(areaLayer, accountValues, log10Boundaries, valueLabel, isSpending, settings) {
			var areaMeta = this.getAreaMeta(areaLayer.key);
			var rawValue = areaValue.rawValueOf(accountValues, settings.account);
			var value = areaValue.of(accountValues, settings.account);
			var comparisonValue = areaValue.inRelationTo(value, this.getRelationValue(areaMeta, settings.relation));
			var inexistent = isNaN(parseInt(rawValue, 10)) ? true : false;

			var templateObject = this.getTemplateObject(valueLabel, areaLayer, areaMeta, comparisonValue, rawValue);

			areaLayer.value.setStyle(hdv.layerStyle.forValue(comparisonValue, log10Boundaries, isSpending, inexistent));
			areaLayer.value.bindPopup(hdv.map.templates.popup(templateObject));
		},
		refreshLayers: function(settings) {
			var boundaries = hdv.accountBoundaries.findAccordingTo(settings);
			var log10Boundaries = hdv.accountBoundaries.toLog10(boundaries);
			var valueLabel = areaValue.getCurrentLabel();
			var isSpending = hdv.accounts.isSpending(settings.account);

			_.each(hdv.data.areaLayers, _.bind(function(areaLayer) {
				var accountValues = hdv.data.values.areas[areaLayer.key];
				this.refreshLayer(areaLayer, accountValues, log10Boundaries, valueLabel, isSpending, settings);
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
