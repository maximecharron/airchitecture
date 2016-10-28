cartApp.controller("cart-controller", function ($scope, $location, $rootScope, ngCart) {
    $scope.customer = { email : ""};

    $scope.redirect = function(){
        $location.path('/checkout')
    }

    $scope.isEmpty = function(){
        return !ngCart.getTotalItems();
    }

    $scope.$watch('customer["email"]', function(newCustomer, oldCustomer) {
        console.log("change " + newCustomer);
        $rootScope.checkoutEmail = newCustomer;
    });

    $scope.redirectHome = function(){
        ngCart.empty();
        $location.path('/home')
    }
});
