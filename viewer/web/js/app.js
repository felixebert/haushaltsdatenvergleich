'use strict';
var hdv = {};

(function(hdv, _) {
	hdv.array = {
		remove: function(array, valueToReject) {
			var indexToReject = _.indexOf(array, valueToReject);
			array.splice(indexToReject, 1);
		}
	};

	hdv.serialize = {
		toLiteral: function(array) {
			var literal = {};
			_.each(array, function(element) {
				literal[element.name] = element.value;
			});
			return literal;
		}
	};
})(hdv, _);