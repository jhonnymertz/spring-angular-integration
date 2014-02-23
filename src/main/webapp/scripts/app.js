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
        'WebApp.filters',
        'ngRoute',
        'ngCookies',
        'ngResource',
        'LocalStorageModule',
        'ui.bootstrap',
        'ngAnimate',
        'ngTable',
        'toaster',
    ])
    //operações globais de usuario
    .run(function ($rootScope, $http, $location, $cookieStore) {

        $rootScope.hasRole = function (role) {

            if ($rootScope.user === undefined) {
                return false;
            }

            if ($rootScope.user.authorities === undefined) {
                return false;
            }

            for (var i = 0; i < $rootScope.user.authorities.length; i++) {
                if ($rootScope.user.authorities[i].authority == role)
                    return true;
            }

            return false;
        };

        $rootScope.logout = function () {

            delete $rootScope.user;
            delete $http.defaults.headers.common['X-Auth-Token'];
            $cookieStore.remove('user');
            $location.url("/login");
        };

    })
    //monitoração de mudança de rotas e conteúdo
    .run(function ($route, $rootScope, $http, $location, $cookieStore, $anchorScroll) {

        $rootScope.$on('$routeChangeStart', function (event, next, current) {
        	var originalPath = $location.url();
        	
        	if(originalPath != '/login')
        		$rootScope.redirectUrl = angular.copy($location.url());

            //verifica se está logado, senão leva ao login
            $location.url("/login");
            var user = $cookieStore.get('user');
            if (user !== undefined) {
                $rootScope.user = user;
                $http.defaults.headers.common['X-Auth-Token'] = user.token;
                $location.url(originalPath);
            }

        });

        /* Reset error when a new view is loaded */
        $rootScope.$on('$viewContentLoaded', function () {
            delete $rootScope.error;
        });
        
        $rootScope.scrollTo = function (id, event) {
            var old = $location.hash();
            $location.hash(id);
            $anchorScroll();
            //reset to old to keep any additional routing logic from kicking in
            $location.hash(old);
        };

    });

});