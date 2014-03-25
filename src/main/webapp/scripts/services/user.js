define(['./module'], function(services) {
	'use strict';
	
	services.service('userData', function() {
		var users = null;
		return {
			setUsers: function(usersToSet) {
				users = usersToSet;
			},
			getUsers: function() {
				return users;
			},
			findById: function(id) {
				for (var i = 0; i < users.length; i++) {
					if (users[i].id == id) return users[i];
				}
				return null;
			}
		};
	});
	
	services.service('userService', function($resource) {
		return $resource(
		        "services/users/:Id",
		        {Id: "@Id" },
		        {
		        	"update": {method: "PUT"},		 
		        }
		    );
	});


	services.factory('authenticationService', function($resource) {
		
		return $resource('services/authenticate/:action', {},
				{
					authenticate: {
						method: 'POST',
						headers : {'Content-Type': 'application/x-www-form-urlencoded'}
					},
					user: {
						method: 'GET',
						params: {'action' : 'user'},
					},
					profile: {
						method: 'GET',
						params: {'action' : 'profile'},
					},
					logout: {
						method: 'GET',
						params: {'action' : 'logout'},
					},
				}
			);
	});
	
});