loginApp.controller("login-controller", function ($scope, $rootScope, loginService, $location, loginResource) {

    $scope.email = "";
    $scope.password = "";

    $scope.login = function () {
        $scope.isLoading = true;
        $scope.loginError = false;

        var credentials = {
            "email": $scope.email,
            "password": $scope.password
        };

        loginResource.post(credentials, function onSuccess(data) {
            
            loginService.SetUser(data);
            if ($rootScope.user.isAdmin === true){
                $location.path("/admin");
            } else {
                $location.path("/home");
            }
            $scope.isLoading = false;

        }, function onError(data) {

            $scope.isLoading = false;
            $scope.loginError = true;

        });
    }
});
