cartApp.factory('cartResource', ["$resource", function ($resource) {
    return $resource("http://localhost:8081/api/cartItems", {}, {
        get: {
            method: 'GET',
            isArray: true
        },
        reserveTicket :{
            method: 'POST'
        }
    });
}]);