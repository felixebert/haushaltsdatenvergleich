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

	it('should generate a value label', function() {
		expect(hdv.areaValue.getLabel('Hundesteuer', null)).toEqual('Hundesteuer');
		expect(hdv.areaValue.getLabel('Hundesteuer', 'pro Einwohnerzahl')).toEqual('Hundesteuer pro Einwohnerzahl');
	});
});
