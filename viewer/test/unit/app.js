'use strict';

describe('app', function() {
	it('should extract defaults in search query', function() {
		expect(hdv.extractDefaultsInSearchQuery('')).toEqual({});
		expect(hdv.extractDefaultsInSearchQuery('?product=123')).toEqual({
			product: '123'
		});
		expect(hdv.extractDefaultsInSearchQuery('?product=123&account=456')).toEqual({
			product: '123',
			account: '456'
		});
	});
});