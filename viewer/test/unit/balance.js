'use strict';

describe('balance utils', function() {
	it('should return an area key which can be used to load the balance of the area', function() {
		expect(hdv.balance.getKeyForArea('12345', 'Kreis')).toEqual('12345001');
		expect(hdv.balance.getKeyForArea('12345', 'Kreisfreie Stadt')).toEqual('12345000');
		expect(hdv.balance.getKeyForArea('12345678', null)).toEqual('12345678');
	});
});
