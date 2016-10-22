homeApp.
    config(['$routeProvider', function ($routeProvider) {

        $routeProvider.when('/home', {
            templateUrl: "components/home/views/home.html",
            controller: "home-controller"
        });
    }]);
