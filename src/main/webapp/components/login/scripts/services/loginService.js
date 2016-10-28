loginApp.factory('loginService', ["loginResource", "$cookies", "$rootScope", '$window', 'ngCart', function (loginResource, $cookies, $rootScope, $window, ngCart) {

    function setUser(user) {
        $window.localStorage.removeItem("showWeightFilteredAlert");
        $rootScope.user = user;
        $cookies.putObject("user", $rootScope.user);
    }

    function getUser() {
        return $rootScope.user;
    }

    function clearUser() {
        $rootScope.user = null;
        $rootScope.showWeightFilteredAlert = undefined;
        $cookies.remove("showWeightFilteredAlert");
        $cookies.remove('user');
        ngCart.empty();
        $window.localStorage.clear();
    }

    return {
        SetUser: setUser,
        ClearUser: clearUser,
        getUser: getUser
    };
}]);
