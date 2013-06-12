'use strict';

describe('area layer visualization', function() {
	it('should return inOut-value according to "compare" setting', function() {
		expect(hdv.areas.getValue([100, 10], 'in')).toEqual(100);
		expect(hdv.areas.getValue([100, 10], 'out')).toEqual(10);
		expect(hdv.areas.getValue([100, 10], 'sum')).toEqual(90);
	});

	it('should return inOut-sum of given account list', function() {
		var accountsInOut = {
			"611": [9269650, 4974823],
			"411": [null, 93863],
		};

		expect(hdv.areas.getInOutSum(accountsInOut, [611])).toEqual([9269650, 4974823]);
		expect(hdv.areas.getInOutSum(accountsInOut, [611, 411])).toEqual([9269650, 4974823 + 93863]);
		expect(hdv.areas.getInOutSum(accountsInOut, [620])).toEqual([0, 0]);
	});

	it('should return inOut in relation to a number', function() {
		expect(hdv.areas.getInOutInRelationTo([9269650, 4974823], 55.41)).toEqual([167292, 89782]);
		expect(hdv.areas.getInOutInRelationTo([9269650, 4974823], 8655)).toEqual([1071, 575]);
	});

	it('should find boundary for value', function() {
		expect(hdv.areas.getBoundary(100, [200, 10, -300, -20])).toEqual([200, 10]);
		expect(hdv.areas.getBoundary(-100, [200, 10, -300, -20])).toEqual([-300, -20]);
	});

	it('should find relevant boundaries', function() {
		var absolutBoundaries = [200, 10, -300, -20, 300, 30, -400, -20];
		var populationBoundaries = [20, 1, -30, -2, 30, 3, -40, -2];
		var areaBoundaries = [25, 2, -35, -3, 40, 4, -40, -3];
		var allBoundaries = [].concat(absolutBoundaries).concat(populationBoundaries).concat(areaBoundaries);

		expect(hdv.areas.getRelevantBoundaries(allBoundaries, 'none', 'sum')).toEqual([200, 10, -300, -20]);
		expect(hdv.areas.getRelevantBoundaries(allBoundaries, 'none', 'in')).toEqual([300, 30]);
		expect(hdv.areas.getRelevantBoundaries(allBoundaries, 'none', 'out')).toEqual([-400, -20]);
		expect(hdv.areas.getRelevantBoundaries(allBoundaries, 'population', 'sum')).toEqual([20, 1, -30, -2]);
	});

	it('should complate boundaries if necessary', function() {
		expect(hdv.areas.completeBoundaries([200, 10, -300, -20])).toEqual([200, 10, -300, -20]);
		expect(hdv.areas.completeBoundaries([200, 10])).toEqual([200, 10, 0, 0]);
		expect(hdv.areas.completeBoundaries([-200, -10])).toEqual([0, 0, -200, -10]);
	});
});
