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
	it('should find boundary for value', function() {
		expect(hdv.accountBoundaries.forValue(100, [200, 10, -300, -20])).toEqual([200, 10]);
		expect(hdv.accountBoundaries.forValue(-100, [200, 10, -300, -20])).toEqual([-300, -20]);
		expect(hdv.accountBoundaries.forValue(100, [200, 10])).toEqual([200, 10]);
		expect(hdv.accountBoundaries.forValue(-100, [200, 10])).toEqual([200, 10]);
	});

	it('should find relevant boundaries', function() {
		var absolutBoundaries = [200, 10, -300, -20, 300, 30, -400, -20];
		var populationBoundaries = [20, 1, -30, -2, 30, 3, -40, -2];
		var areaBoundaries = [25, 2, -35, -3, 40, 4, -40, -3];
		var allBoundaries = [].concat(absolutBoundaries).concat(populationBoundaries).concat(areaBoundaries);

		expect(hdv.accountBoundaries.findRelevant(allBoundaries, 'none', 'sum')).toEqual([200, 10, -300, -20]);
		expect(hdv.accountBoundaries.findRelevant(allBoundaries, 'none', 'in')).toEqual([300, 30]);
		expect(hdv.accountBoundaries.findRelevant(allBoundaries, 'none', 'out')).toEqual([-400, -20]);
		expect(hdv.accountBoundaries.findRelevant(allBoundaries, 'population', 'sum')).toEqual([20, 1, -30, -2]);
	});

	it('should convert boundary values to their log10 result', function() {
		expect(hdv.accountBoundaries.toLog10([200, 10, -300, -20])).toEqual([2.301029995663981, 1, 2.477121254719662, 1.301029995663981]);
		expect(hdv.accountBoundaries.toLog10([200, 0])).toEqual([2.301029995663981, 0]);
	});

	it('should find boundaries according to settings', function() {
		hdv.map.data = {
			accounts: {
				255: {
					data: [200, 10, -300, -20, 300, 30, -400, -20]
				}
			}
		};
		var settings = {
			account: 255,
			relation: 'none',
			compare: 'sum'
		};

		expect(hdv.accountBoundaries.findAccordingTo(settings)).toEqual([200, 10, -300, -20]);
	});
});