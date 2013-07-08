basePath = '../';

files = [
  JASMINE,
  JASMINE_ADAPTER,
  'src/lib/jquery.js',
  'src/lib/underscore.js',
  'src/lib/leaflet.js',
  'src/lib/handlebars.js',
  'src/js/app.js',
  'src/js/*.js',
  'test/unit/**/*.js'
];

autoWatch = true;

browsers = ['Chrome'];

junitReporter = {
  outputFile: 'test_out/unit.xml',
  suite: 'unit'
};
