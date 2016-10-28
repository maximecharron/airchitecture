cartApp.controller("cart-controller", function ($scope, $location, $rootScope, ngCart) {
    $scope.customer = { email : ""};

    $scope.redirect = function(){
        $location.path('/checkout')
    }

    $scope.isEmpty = function(){
        return !ngCart.getTotalItems();
    }

    $scope.$watch('customer["email"]', function(newCustomer, oldCustomer) {
        $rootScope.checkoutEmail = newCustomer;
    });

    $scope.redirectHome = function(){
        ngCart.empty();
        $location.path('/home')
    }
});
