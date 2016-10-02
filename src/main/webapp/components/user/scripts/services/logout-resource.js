userApp.factory('logoutResource', ["$resource", function($resource){
    return $resource("https://localhost:8081/api/auth/logout" , {}, {
        logout:{
            method:'GET'
        }
    });
}]);
