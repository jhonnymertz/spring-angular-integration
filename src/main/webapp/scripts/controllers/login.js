define(['./module'], function(controllers) {
	'use strict';
	controllers.controller('LoginCtrl', function($scope, $rootScope, $location, $http, $cookieStore, authenticationService, toaster) {
		
		$scope.authenticate = function() {
			authenticationService.authenticate($.param({username: $scope.username, password: $scope.password}), function success() {
				
				toaster.pop('success', 'Autenticação', 'Usuário autenticado com sucesso!');
				
				if($rootScope.redirectUrl != null && $rootScope.redirectUrl.indexOf('/login') == -1)
					$location.url($rootScope.redirectUrl);
				else 
					$location.path("/");
				
				$rootScope.redirectUrl = null;
            	$rootScope.redirectStatus = null;
			}, function err(data) {

			});
		};
	});
});