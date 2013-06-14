'use strict';

describe('area value utils', function() {
	it('should return inOut-value according to "compare" setting', function() {
		expect(hdv.areaValue.getValue([100, 10], 'in')).toEqual(100);
		expect(hdv.areaValue.getValue([100, 10], 'out')).toEqual(10);
		expect(hdv.areaValue.getValue([100, 10], 'sum')).toEqual(90);
	});

	it('should return inOut-sum of given account list', function() {
		var accountsInOut = {
			"611": [9269650, 4974823],
			"411": [null, 93863],
		};

		expect(hdv.areaValue.getInOutSum(accountsInOut, [611])).toEqual([9269650, 4974823]);
		expect(hdv.areaValue.getInOutSum(accountsInOut, [611, 411])).toEqual([9269650, 4974823 + 93863]);
		expect(hdv.areaValue.getInOutSum(accountsInOut, [620])).toEqual([0, 0]);
	});

	it('should return inOut in relation to a number', function() {
		expect(hdv.areaValue.getInOutInRelationTo([9269650, 4974823], 55.41)).toEqual([167292, 89782]);
		expect(hdv.areaValue.getInOutInRelationTo([9269650, 4974823], 8655)).toEqual([1071, 575]);
	});
});
