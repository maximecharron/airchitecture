homeApp.factory('homeResource', ["$resource", "$rootScope", function ($resource, $rootScope) {
    console.log($rootScope.user ? $rootScope.user.token : undefined);
    return $resource("http://localhost:8081/api/search/flights", {}, {
        get: {
            method: 'GET',
            headers: {
                "X-Access-Token": $rootScope.user ? $rootScope.user.token : undefined
            }
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

homeApp.factory('userSearchPreferencesResource',  ["$resource", "$rootScope", function ($resource, $rootScope) {
    return $resource("http://localhost:8081/api/users/me/searchPreferences", {}, {
        get: {
            method: 'GET',
            headers: {
                "X-Access-Token": $rootScope.user ? $rootScope.user.token : undefined
            }
        }
    });
}]);