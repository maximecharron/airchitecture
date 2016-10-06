homeApp.factory('homeResource', ["$resource", function ($resource) {
    return $resource("http://localhost:8081/api/search/flights", {}, {
        get: {
            method: 'GET',
            isArray: true
        }
    });
}]);

homeApp.factory('weightDetectionResource', ["$resource", function ($resource) {
    return $resource("http://localhost:8081/api/weightDetection", {}, {
        get: {
            method: 'GET'
        }
    });
}]);