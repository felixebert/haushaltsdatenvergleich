'use strict';

describe('layer style utils', function() {
	it('should calculate a logarithmic comparison factor', function() {
		expect(hdv.layerStyle.getComparisonFactor(10, [2, 1])).toEqual(0);
		expect(hdv.layerStyle.getComparisonFactor(40, [2, 1])).toEqual(0.6);
		expect(hdv.layerStyle.getComparisonFactor(70, [2, 1])).toEqual(0.85);
		expect(hdv.layerStyle.getComparisonFactor(100, [2, 1])).toEqual(1);
		expect(hdv.layerStyle.getComparisonFactor(-10, [2, 1])).toEqual(0);
	});

	it('should handle equal max / min boundary', function() {
		expect(hdv.layerStyle.getComparisonFactor(10, [1, 1])).toEqual(1);
	});

	it('should find a proper fillColor', function() {
		expect(hdv.layerStyle.getFillColor(10, [0, 100], false)).toEqual('#00441b');
		expect(hdv.layerStyle.getFillColor(10, [0, 100], true)).toEqual('#67000d');
		expect(hdv.layerStyle.getFillColor(-10, [0, 100], false)).toEqual('#67000d');
		expect(hdv.layerStyle.getFillColor(0, [0, 100], false)).toEqual('#00441b');
		expect(hdv.layerStyle.getFillColor(0, [0, 100], true)).toEqual('#67000d');
	});

	it('should return a valid layerStyle', function() {
		expect(hdv.layerStyle.forValue(10, [2, 1], false)).toEqual({
			fillOpacity: 0.65,
			fillColor: '#F7FCF5'
		});
	});
});
