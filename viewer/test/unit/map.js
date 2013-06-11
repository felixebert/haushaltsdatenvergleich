'use strict';

describe('map', function() {
	it('should calculate logarithmic opacity', function() {
		expect(hdv.map.getOpacity(90, 100)).toEqual(0.74);
		expect(hdv.map.getOpacity(-90, -100)).toEqual(0.74);
		expect(hdv.map.getOpacity(0, -100)).toEqual(0.25);
		expect(hdv.map.getOpacity(-75201, -18583298)).toEqual(0.5);
	});
});
