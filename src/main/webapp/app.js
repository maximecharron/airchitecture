'use strict';

// Declare app level module which depends on views, and components
var homeApp = angular.module('airchitecture', [
    'ngRoute',
    'ngResource',
    'ngCookies',
    'airchitecture.user',
    "airchitecture.login",
    "validation.match",
    "ui.gravatar",
    "airchitecture.register",
    "airchitecture.cart",
    'ngCart',
    "ui.bootstrap",
    "angularModalService",
    "airchitecture.admin"
]).run(['$rootScope', '$cookies', '$location', function ($rootScope, $cookies, $location) {

    if ($cookies.getObject('user') != null) {
        $rootScope.user = $cookies.getObject('user');
    }

    $rootScope.$on("$routeChangeStart", function (event, next, current) {

        if ($cookies.getObject('user') != null && next.templateUrl == "components/login/views/login.html"){
            $location.path("/home");
        }

        if ($cookies.getObject('user') != null && !$cookies.getObject('user').isAdmin  && next.templateUrl == "components/admin/views/admin.html"){
            $location.path("/unauthorized");
        }
    });
}]);