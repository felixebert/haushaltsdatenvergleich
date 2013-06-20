basePath = '../';

files = [
  JASMINE,
  JASMINE_ADAPTER,
  'web/lib/jquery.js',
  'web/lib/underscore.js',
  'web/lib/leaflet.js',
  'web/lib/handlebars.js',
  'web/js/app.js',
  'web/js/*.js',
  'test/unit/**/*.js'
];

autoWatch = true;

browsers = ['Chrome'];

junitReporter = {
  outputFile: 'test_out/unit.xml',
  suite: 'unit'
};
