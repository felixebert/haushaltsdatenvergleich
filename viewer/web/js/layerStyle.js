'use strict';
(function(hdv) {
	var layerStyle = {
		colorSchemes: {
			red: ["#fee0d2", "#fcbba1", "#fc9272", "#fb6a4a", "#ef3b2c", "#cb181d", "#a50f15", "#67000d"],
			green: ["#e5f5e0", "#c7e9c0", "#a1d99b", "#74c476", "#41ab5d", "#238b45", "#006d2c", "#00441b"]
		},
		/**
		 * @param value
		 *            value of the layer
		 * @param log10Boundary
		 *            boundary array [max / min]
		 * @param hasNegativeMeaning
		 */
		forValue: function(value, log10Boundary, hasNegativeMeaning) {
			var result = {
				'fillOpacity': 0.65,
				'fillColor': this.getFillColor(value, log10Boundary, hasNegativeMeaning)
			};
			if (result.fillColor === NaN || result.fillColor === undefined) {
				console.log(hdv.calc.safeLog10(value));
				console.log(log10Boundary);
			}
			return result;
		},
		getFillColor: function(value, log10Boundary, hasNegativeMeaning) {
			if (value == 0 || value == '.') {
				return '#EEE';
			}

			var colorScheme = (value <= 0 || hasNegativeMeaning) ? this.colorSchemes.red : this.colorSchemes.green;
			var factor = this.getComparisonFactor(value, log10Boundary);
			var colorIndex = Math.max(0, Math.round((colorScheme.length - 1) * factor));
			return colorScheme[colorIndex];
		},
		getComparisonFactor: function(value, log10Boundary) {
			if (log10Boundary[0] === log10Boundary[1]) {
				return 1;
			}
			return Math.round((hdv.calc.safeLog10(value) - log10Boundary[1]) / (log10Boundary[0] - log10Boundary[1]) * 100) / 100;
		}
	};

	hdv.layerStyle = layerStyle;
})(hdv);
