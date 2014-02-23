'use strict';

define(['./module'], function (controllers) {
    'use strict';
    controllers.controller('UsersCtrl', function ($scope, $http, $location, userData, 
    		userService, $filter, ngTableParams, tables, toaster) {

        $scope.selectedUsers = [];
        $scope.users = [];

        userService.query(function success(data) {
            userData.setUsers(data);
            $scope.users = userData.getUsers();

            // table sorting
            $scope.tableParams = tables.getUsersTable($scope.users);
        }, function err(data) {
			
		});

        $scope.toggleSelection = function (userLogin) {
            var index = $scope.selectedUsers.indexOf(userLogin);
            if (index > -1) {
                $scope.selectedUsers.splice(index, 1);
            } else {
                $scope.selectedUsers.push(userLogin);
            }
        }
        $scope.deleteUsers = function () {
            if (window.confirm("Deseja mesmo remover os usuários selecionados?")) {
                for (var i = 0; i < $scope.selectedUsers.length; i++) {
                    for (var j = 0; j < $scope.users.length; j++) {
                        if ($scope.selectedUsers[i] == $scope.users[j].username) {
                        	userService.delete({}, {'Id': $scope.users[j].id}, ((function success(user) {
                            	return function(){
                            		var index = $scope.users.indexOf(user);
	                                $scope.users.splice(index, 1);
	                                $scope.tableParams.reload();
	                                toaster.pop('success', 'Usuários', 'Remoção realizada com sucesso!');
                            	}
                            })($scope.users[j])), function err(data) {
                        		
                        		toaster.pop('error', 'Usuários', 'Não foi possível realizar a remoção!');
                            });
                        }
                    }
                }
            }
        }
    });

    controllers.controller('UserFormCtrl', function ($scope, $http, $location, $routeParams, userData, userService, toaster) {

        $scope.user = {
            id: '',
            username: '',
            name: '',
            password: '',
            passwordConfirm: '',
            profile: 'OPERATOR',
            enabled: true
        };

        $scope.saveUser = function () {
            $scope.userForm.username.$setValidity('unique', true);
            if ($scope.userForm.$valid) {
                var user = angular.copy($scope.user);

                userService.save({}, user, function success(data) {
                	toaster.pop('success', 'Usuários', 'Usuário salvo com sucesso!');
                    $location.path("/users");
                }, function error(data) {
                	//TODO refatorar mensagens de erro e definir padrões
                    if (data.data.indexOf("Username already in use") != -1) {
                    	toaster.pop('error', 'Usuários', 'Nome de usuário já existe!');
                    	
                    } else if (data.data.indexOf("Passwords don't match") != -1) {
                    	toaster.pop('error', 'Usuários', 'Senhas não conferem!');
                    } else {
                    	toaster.pop('error', 'Usuários', 'Erro ao salvar informações!');
                    }
                });
            }
        };
    });

    controllers.controller('UserEditCtrl', function ($scope, $http, $location, $routeParams, userData, userService, toaster) {

        if ($routeParams.id !== 'undefined') {
            if (userData.getUsers() == null) {
                userService.get({}, {'Id': $routeParams.id}, function success(data) {
                    if (!data) {
                        toaster.pop('error', 'Usuários', 'Usuário inválido');
                        $location.path("/");
                    }
                    $scope.user = data;
                    $scope.user.password = '';
                    $scope.user.passwordConfirm = '';
                    $scope.user.passwordOld = '';
                    $scope.isEdit = true;
                });
            } else {
                $scope.user = userData.findById($routeParams.id);
                if ($scope.user == null) {
                    window.alert('Usuário inválido');
                    $location.path("/users");
                }
                $scope.user.password = '';
                $scope.user.passwordConfirm = '';
                $scope.user.passwordOld = '';
            }
        }

        $scope.editPassword = true;
        $scope.changePassword = function () {
            $scope.editPassword = !$scope.editPassword;
            $scope.user.password = $scope.user.passwordConfirm = $scope.user.passwordOld = '';
        }

        $scope.saveUser = function () {
            $scope.userForm.username.$setValidity('unique', true);
            if ($scope.userForm.$valid) {
                var user = angular.copy($scope.user);
                user.passwordChange = {
                    value: user.password,
                    confirm: user.passwordConfirm,
                    older: user.passwordOld
                };
                delete user['authorities'];
                delete user['password'];
                delete user['passwordConfirm'];
                delete user['passwordOld'];

                userService.save({}, user, function success(data) {
                	toaster.pop('success', 'Usuários', 'Informações salvas com sucesso!');
                	
                }, function error(data) {
                	//TODO refatorar mensagens de erro e definir padrões
                    if (data.data.indexOf("Passwords don't match") != -1) {
                    	toaster.pop('error', 'Usuários', 'Senhas não conferem!');
                    } else if (data.data.indexOf("A senha antiga informada é inválida") != -1) {
                    	toaster.pop('error', 'Usuários', 'Senha atual incorreta!');
                    } else {
                    	toaster.pop('error', 'Usuários', 'Erro ao atualizar informações!');
                    }
                });
            }
        };
    });

});