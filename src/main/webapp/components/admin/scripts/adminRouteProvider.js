homeApp.
    config(['$routeProvider', function ($routeProvider) {

        $routeProvider.when('/admin', {
            templateUrl: "components/admin/views/admin.html"
        }).when('/admin/airplanes', {
            templateUrl: "components/admin/views/airplanes.html",
            controller: "admin-controller"
        });
    }]);
