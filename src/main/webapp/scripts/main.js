require.config({
	paths: {
		'angular': '../bower_components/angular/angular',
		'angular-route': '../bower_components/angular-route/angular-route',
		'angular-local-storage': '../bower_components/angular-local-storage/angular-local-storage',
		'angular-mocks': '../bower_components/angular-mocks/angular-mocks',
		'angular-cookies': '../bower_components/angular-cookies/angular-cookies',
		'angular-resource': '../bower_components/angular-resource/angular-resource',
		'angular-animate': '../bower_components/angular-animate/angular-animate',
		'domReady': '../bower_components/requirejs-domready/domReady',
		'jquery': '../bower_components/jquery/jquery.min',
		'twbootstrap': '../bower_components/bootstrap/dist/js/bootstrap',
		'angular-bootstrap' : '../bower_components/angular-bootstrap/ui-bootstrap-tpls',
	    'ng-table': '../bower_components/ng-table/ng-table',
	    'angular-toaster': '../bower_components/AngularJS-Toaster/toaster', 
	},
	shim: {
		'angular': {
			exports: 'angular'
		},
		'angular-route': {
			deps: ['angular']
		},
		'angular-local-storage': {
			deps: ['angular']
		},
		'angular-mocks': {
			deps: ['angular']
		},
		'angular-bootstrap': {
			deps: ['jquery','angular']
		},
		'angular-cookies':{
			deps: ['angular']
		},
		'angular-resource':{
			deps: ['angular']
		},
		'angular-animate':{
			deps: ['angular']
		},
		'angular-toaster':{
			deps: ['angular']
		},
		'ng-table':{
			deps: ['angular']
		},
		'jquery': {
			exports: 'jquery'
		},
		'twbootstrap': {
			deps: ['jquery']
		},
	},
	deps: ['./bootstrap']
});