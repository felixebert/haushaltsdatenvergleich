'use strict';

describe('settingsService', function() {
	it('should merge defaults into settings (if nothing is set)', function() {
		var defaults = {
			product: 123,
			account: 456
		};

		expect(hdv.settingsService._mergeSettings({}, defaults)).toEqual(defaults);
		expect(hdv.settingsService._mergeSettings({
			product: 987
		}, defaults)).toEqual({
			product: 987,
			account: 456
		});
	});

	it('should prefer default property if current setting is none', function() {
		expect(hdv.settingsService._mergeProperty(null, '123')).toEqual('123');
		expect(hdv.settingsService._mergeProperty('none', '123')).toEqual('123');
		expect(hdv.settingsService._mergeProperty('456', '123')).toEqual('456');
	});
});