# Spring & AngularJS integration

This sample focus on localhost application which has angular and spring webservices running in the same project. Authentication is based on JSESSIONID and handled by Spring Security.

Aspects presented in project:

* A relational or NoSQL database to manage users with Spring data MongoDB or JPA;
* A REST service to expose users data;
* Authentication and authorization against the REST service.
* A set of AngularJS application pages to view or edit users depending on their role.

## Features

#### Spring Framework

* Application setup with servlet 3.0 specification and without XML configs;
* Logback set to debug operations;

#### Spring MVC resources

* Users data exposes through REST;
* Exposing HTML, CSS, Javascript and other non-secured resources, however each one can be blocked through a Spring Security rule;
* Errors and exceptions controlled via advices;

#### Spring Security resources

* Rules in order to filter requests to rest application and html pages;
* Custom authentication entry-point and request filter based on token;
* Token utils;

#### AngularJS Security resources

* Angular Routes;
* $rootScope and cookies are used to hold and save credentials;
* routeChange listener checking credentials;
* HTTP interceptor to catch errors form back-end;
* User operation methods in $rootScope:

        ng-show = "hasRole('ROLE_ADMINISTRATOR')" #enable or disable content based on roles

        ng-show = "isAuthenticated()" #check for authenticated user
        
        ng-click = "logout()" #erase any authetication record

### Project uses:

Spring projects:
- [Spring (MVC)](http://github.com/spring-projects/spring-framework) - 4.0.0.RELEASE
- [Spring Data JPA](http://github.com/spring-projects/spring-data-jpa) - 1.4.3.RELEASE
- [Spring Data MongoDB](http://github.com/spring-projects/spring-data-mongodb) - 1.3.3.RELEASE
- [Spring Security](http://github.com/spring-projects/spring-security) - 3.2.0.RELEASE

Other projects:
- [Hibernate](http://hibernate.org/)
- [Bower](https://github.com/bower/bower)
- [Gradle](https://github.com/gradle/gradle)
- [RequireJS](https://github.com/jrburke/requirejs)

JS libs:
- [AngularJS](https://github.com/angular/angular.js)
- [AngularJS-Toaster](https://github.com/jirikavi/AngularJS-Toaster)
- [ngTable Directive](http://bazalt-cms.com/ng-table/)

### Notes

* It's only an example/demonstration and does not aim to be used for production projects. Use it to learn about the technologies used.
* Despite there are many different technologies involved, this project focus on Spring Security and Angular integration;
