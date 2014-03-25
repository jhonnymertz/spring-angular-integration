define([
    'angular',
    'angular-route',
    'angular-local-storage',
    'angular-cookies',
    'angular-resource',
    'angular-mocks',
    'angular-bootstrap',
    'angular-animate',
    'angular-toaster',
    'ng-table',
    'jquery',
    'twbootstrap',
    './controllers/index',
    './directives/index',
    './services/index',
    './filters/index',
], function (ng) {
    'use strict';
    return ng.module('WebApp', [
        'WebApp.controllers',
        'WebApp.directives',
        'WebApp.services',
//        'WebApp.filters',
        'ngRoute',
        'ngCookies',
        'ngResource',
        'LocalStorageModule',
        'ui.bootstrap',
        'ngAnimate',
        'ngTable',
        'toaster',
    ])
    //user operations
    .run(function ($rootScope, $http, $location, authenticationService) {
    	
        $rootScope.hasRole = function (role) {
        	
            if ($rootScope.user === undefined || $rootScope.user.authorities === undefined) {
                return false;
            }
            
            for(var i = 0; i < $rootScope.user.authorities.length; i++){
            	if($rootScope.user.authorities[i].authority == role)
            		return true;
            }
            
            return false;
        };
        
        $rootScope.isAuthenticated = function () {
            return $rootScope.user !== undefined;
        };

        $rootScope.logout = function () {
        	authenticationService.logout({}, function success() {
        		delete $rootScope.user;
				$location.url("/login");
			}, function error() {
				
			});
        };
    })
    //monitoring route changes
    .run(function ($route, $rootScope, $http, $location, authenticationService) {

    	$rootScope.$on('$routeChangeStart', function (event, next, current) {
         	
         	if(!$rootScope.isAuthenticated()){
         		
             	var originalPath = $location.url();
             	
             	if(originalPath != '/login')
             		$rootScope.redirectUrl = angular.copy($location.url());
         		
 	        	authenticationService.user({}, function success(data) {
 	        		$rootScope.user = data;
 	        		
 	        		$location.url(originalPath);
 	    		}, function error(response) {
 	    			delete $rootScope.user;
 	    			$rootScope.logout();
 	    			$location.url("/login");
 	    		});
         	}

         });

    });

});