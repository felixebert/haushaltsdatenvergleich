'use strict';

describe('area value utils', function() {
	it('should return null-safe value of given product account values', function() {
		var values = {
			"611": {
				"100": 10,
				"200": 20
			},
			"612": {
				"100": 30,
				"200": 40
			}
		};

		expect(hdv.areaValue.findValue(values, 611, 200)).toEqual(20);
		expect(hdv.areaValue.findValue(values, 612, 100)).toEqual(30);
		expect(hdv.areaValue.findValue(values, 612, 900)).toEqual(0);
		expect(hdv.areaValue.findValue(values, 400, 200)).toEqual(0);
	});

	it('should return value in relation to a number', function() {
		expect(hdv.areaValue.getValueInRelationTo(9269650, 55.41)).toEqual(167292.01);
		expect(hdv.areaValue.getValueInRelationTo(4974823, 8655)).toEqual(574.79);
	});

	it('should generate a value label', function() {
		expect(hdv.areaValue.getLabel('Hundesteuer', null)).toEqual('Hundesteuer');
		expect(hdv.areaValue.getLabel('Hundesteuer', 'pro Einwohnerzahl')).toEqual('Hundesteuer pro Einwohnerzahl');
	});
});
