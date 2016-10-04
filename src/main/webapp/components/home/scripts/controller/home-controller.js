homeApp.controller("home-controller", function ($scope, homeResource) {

    $scope.isLoading = false;

    $scope.formData = {
        from: "",
        to: "",
        date: ""
    };
    $scope.haveResults = false;
    $scope.flightsResults = [];

    $scope.find = function () {
        console.log($scope.formData);
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

        console.dir(searchCriteria);
        homeResource.query(searchCriteria, function onSuccess(data) {
            console.log(data);

            $scope.flightsResults = [];
            data.forEach(function(d) {
                $scope.flightsResults.push(d);
            });
            $scope.isLoading = false;
            $scope.haveResults = true;
        }, function onError(data) {
            console.log("erreur");
            console.dir(data);
        });
    };

    $scope.click = function(flight) {
        console.log(flight);
    };
});
