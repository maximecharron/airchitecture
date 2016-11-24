homeApp.controller("home-controller", function ($scope, $rootScope, $http, $cookies, $window, homeResource, weightDetectionResource, userResource, ModalService) {

    $scope.isLoading = false;
    $scope.doNotShow = false;

    $scope.formData = {
        from: "",
        to: "",
        date: "",
        luggageWeight: 0.0,
        onlyAirVivant: false,
        acceptsAirCargo: false
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
        if ($rootScope.user){
            userResource.put({showWeightFilteredAlert: false}, function onSuccess(data) {
                $rootScope.user = data;
                $cookies.putObject("user", $rootScope.user);
            });
        } else{
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
            $scope.formData.luggageWeight = Number((Math.ceil($scope.formData.luggageWeight * 2) / 2));
            searchCriteria.weight = $scope.formData.luggageWeight;
        }
        if ($scope.formData.onlyAirVivant) {
            searchCriteria.onlyAirVivant = $scope.formData.onlyAirVivant;
        }
        if ($scope.formData.acceptsAirCargo) {
            searchCriteria.acceptsAirCargo = $scope.formData.acceptsAirCargo;
        }
        homeResource.get(searchCriteria, function onSuccess(data) {
            var flights = [];
            for (var index in data.flights) {
                var flight = data.flights[index];
                flight.idEconomic = flight.airlineCompany + flight.departureDate + flight.arrivalAirport + "-Economic";
                flight.idBusiness = flight.airlineCompany + flight.departureDate + flight.arrivalAirport + "-Business";
                flight.idRegular = flight.airlineCompany + flight.departureDate + flight.arrivalAirport + "-Regular";
                flight.availableSeats = flight.availableSeatsDto.regularSeats + flight.availableSeatsDto.economicSeats + flight.availableSeatsDto.businessSeats;
                flight.humanArrivalAirport = $scope.formData.to.name;
                flight.humanDepartureAirport = $scope.formData.from.name;
                flight.luggageWeight = $scope.formData.luggageWeight;
                flight.nameEconomic = flight.airlineCompany + " from " + flight.humanDepartureAirport + " to "+ flight.humanArrivalAirport + " - Economic Seat";
                flight.nameBusiness = flight.airlineCompany + " from " + flight.humanDepartureAirport + " to "+ flight.humanArrivalAirport + " - Business Seat";
                flight.nameRegular = flight.airlineCompany + " from " + flight.humanDepartureAirport + " to "+ flight.humanArrivalAirport + " - Regular Seat";
                flights.push(flight);
            }
            if ($rootScope.user) {$scope.showWeightFilteredAlert = $rootScope.user.showsWeightFilteredAlert}
            else {$scope.showWeightFilteredAlert = $window.localStorage.getItem("showWeightFilteredAlert") === null}

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
                $scope.find();
            });
        });
    };

    $scope.today = function() {
        $scope.formData.date = new Date();
    };

    $scope.clear = function() {
        $scope.formData.date = null;
    };

    $scope.inlineOptions = {
        customClass: getDayClass,
        minDate: new Date(),
        showWeeks: true
    };

    $scope.dateOptions = {
        formatYear: 'yy',
        minDate: new Date(),
        startingDay: 1
    };

    $scope.toggleMin = function() {
        $scope.inlineOptions.minDate = $scope.inlineOptions.minDate ? null : new Date();
        $scope.dateOptions.minDate = $scope.inlineOptions.minDate;
    };

    $scope.toggleMin();

    $scope.open1 = function() {
        $scope.popup1.opened = true;
    };

    $scope.setDate = function(year, month, day) {
        $scope.formData.date = new Date(year, month, day);
    };

    $scope.formats = ['dd-MMMM-yyyy', 'yyyy/MM/dd', 'dd.MM.yyyy', 'shortDate'];
    $scope.format = $scope.formats[0];
    $scope.altInputFormats = ['M!/d!/yyyy'];

    $scope.popup1 = {
        opened: false
    };

    var tomorrow = new Date();
    tomorrow.setDate(tomorrow.getDate() + 1);
    var afterTomorrow = new Date();
    afterTomorrow.setDate(tomorrow.getDate() + 1);
    $scope.events = [
        {
            date: tomorrow,
            status: 'full'
        },
        {
            date: afterTomorrow,
            status: 'partially'
        }
    ];

    function getDayClass(data) {
        var date = data.date,
            mode = data.mode;
        if (mode === 'day') {
            var dayToCheck = new Date(date).setHours(0,0,0,0);

            for (var i = 0; i < $scope.events.length; i++) {
                var currentDay = new Date($scope.events[i].date).setHours(0,0,0,0);

                if (dayToCheck === currentDay) {
                    return $scope.events[i].status;
                }
            }
        }

        return '';
    }
});
