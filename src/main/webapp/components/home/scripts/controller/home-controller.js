homeApp.controller("home-controller", function ($scope, $rootScope, $http, $cookies, $window, homeResource, weightDetectionResource, userResource, userSearchPreferencesResource, geolocationResource, ModalService) {

    $scope.isLoading = false;
    $scope.doNotShow = false;

    $scope.formData = {
        from: "",
        to: "",
        date: "",
        luggageWeight: 0.0,
        onlyAirVivant: false,
        acceptsAirCargo: false,
        economic: false,
        regular: false,
        business: false
    };
    $scope.haveResults = false;
    $scope.haveMyResults = false;
    $scope.flightsResults = [];
    $scope.myFlightResults = [];

    $scope.hasError = false;
    $scope.error = undefined;

    if ($rootScope.user) {
        $scope.isLoading = true;
        userSearchPreferencesResource.get({}, function onSuccess(data) {
            $scope.formData.onlyAirVivant = data.hasMostlySearchedForAirVivantFlights;
            $scope.formData.economic = data.hasMostlySearchedForEconomyClassFlights;
            $scope.formData.regular = data.hasMostlySearchedForRegularClassFlights;
            $scope.formData.business = data.hasMostlySearchedForBusinessClassFlights;
            $scope.isLoading = false;
        });
    }


    $scope.detectWeight = function () {
        weightDetectionResource.get({}, function onSuccess(data) {
            $scope.formData.luggageWeight = data.weight;
        })
    };
    $scope.findNearestAirport = function () {
        geolocationResource.get({}, function onSuccess(data) {
            $scope.airports.forEach(function (airport, index) {
                if (airport.code == data.nearestAirport) {
                    $scope.formData.from = airport;
                }
            });
        });
    }

    $scope.closeWeightFilteredAlert = function () {
        $scope.showWeightFilteredAlert = false;
        if ($rootScope.user) {
            userResource.put({showWeightFilteredAlert: false}, function onSuccess(data) {
                $rootScope.user = data;
                $cookies.putObject("user", $rootScope.user);
            });
        } else {
            $window.localStorage.setItem("showWeightFilteredAlert", false);
        }
    };

    $scope.findMyFlight = function () {

        if ($rootScope.user && $rootScope.user.preferredDestinations) {
            $scope.isLoading = true;
            if ($rootScope.user.preferredDestinations.entry.length > 0) {
                geolocationResource.get({}, function onSuccess(data) {
                    $scope.airports.forEach(function (airport, index) {
                        if (airport.code == data.nearestAirport) {
                            $scope.formData.from = airport;
                        }
                    });

                    $rootScope.user.preferredDestinations.entry.forEach(function (destination, index) {
                        var searchCriteria = {};
                        searchCriteria.to = destination.key;
                        searchCriteria.weight = 1;
                        if ($scope.formData.from) {
                            searchCriteria.from = 'YUL';
                        }

                        homeResource.get(searchCriteria, function onSuccess(data) {
                            var flights = [];
                            data.flights.forEach(function(flight, index) {
                                var flight = data.flights[index];
                                flight.idEconomic = flight.airlineCompany + flight.departureDate + flight.arrivalAirport + "-Economic";
                                flight.idBusiness = flight.airlineCompany + flight.departureDate + flight.arrivalAirport + "-Business";
                                flight.idRegular = flight.airlineCompany + flight.departureDate + flight.arrivalAirport + "-Regular";
                                flight.availableSeats = flight.availableSeatsDto.regularSeats + flight.availableSeatsDto.economicSeats + flight.availableSeatsDto.businessSeats;
                                flight.humanArrivalAirport = $scope.formData.to.name;
                                flight.humanDepartureAirport = $scope.formData.from.name;
                                flight.luggageWeight = $scope.formData.luggageWeight;
                                flight.nameEconomic = flight.airlineCompany + " from " + flight.humanDepartureAirport + " to " + flight.humanArrivalAirport + " - Economic Seat";
                                flight.nameBusiness = flight.airlineCompany + " from " + flight.humanDepartureAirport + " to " + flight.humanArrivalAirport + " - Business Seat";
                                flight.nameRegular = flight.airlineCompany + " from " + flight.humanDepartureAirport + " to " + flight.humanArrivalAirport + " - Regular Seat";
                                $scope.myFlightResults.push(flight);
                            })

                            $scope.flightsWereFilteredByWeight = data.flightsWereFilteredByWeight;
                            $scope.haveMyResults = true;
                            $scope.isLoading = false;

                        }, function onError(data) {
                            $scope.isLoading = false;
                            $scope.hasError = true;
                            $scope.error = data;
                        });
                    })
                    if ($rootScope.user.preferredDestinations.entry.length == 0) {
                        $scope.isLoading = false;
                    }
                });
            } else {
                $scope.isLoading = false;
            }
        }
    }

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
        if ($scope.formData.economic) {
            searchCriteria.hasEconomySeats = $scope.formData.economic;
        }
        if ($scope.formData.regular) {
            searchCriteria.hasRegularSeats = $scope.formData.regular;
        }
        if ($scope.formData.business) {
            searchCriteria.hasBusinessSeats = $scope.formData.business;
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
                flight.nameEconomic = flight.airlineCompany + " from " + flight.humanDepartureAirport + " to " + flight.humanArrivalAirport + " - Economic Seat";
                flight.nameBusiness = flight.airlineCompany + " from " + flight.humanDepartureAirport + " to " + flight.humanArrivalAirport + " - Business Seat";
                flight.nameRegular = flight.airlineCompany + " from " + flight.humanDepartureAirport + " to " + flight.humanArrivalAirport + " - Regular Seat";
                flights.push(flight);
            }
            if ($rootScope.user) {
                $scope.showWeightFilteredAlert = $rootScope.user.showsWeightFilteredAlert
            }
            else {
                $scope.showWeightFilteredAlert = $window.localStorage.getItem("showWeightFilteredAlert") === null
                console.log($scope.showWeightFilteredAlert);
            }

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
                $scope.formData.date = result.departureDate;
                $scope.find();
            });
        });
    };

    $scope.today = function () {
        $scope.formData.date = new Date();
    };

    $scope.clear = function () {
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

    $scope.toggleMin = function () {
        $scope.inlineOptions.minDate = $scope.inlineOptions.minDate ? null : new Date();
        $scope.dateOptions.minDate = $scope.inlineOptions.minDate;
    };

    $scope.toggleMin();

    $scope.open1 = function () {
        $scope.popup1.opened = true;
    };

    $scope.setDate = function (year, month, day) {
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
            var dayToCheck = new Date(date).setHours(0, 0, 0, 0);

            for (var i = 0; i < $scope.events.length; i++) {
                var currentDay = new Date($scope.events[i].date).setHours(0, 0, 0, 0);

                if (dayToCheck === currentDay) {
                    return $scope.events[i].status;
                }
            }
        }

        return '';
    }

    $http.get('./airports.json')
        .success(function (data) {
            $scope.airports = data;
            $scope.findMyFlight();
        });

});
