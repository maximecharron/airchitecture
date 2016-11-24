homeApp.factory('sessionInjector',['$q', '$cookies', function($q, $cookies){
    var sessionInjector = {
        request: function(request) {
            if (request.url.indexOf("api") > -1 && request.url.indexOf("airplanes") > -1 && request.url.indexOf("search") == -1) {
                request.headers['X-Access-Token'] = $cookies.getObject("user").token;
            }
            return request;
        }
    };
    return sessionInjector;
}])
.config(['$httpProvider', function($httpProvider) {
    $httpProvider.interceptors.push('sessionInjector');
}]);
