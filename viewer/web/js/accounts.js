'use strict';

(function(hdv, $, _) {
	var accounts = {
		selectedGroup: 'all',
		selectedAccount: 523,
		init: function() {
			$(hdv).on('map.loaded.data', _.bind(this.reset, this));
			$('.settings').on('change', _.bind(this.refresh, this));
		},
		reset: function() {
			this.resetAccountGroups();
			this.resetAccounts();
		},
		refresh: function() {
			if (this.selectedGroup !== $('select[name="pb"]').val()) {
				this.selectedGroup = $('select[name="pb"]').val();
				this.selectedAccount = 'all';
				this.resetAccounts();
			}
			this.selectedAccount = $('select[name="pg"]').val();
		},
		resetAccountGroups: function() {
			var selectList = $('select[name="pb"]');

			this.resetSelectList(selectList);
			_.each(_.keys(hdv.map.data.tree), _.bind(function(groupKey) {
				this.addOption(selectList, groupKey);
			}, this));

			selectList.val(this.selectedGroup);
		},
		resetAccounts: function() {
			var selectList = $('select[name="pg"]');

			this.resetSelectList(selectList);
			if (this.selectedGroup === 'all') {
				this.addAllAccounts(selectList);
			} else {
				this.addAccountsOfGroup(selectList, this.selectedGroup);
			}

			selectList.val(this.selectedAccount);
		},
		addAllAccounts: function(selectList) {
			_.each(_.keys(hdv.map.data.tree), _.bind(function(groupKey) {
				var groupAccount = hdv.map.data.accounts[groupKey];
				var optGroup = $('<optgroup />').attr('label', groupAccount.label);
				this.addAccountsOfGroup(optGroup, groupKey);
				selectList.append(optGroup);
			}, this));
		},
		addAccountsOfGroup: function(parentElement, groupKey) {
			_.each(hdv.map.data.tree[groupKey], _.bind(function(accountKey) {
				this.addOption(parentElement, accountKey);
			}, this));
		},
		resetSelectList: function(selectList) {
			selectList.empty();
			selectList.append($("<option />").val('all').text('Alle'));
		},
		addOption: function(parentElement, accountKey) {
			var account = hdv.map.data.accounts[accountKey];
			parentElement.append($("<option />").val(account.key).text(account.label));
		},
		getSelectedAccounts: function(selectedGroup, selectedAccount) {
			var accounts = [];
			if (selectedAccount !== 'all') {
				accounts.push(parseInt(selectedAccount, 10));
			} else {
				var groups = [];
				if (selectedGroup === 'all') {
					_.each(_.keys(hdv.map.data.tree), function(group) {
						groups.push(group);
					});
				} else {
					groups.push(selectedGroup);
				}

				_.each(groups, function(group) {
					_.each(hdv.map.data.tree[group], function(account) {
						accounts.push(account);
					});
				});
			}

			return accounts;
		},
		getTopAccount: function(selectedGroup, selectedAccount) {
			var account = selectedAccount === 'all' ? selectedGroup : selectedAccount;
			return parseInt(account, 10);
		}
	};

	hdv.accounts = accounts;
	accounts.init();
})(hdv, $, _);