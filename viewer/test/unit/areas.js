'use strict';

describe('area value utils', function() {
	it('should return inOut-value according to "compare" setting', function() {
		expect(hdv.areaValue.getValue([100, 10], 'in')).toEqual(100);
		expect(hdv.areaValue.getValue([100, 10], 'out')).toEqual(10);
		expect(hdv.areaValue.getValue([100, 10], 'sum')).toEqual(90);
	});

	it('should return null-safe inOut of given account list', function() {
		var accountsInOut = {
			"611": [9269650, 4974823],
			"411": [null, 93863],
		};

		expect(hdv.areaValue.getInOut(accountsInOut, 611)).toEqual([9269650, 4974823]);
		expect(hdv.areaValue.getInOut(accountsInOut, 411)).toEqual([0, 93863]);
		expect(hdv.areaValue.getInOut(accountsInOut, 620)).toEqual([0, 0]);
	});

	it('should return inOut in relation to a number', function() {
		expect(hdv.areaValue.getInOutInRelationTo([9269650, 4974823], 55.41)).toEqual([167292.01, 89782.04]);
		expect(hdv.areaValue.getInOutInRelationTo([9269650, 4974823], 8655)).toEqual([1071.02, 574.79]);
	});
});

describe('area layer utils', function() {
	it('should calculate a logarithmic opacity factor', function() {
		expect(hdv.areas.getComparisonFactor(10, [2, 1])).toEqual(0);
		expect(hdv.areas.getComparisonFactor(40, [2, 1])).toEqual(0.6);
		expect(hdv.areas.getComparisonFactor(70, [2, 1])).toEqual(0.85);
		expect(hdv.areas.getComparisonFactor(100, [2, 1])).toEqual(1);
		expect(hdv.areas.getComparisonFactor(-10, [2, 1])).toEqual(0);
	});

	it('should handle equal max / min boundary', function() {
		expect(hdv.areas.getComparisonFactor(10, [1, 1])).toEqual(1);
	});

	it('should always find an useful opacity', function() {
		expect(hdv.areas.getOpacity(10, [2, 1])).toEqual(0.2);
		expect(hdv.areas.getOpacity(70, [2, 1])).toEqual(0.64);
		expect(hdv.areas.getOpacity(100, [2, 1])).toEqual(0.75);
	});

	it('should find a proper fillColor', function() {
		expect(hdv.areas.getFillColor(10, 'in', [0, 100])).toEqual('#00441b');
		expect(hdv.areas.getFillColor(10, 'out', [0, 100])).toEqual('#67000d');
		expect(hdv.areas.getFillColor(10, 'sum', [0, 100])).toEqual('#00441b');
		expect(hdv.areas.getFillColor(-10, 'sum', [0, 100])).toEqual('#67000d');
		expect(hdv.areas.getFillColor(0, 'in', [0, 100])).toEqual('#EEE');
	});

	it('should return a valid layerStyle', function() {
		expect(hdv.areas.getLayerStyle(10, [2, 1], 'sum')).toEqual({
			fillOpacity: 0.65,
			fillColor: '#e5f5e0'
		});
	});

	it('should return an area key which can be used to load the balance of the area', function() {
		expect(hdv.areas.getKeyForBalance('12345', 'Kreis')).toEqual('12345001');
		expect(hdv.areas.getKeyForBalance('12345', 'Kreisfreie Stadt')).toEqual('12345000');
		expect(hdv.areas.getKeyForBalance('12345678', null)).toEqual('12345678');
	});
});
