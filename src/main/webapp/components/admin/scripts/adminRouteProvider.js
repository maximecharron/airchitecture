homeApp.
    config(['$routeProvider', function ($routeProvider) {

        $routeProvider.when('/admin', {
            templateUrl: "components/admin/views/admin.html"
        });
    }]);
