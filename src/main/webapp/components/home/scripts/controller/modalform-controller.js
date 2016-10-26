homeApp.controller('ModalFormController', [
    '$scope', '$element', 'title', 'close',
    function ($scope, $element, title, close) {
        $scope.departureDate = null;
        $scope.title = title;

        $scope.close = function () {
            console.log("Departure date is: " + $scope.departureDate);
            close({
                departureDate: $scope.departureDate
            }, 500);
        };

        $scope.cancel = function () {
            $element.modal('hide');
            console.log("Departure date is: " + $scope.departureDate);
            close({
                departureDate: $scope.departureDate
            }, 500);
        };
    }]);