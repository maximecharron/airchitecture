registerApp.controller("register-controller", function ($scope, $location, registerResource) {
    $scope.notRegister = true;

    $scope.emailAddress ="";
    $scope.password ="";

    $scope.register = function () {
        var user = {
            "email": $scope.emailAddress,
            "password": $scope.password
        };
        registerResource.post(user, function onSuccess(data) {

          $location.path("/login");

        }, function onError(data) {

            $location.path("/lost");

        });
    }
})
