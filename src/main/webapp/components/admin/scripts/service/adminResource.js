adminApp.factory('adminResource', ["$resource", function ($resource) {
    return $resource("http://localhost:8081/api/airplanes/:path", {}, {
        get: {
            method: 'GET',
            params:{
                path:"search"
            }
        },
        put: {
            method: 'PUT',
            params:{
                path:"@serialNumber"
            }

        }
    });
}]);