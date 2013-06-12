'use strict';

var mockData = {
	"accounts": {
		"111": {
			"key": 111,
			"label": "Verwaltungssteuerung und Service",
		},
		"126": {
			"key": 126,
			"label": "Rettungsdienst",
		},
		"127": {
			"key": 127,
			"label": "Rettungsdienst",
		},
		"128": {
			"key": 128,
			"label": "Rettungsdienst",
		},
		"122": {
			"key": 122,
			"label": "Rettungsdienst",
		},
		"121": {
			"key": 121,
			"label": "Rettungsdienst",
		}
	},
	"tree": {
		"11": [111],
		"12": [126, 128, 122, 127, 121]
	}
};

describe('accounts', function() {
	it('should find all related accounts to current user selection', function() {
		hdv.map.data = mockData;

		expect(hdv.accounts.getSelectedAccounts('all', '111')).toEqual([111]);
		expect(hdv.accounts.getSelectedAccounts('11', 'all')).toEqual([111]);
		expect(hdv.accounts.getSelectedAccounts('12', 'all').length).toEqual(mockData.tree[12].length);
		expect(hdv.accounts.getSelectedAccounts('all', 'all').length).toEqual(_.keys(mockData.accounts).length);
	});

	it('should find the top account (by level)', function() {
		hdv.map.data = mockData;

		expect(hdv.accounts.getTopAccount('all', '111')).toEqual(111);
		expect(hdv.accounts.getTopAccount('11', 'all')).toEqual(11);
		expect(hdv.accounts.getTopAccount('11', '111')).toEqual(111);
	});
});
