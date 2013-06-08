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

describe('convert a jquery serialized array to an object literal', function() {
	it('should add all elements in the array', function() {
		var exampleArray = [{
			name: 'year',
			value: '2010'
		}, {
			name: 'areaLayer',
			value: 'g'
		}];

		var result = hdv.serialize.toLiteral(exampleArray);
		expect(_.keys(result).length).toEqual(exampleArray.length);
		expect(result.year).toEqual('2010');
	});
});
