homeApp.factory('404HttpResponseInterceptor',['$q','$location','$rootScope', function($q, $location, $rootScope){
  return {
        response: function(response){
            if(response.status === 404){
              console.log("Response 404")
            }
            return response || $q.when(response);
        },
        responseError: function(rejection) {
            if(rejection.status === 404 && (rejection.config.url.indexOf('localhost') >=0 || rejection.config.url.indexOf('umovie-team01-client') >= 0)){
              console.log("Response error 404", rejection);
              $location.path('/lost');
            }
            return $q.reject(rejection);
        }
    }
}]).factory('400HttpResponseInterceptor',['$q','$location','$rootScope', function($q, $location, $rootScope){
    return {
        response: function(response){
            if(response.status === 400){
                console.log("Response 400")
            }
            return response || $q.when(response);
        },
        responseError: function(rejection) {
            if(rejection.status === 400 ){
                console.log("Response error 400", rejection);
                $location.path('/lost');
            }
            return $q.reject(rejection);
        }
    }
}])
.factory('500HttpResponseInterceptor',['$q','$location','$rootScope', function($q, $location, $rootScope){
  return {
    response: function(response){
        if(response.status === 500){
          console.log("Response 500")
        }
        return response || $q.when(response);
    },
    responseError: function(rejection) {
        if(rejection.status >= 500){
          console.log("Response error 500", rejection);
          $location.path('/problem');
        }
        return $q.reject(rejection);
    }
  }
}])
.config(['$httpProvider',function($httpProvider) {
        $httpProvider.interceptors.push('400HttpResponseInterceptor');
    $httpProvider.interceptors.push('404HttpResponseInterceptor');
    $httpProvider.interceptors.push('500HttpResponseInterceptor');
}]);
