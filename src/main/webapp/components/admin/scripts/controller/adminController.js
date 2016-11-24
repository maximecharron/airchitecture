adminApp.controller("admin-controller", function ($scope, adminResource) {
    adminResource.get({}, function onSuccess(data) {
        $scope.airplanes = data.airplanes;
    });

    $scope.updateWeight = function(airplane) {
        adminResource.put({path: airplane.serialNumber}, {acceptedMaximumWeight: airplane.acceptedAdditionalWeight});
    }
});