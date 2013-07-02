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

	var accountSelectList = {
		init: function() {
			$(hdv).on('loader.finished', _.bind(this.reset, this));
		},
		reset: function() {
			var selectList = $('select[name="account"]');
			var accounts = this.getAccountsWithValues(hdv.data.values.areas);
			if (_.indexOf(accounts, hdv.settings.account) < 0) {
				hdv.settingsService.resetAccount();
			}
			selectList.html(this.generateHtml(accounts, hdv.data.meta.incomeLabels, hdv.data.meta.spendingsLabels));
			selectList.val(hdv.settings.account);
		},
		getAccountsWithValues: function(areas) {
			var accounts = ['6', '7'];
			_.each(_.values(areas), function(areaAccounts) {
				accounts = accounts.concat(_.keys(areaAccounts));
			});
			return _.uniq(accounts);
		},
		generateHtml: function(accounts, incomeLabels, spendingsLabels) {
			var html = '';
			html += this.generateOptGroup(accounts, incomeLabels, 'Einnahmen');
			html += this.generateOptGroup(accounts, spendingsLabels, 'Ausgaben');
			return html;
		},
		generateOptGroup: function(accounts, labels, groupLabel) {
			var html = '<optgroup label="' + groupLabel + '">';
			_.each(_.keys(labels), function(accountId) {
				if (_.indexOf(accounts, accountId) >= 0) {
					html += '<option value="' + accountId + '">' + labels[accountId] + '</option>';
				}
			});
			html += '</optgroup>';
			return html;
		}
	};

	var accounts = {
		isSpending: function(account) {
			return this._isSpending(account, hdv.data.meta.incomeLabels, hdv.data.meta.spendingsLabels);
		},
		_isSpending: function(account, incomeLabels, spendingsLabels) {
			return _.indexOf(_.keys(incomeLabels), account) >= 0 ? false : true;
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
			var allBoundaries = hdv.data.values.minmax[hdv.settings.account];
			var relevantBoundaries = this.findRelevant(allBoundaries, hdv.settings.relation);
			return relevantBoundaries;
		},
		findRelevant: function(allBoundaries, relation) {
			if (allBoundaries === undefined) {
				return [0, 0];
			}

			var startPos = 0;
			var length = 2;
			if (relation !== 'none') {
				startPos = relation === 'population' ? 2 : 4;
			}

			var boundaries = [];
			for ( var i = startPos; i < startPos + length; i++) {
				boundaries.push(allBoundaries[i]);
			}
			return boundaries;
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

	hdv.accountSelectList = accountSelectList;
	hdv.accountSelectList.init();

	hdv.accounts = accounts;
	hdv.accountBoundaries = accountBoundaries;
})(hdv, $, _);