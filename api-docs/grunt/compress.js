/* eslint no-var: 0 */
var pkg = require('../package.json');
var path = require('path');

module.exports = function() {
  return {
    dist: {
      options: {
        archive: path.join('.', 'pkg', 'v' + pkg.version,
           pkg.name + '-' + pkg.version + '.tar.gz'),
        level: 9,
        pretty: true
      },
      files: [
        {
          expand: true,
          cwd: './dist',
          src: ['**'],
          dest: '.'
        }
      ]
    }
  };
};
