'use strict';
var hdv = {};

(function(hdv, _) {
	hdv.array = {
		remove: function(array, valueToReject) {
			var indexToReject = _.indexOf(array, valueToReject);
			array.splice(indexToReject, 1);
		}
	};
})(hdv, _);