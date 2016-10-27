loginApp.factory('loginService', ["loginResource", "$cookies", "$rootScope", function (loginResource, $cookies, $rootScope) {

    function setUser(user) {
        $rootScope.user = user;
        $cookies.putObject("user", $rootScope.user);
    }

    function getUser() {
        return $rootScope.user;
    }

    function clearUser() {
        $rootScope.user = null;
        $rootScope.showWeightFilteredAlert = undefined;
        $cookies.remove('user');
    }

    return {
        SetUser: setUser,
        ClearUser: clearUser,
        getUser: getUser
    };
}]);
