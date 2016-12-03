homeApp.factory('homeResource', ["$resource", function ($resource) {
    return $resource("http://localhost:8081/api/search/flights", {}, {
        put: {
            method: 'GET'
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

homeApp.factory('geolocationResource', ["$resource", function ($resource) {
    return $resource("http://localhost:8081/api/geolocation/:path", {}, {
        get: {
            method: 'GET',
            params:{
                path:"findNearestAirport"
            }
        }
    });
}]);

homeApp.factory('userResource',  ["$resource", "$rootScope", function ($resource, $rootScope) {
    return $resource("http://localhost:8081/api/users/me", {}, {
        put: {
            method: 'PUT',
            headers: {
                "X-Access-Token": $rootScope.user ? $rootScope.user.token : undefined
            }
        }
    });
}]);