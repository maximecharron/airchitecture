homeApp.
    config(['$routeProvider', function ($routeProvider) {

        $routeProvider.when('/cart', {
            templateUrl: "components/cart/views/cart.html",
            controller: "cart-controller"

        });
    }]);
