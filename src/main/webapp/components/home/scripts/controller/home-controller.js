homeApp.controller("home-controller", function ($scope, homeResource, weightDetectionResource) {

    $scope.isLoading = false;

    $scope.formData = {
        from: "",
        to: "",
        date: "",
        luggageWeight: ""
    };
    $scope.haveResults = false;
    $scope.flightsResults = [];

    $scope.hasError = false;
    $scope.error = undefined;

    $scope.detectWeight = function () {
        weightDetectionResource.get({}, function onSuccess(data) {
            $scope.formData.luggageWeight = data.weight;
        })
    };

    $scope.find = function () {
        $scope.isLoading = true;

        var searchCriteria = {};
        if ($scope.formData.from) {
            searchCriteria.from = $scope.formData.from;
        }
        if ($scope.formData.to) {
            searchCriteria.to = $scope.formData.to;
        }
        if ($scope.formData.date) {
            searchCriteria.datetime = new Date($scope.formData.date).toISOString().slice(0, 16);
        }
        if($scope.formData.luggageWeight){
            $scope.formData.luggageWeight = Number((Math.ceil($scope.formData.luggageWeight * 2)/2).toFixed(1));
            searchCriteria.weight = $scope.formData.luggageWeight;
        }
        homeResource.get(searchCriteria, function onSuccess(data) {
            $scope.flightsResults = data;
            $scope.isLoading = false;
            $scope.haveResults = true;
        }, function onError(data) {
            $scope.isLoading = false;
            $scope.hasError = true;
            $scope.error = data;
        });
    };

    $scope.findReturnFlight = function () {
        var previousFrom = $scope.formData.from;

        $scope.formData.from = $scope.formData.to;
        $scope.formData.to = previousFrom;

        $scope.find();
    };
});
