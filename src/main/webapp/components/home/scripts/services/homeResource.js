homeApp.factory('homeResource', ["$resource", function ($resource) {
    return $resource("http://localhost:8081/api/whatever", {}, {

    });
}]);