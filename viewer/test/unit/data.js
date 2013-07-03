'use strict';

describe('dataService', function() {
	it('should find areaLayer by key', function() {
		var mockAreaLayer = {
			key: 123
		};
		hdv.data.areaLayers.push(mockAreaLayer);

		expect(hdv.data.getAreaLayer(123)).toEqual(mockAreaLayer);
		expect(hdv.data.getAreaLayer(456)).toEqual(null);
	});
});