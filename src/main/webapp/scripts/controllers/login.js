define(['./module'], function(controllers) {
	'use strict';
	controllers.controller('LoginCtrl', function($scope, $rootScope, $location, $http, $cookieStore, loginService, toaster) {
		
		$scope.authenticate = function() {
			loginService.authenticate($.param({username: $scope.username, password: $scope.password}), function success(user) {
				
				toaster.pop('success', 'Autenticação', 'Usuário autenticado com sucesso!');
				
				$rootScope.user = user;
				$http.defaults.headers.common['X-Auth-Token'] = user.token;
				$cookieStore.put('user', user);
				
				if($rootScope.redirectUrl != null && $rootScope.redirectUrl.indexOf('/login') == -1)
					$location.url($rootScope.redirectUrl);
				else 
					$location.path("/");
				
				$rootScope.redirectUrl = null;
            	$rootScope.redirectStatus = null;
			}, function err(data) {
				if($rootScope.redirectStatus == 401)
					toaster.pop('error', 'Autenticação', 'Usuário ou senha incorretos!');
			});
		};
	});
});