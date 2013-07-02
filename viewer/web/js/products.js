'use strict';

(function(hdv, $, _) {
	var productSelectList = {
		init: function() {
			$(hdv).on('loaded.metadata', _.bind(this.reset, this));
		},
		reset: function() {
			var selectList = $('select[name="product"]');
			selectList.html(this.generateHtml(hdv.data.meta.tree, hdv.data.meta.productLabels));
			selectList.val(hdv.settings.product);
		},
		generateHtml: function(tree, labels) {
			var html = '';
			_.each(_.keys(tree), _.bind(function(groupKey) {
				html += '<optgroup label="' + labels[groupKey] + '">';
				html += this.generateOptions(tree[groupKey], labels);
				html += '</optgroup>';
			}, this));
			return html;
		},
		generateOptions: function(keys, labels) {
			var html = '';
			_.each(keys, function(key) {
				html += '<option value="' + key + '">' + labels[key] + '</option>';
			});
			return html;
		}
	};

	/**
	 * Je Produktgruppe (Account) sind 6 Grenzwerte unter dem Attribut "data"
	 * vorhanden. Diese Grenzwerte unterteilen sich in 3 Gruppen, die das
	 * Attribut "relation" (Im Verhältnis zu?) abdecken: absolut / Einwohnerzahl /
	 * Fläche (in dieser Reihenfolge).<br />
	 * Jede Gruppe besteht aus 2 Werten: max und min<br />
	 * Max und Min steht immer für den maximalen bzw. minimalen Wert, den es für
	 * diese Produktgruppe über alle Areas (Gemeinden / Landkreise) hinweg gibt.
	 */
	var accountBoundaries = {
		findAccordingTo: function(settings) {
			var allBoundaries = hdv.map.data.accounts[settings.account].data;
			var relevantBoundaries = this.findRelevant(allBoundaries, settings.relation, settings.compare);
			return relevantBoundaries;
		},
		findRelevant: function(allBoundaries, relation, compare) {
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
		forValue: function(value, boundaries) {
			if (boundaries.length == 2) {
				return boundaries;
			}
			return value > 0 ? [boundaries[0], boundaries[1]] : [boundaries[2], boundaries[3]];
		},
		toLog10: function(boundaries) {
			var log10Boundaries = [];
			_.each(boundaries, _.bind(function(boundaryValue) {
				log10Boundaries.push(hdv.calc.safeLog10(boundaryValue));
			}, this));
			return log10Boundaries;
		}
	};

	hdv.productSelectList = productSelectList;
	hdv.productSelectList.init();

	hdv.accounts = {};
	hdv.accountBoundaries = accountBoundaries;
})(hdv, $, _);