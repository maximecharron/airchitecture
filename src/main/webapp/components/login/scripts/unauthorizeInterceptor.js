homeApp.factory('authHttpResponseInterceptor',['$q','$location', '$cookies','$rootScope', function($q, $location, $cookies, $rootScope){
  return {
        response: function(response){
            if (response.status === 401) {
                console.log("Response 401");
            }
            return response || $q.when(response);
        },
        responseError: function(rejection) {
            if (rejection.status === 401) {
                console.log("Response Error 401",rejection);
                $rootScope.user=undefined;
                $cookies.remove('user');
                $location.path('/login');
            }
            return $q.reject(rejection);
        }
    }
}])
.config(['$httpProvider',function($httpProvider) {
    $httpProvider.interceptors.push('authHttpResponseInterceptor');
}]);
