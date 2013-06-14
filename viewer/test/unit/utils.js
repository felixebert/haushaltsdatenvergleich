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

describe('formatter', function() {
	it('should format a money value', function() {
		expect('100.000.000').toEqual(hdv.formatter.currency(100000000));
		expect('100.000').toEqual(hdv.formatter.currency(100000));
		expect('100').toEqual(hdv.formatter.currency(100));
		expect('10').toEqual(hdv.formatter.currency(10));
		expect('1').toEqual(hdv.formatter.currency(1));
	});
});
