homeApp.controller("home-controller", function ($scope, homeResource) {

    $scope.isLoading = false;

    $scope.formData = {
        from: "",
        to: "",
        date: ""
    };
    $scope.haveResults = false;
    $scope.flightsResults = [];
    
    $scope.hasError = false;
    $scope.error = undefined;

    $scope.find = function () {
        $scope.isLoading = true;

        var searchCriteria = {};
        if($scope.formData.from) {
            searchCriteria.from = $scope.formData.from;
        }
        if($scope.formData.to) {
            searchCriteria.to = $scope.formData.to;
        }
        if($scope.formData.date) {
            searchCriteria.datetime = new Date($scope.formData.date).toISOString().slice(0, 16);
        }
        
        homeResource.query(searchCriteria, function onSuccess(data) {
            $scope.flightsResults = data;
            $scope.isLoading = false;
            $scope.haveResults = true;
        }, function onError(data) {
            $scope.isLoading = false;
            $scope.error = data;
        });
    };
});
