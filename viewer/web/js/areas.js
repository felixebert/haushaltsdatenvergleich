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
		getLayerStyle: function(total, boundary) {
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
			return Math.round((this.getBaseLog(value) - this.getBaseLog(boundary[1])) / (this.getBaseLog(boundary[0]) - this.getBaseLog(boundary[1])) * 100) / 100;
		},
		getBaseLog: function(number) {
			return Math.log(Math.abs(number)) / Math.log(10);
		},
		getBoundaries: function(settings) {
			var allBoundaries = hdv.map.data.accounts[settings.boundaryAccount].data;
			var relevantBoundaries = this.getRelevantBoundaries(allBoundaries, settings.relation, settings.compare);
			return this.completeBoundaries(relevantBoundaries);
		},
		getRelevantBoundaries: function(allBoundaries, relation, compare) {
			var startPos = 0;
			var length = 2;
			if (relation !== 'none') {
				startPos = relation === 'population' ? 8 : 16;
			}
			if (compare === 'in') {
				startPos += 4;
			} else if (compare === 'out') {
				startPos += 6;
			} else {
				length = 4;
			}

			var boundaries = [];
			for ( var i = startPos; i < startPos + length; i++) {
				boundaries.push(allBoundaries[i]);
			}
			return boundaries;
		},
		getBoundary: function(value, boundaries) {
			return value > 0 ? [boundaries[0], boundaries[1]] : [boundaries[3], boundaries[2]];
		},
		completeBoundaries: function(boundaries) {
			if (boundaries.length === 4) {
				return boundaries;
			}

			var completeBoundaries;
			if (boundaries[0] < 0) {
				completeBoundaries = [0, 0, boundaries[0], boundaries[1]];
			} else {
				completeBoundaries = [boundaries[0], boundaries[1], 0, 0];
			}
			return completeBoundaries;
		},
		refreshLayers: function(settings) {
			var boundaries = this.getBoundaries(settings);

			_.each(hdv.map.data.areas, _.bind(function(area) {
				var areaLayer = hdv.map.getAreaLayer(area.key);
				var areaValue = this.getValueOfArea(area, settings);
				var boundary = this.getBoundary(areaValue, boundaries);

				var style = this.getLayerStyle(areaValue, boundary);
				var html = "<strong>" + areaLayer.label + "</strong><br />" + hdv.formatter.currency(areaValue) + " €";

				areaLayer.value.setStyle(style);
				areaLayer.value.bindPopup(html);
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