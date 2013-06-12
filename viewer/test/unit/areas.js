'use strict';

describe('area layer visualization', function() {
	it('should calculate logarithmic opacity', function() {
		expect(hdv.areas.getOpacity(90, 100)).toEqual(0.74);
		expect(hdv.areas.getOpacity(-90, -100)).toEqual(0.74);
		expect(hdv.areas.getOpacity(0, -100)).toEqual(0.25);
		expect(hdv.areas.getOpacity(-75201, -18583298)).toEqual(0.5);
	});
});
