homeApp.controller("home-controller", function ($scope, $rootScope, $http, $cookies, $window, homeResource, weightDetectionResource, userResource, ModalService) {

    $scope.isLoading = false;
    $scope.doNotShow = false;

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

    $http.get('./airports.json')
        .success(function(data) {
            $scope.airports=data;
        });

    $scope.detectWeight = function () {
        weightDetectionResource.get({}, function onSuccess(data) {
            $scope.formData.luggageWeight = data.weight;
        })
    };

    $scope.closeWeightFilteredAlert = function () {
        $scope.showWeightFilteredAlert = false;
        if ($rootScope.user && $scope.doNotShow){
            userResource.put({showWeightFilteredAlert: false}, function onSuccess(data) {
                $rootScope.user = data;
            });
        } else if ($scope.doNotShow){
            $window.localStorage.setItem("showWeightFilteredAlert", false);
        }
    };

    $scope.find = function () {
        $scope.isLoading = true;

        var searchCriteria = {};
        if ($scope.formData.from) {
            searchCriteria.from = $scope.formData.from.code;
        }
        if ($scope.formData.to) {
            searchCriteria.to = $scope.formData.to.code;
        }
        if ($scope.formData.date) {
            searchCriteria.datetime = new Date($scope.formData.date).toISOString().slice(0, 16);
        }
        if ($scope.formData.luggageWeight) {
            $scope.formData.luggageWeight = Number((Math.ceil($scope.formData.luggageWeight * 2) / 2).toFixed(1));
            searchCriteria.weight = $scope.formData.luggageWeight;
        }
        homeResource.get(searchCriteria, function onSuccess(data) {
            var flights = [];
            for (var index in data.flights) {
                var flight = data.flights[index];
                flight.id = flight.airlineCompany + flight.departureDate + flight.arrivalAirport;
                flight.humanArrivalAirport = $scope.formData.to.name;
                flight.humanDepartureAirport = $scope.formData.from.name;
                flight.name = flight.airlineCompany + " from " + flight.humanDepartureAirport + " to "+ flight.humanArrivalAirport;
                flights.push(flight);
            }
            if ($rootScope.user) {$scope.showWeightFilteredAlert = $rootScope.user.showsWeightFilteredAlert}
            else {$scope.showWeightFilteredAlert = $window.localStorage.getItem("showWeightFilteredAlert") || $scope.showWeightFilteredAlert === undefined;}

            $scope.flightsResults = flights;
            $scope.flightsWereFilteredByWeight = data.flightsWereFilteredByWeight;
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

        $scope.formData.date = null;

        ModalService.showModal({
            templateUrl: "/components/home/views/ReturnDateSelectionDlg.html",
            controller: "ModalFormController",
            inputs: {
                title: "Please enter a new departure date!"
            }
        }).then(function (modal) {
            modal.element.modal();
            modal.close.then(function (result) {
                console.log(result);
                $scope.formData.date = result.departureDate;
            });
        });
        
        $scope.find();
    };
});
