homeApp.
    config(['$routeProvider', function ($routeProvider) {

        $routeProvider.when('/cart', {
            templateUrl: "components/cart/views/cart.html",
            controller: "cart-controller"

        });

        $routeProvider.when('/checkout', {
            templateUrl: "components/cart/views/checkoutinfo.html",
            controller: "cart-controller"

        });


        $routeProvider.when('/checkoutsummary', {
            templateUrl: "components/cart/views/checkoutsummary.html",
            controller: "cart-controller"

        });


    }]);
