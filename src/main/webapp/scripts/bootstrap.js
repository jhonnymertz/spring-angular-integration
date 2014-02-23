define([
     'require',
     'angular',
     'app',
     'routes'
 ], function (require, ng) {
     'use strict';
 
     require(['domReady!'], function (document) {
         ng.bootstrap(document, ['WebApp']);
     });
 });