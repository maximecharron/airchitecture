homeApp.factory('homeResource', ["$resource", function ($resource) {
    return $resource("http://localhost:8081/api/search/flights", {}, {
        get: {
            methode: 'GET',
            isArray: true
        }
    });
}]);