homeApp.
    config(['$routeProvider', function ($routeProvider) {

        $routeProvider.when('/problem', {
            templateUrl: "components/commons/views/problem.html",
            controller: "home-controller"
        }).when('/lost', {
            templateUrl: "components/commons/views/lost.html",
            controller: "home-controller"
        });
    }]);
