registerApp.controller("register-controller", function ($scope, $location, registerResource) {
    $scope.notRegister = true;
    $scope.loading = false;
    $scope.error = false;
    $scope.emailAddress ="";
    $scope.password ="";

    $scope.register = function () {
        $scope.loading = true;
        $scope.error = false;
        var user = {
            "email": $scope.emailAddress,
            "password": $scope.password
        };
        registerResource.post(user, function onSuccess(data) {
            $scope.loading = false;
            $location.path("/login");

        }, function onError(data) {
            $scope.loading = false;
            $scope.error = true;
        });
    }
})
