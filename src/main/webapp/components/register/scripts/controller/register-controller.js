registerApp.controller("register-controller", function ($scope, $location, registerResource) {
    $scope.notRegister = true;

    $scope.email ="";
    $scope.password ="";

    $scope.register = function () {
        var user = {
            "email": $scope.email,
            "password": $scope.password
        };
        registerResource.post(user, function onSuccess(data) {

          $location.path("/login");

        }, function onError(data) {

            $location.path("/lost");

        });
    }
})
