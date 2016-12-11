package ca.ulaval.glo4003.air.domain.flight;

import java.util.List;

public interface FlightSortingStrategy {

    List<PassengerFlight> sort(List<PassengerFlight> passengerFlights);
}
