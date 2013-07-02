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

describe('account boundaries utils', function() {
	it('should find relevant boundaries', function() {
		var allBoundaries = [200, 10, 300, 20, 300, 30];

		expect(hdv.accountBoundaries.findRelevant(allBoundaries, 'none')).toEqual([200, 10]);
		expect(hdv.accountBoundaries.findRelevant(allBoundaries, 'population')).toEqual([300, 20]);
		expect(hdv.accountBoundaries.findRelevant(allBoundaries, 'area')).toEqual([300, 30]);
	});

	it('should convert boundary values to their log10 result', function() {
		expect(hdv.accountBoundaries.toLog10([200, 10, -300, -20])).toEqual([2.301029995663981, 1, 2.477121254719662, 1.301029995663981]);
		expect(hdv.accountBoundaries.toLog10([200, 0])).toEqual([2.301029995663981, 0]);
	});
});