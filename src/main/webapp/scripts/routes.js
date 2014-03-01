define(['./app'], function(app) {
	return app.config(['$routeProvider', '$locationProvider', '$httpProvider', function($routeProvider, $locationProvider, $httpProvider) {
		$routeProvider
	      .when('/', {
	        templateUrl: 'views/main.html',
	      })
	      .when('/users', {
	        templateUrl: 'views/users/users.html',
	        controller: 'UsersCtrl'
	      })
	      .when('/users/form', {
	        templateUrl: 'views/users/user-form.html',
	        controller: 'UserFormCtrl'
	      })
	      .when('/users/form/:id', {
	        templateUrl: 'views/users/user-edit.html',
	        controller: 'UserEditCtrl'
	      })
	      .when('/login', {
	        templateUrl: 'views/login.html',
	        controller: 'LoginCtrl'
	      })
	      .when('/400', {
	        templateUrl: 'views/errors/400.html'
	      })
	      .when('/403', {
	        templateUrl: 'views/errors/403.html'
	      })
	      .when('/404', {
	        templateUrl: 'views/errors/404.html'
	      })
	      .when('/500', {
	        templateUrl: 'views/errors/500.html'
	      })
	      .otherwise({
	        redirectTo: '/404'
	      });
		
		/* Intercept http errors */
		var interceptor = function ($rootScope, $q, $location, toaster) {

	        function success(response) {
	            return response;
	        };

	        function error(response) {
	        	
	            var status = response.status;
	            var config = response.config;
	            var method = config.method;
	            var url = config.url;

	            if (status == 401) {
	            	$rootScope.redirectUrl = $location.url();
	            	$rootScope.redirectStatus = 401;
	            	$rootScope.logout();
	            } else if (status == 403) {
	            	$location.url('/403');
	            } else if (status == 500) {
	            	$location.url('/500');
	            } else{
	            	//skip others
	            }
	            
	            return $q.reject(response);
	        };

	        return function (promise) {
	            return promise.then(success, error);
	        };
	    };
	    $httpProvider.responseInterceptors.push(interceptor);
		
	}]);
});