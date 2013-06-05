'use strict';

describe('remove one value of an array', function() {
	it('should remove the value on the same array reference', function() {
		var exampleArray = [1, 2, 3, 4, 5];
		var iterator = function(value) {
			return 4 === value;
		};
		hdv.array.remove(exampleArray, iterator);

		expect(exampleArray.length).toEqual(4);
	});
});
PROJCS["ETRS_1989_UTM_Zone_32N", GEOGCS["GCS_ETRS_1989", DATUM["D_ETRS_1989", SPHEROID["GRS_1980", 6378137.0, 298.257222101]], PRIMEM["Greenwich", 0.0], UNIT[
		"Degree", 0.0174532925199433]], PROJECTION["Transverse_Mercator"], PARAMETER["False_Easting", 500000.0], PARAMETER["False_Northing", 0.0], PARAMETER[
		"Central_Meridian", 9.0], PARAMETER["Scale_Factor", 0.9996], PARAMETER["Latitude_Of_Origin", 0.0], UNIT["Meter", 1.0]]
