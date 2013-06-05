basePath = '../';

files = [
  JASMINE,
  JASMINE_ADAPTER,
  'web/lib/angular/angular.js',
  'web/lib/angular/angular-*.js',
  'web/lib/underscore.js',
  'test/lib/angular/angular-mocks.js',
  'web/js/app.js',
  'web/js/utils.js',
  'web/js/map.js',
  'test/unit/**/*.js'
];

autoWatch = true;

browsers = ['Chrome'];

junitReporter = {
  outputFile: 'test_out/unit.xml',
  suite: 'unit'
};
