package ca.ulaval.glo4003.air.persistence.flight;

import ca.ulaval.glo4003.air.domain.flight.Flight;
import ca.ulaval.glo4003.air.domain.flight.FlightRepository;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class FlightRepositoryInMemory implements FlightRepository {

    private Map<String, Flight> flights = new HashMap<>();

    @Override
    public void save(Flight flight) {
        flights.put(flight.getFlightNumber(), flight);
    }

    @Override
    public Stream<Flight> findAllWithFilters(String departureAirport, String arrivalAirport, LocalDateTime departureDate) {
        return flights.values()
                .stream()
                .filter(flight -> flight.isLeavingOn(departureDate))
                .filter(flight -> flight.isDepartingFrom(departureAirport))
                .filter(flight -> flight.isGoingTo(arrivalAirport));
    }

    @Override
    public Stream<Flight> findFuture(String departureAirport, String arrivalAirport) {
        return flights.values()
                .stream()
                .filter(Flight::isFuture)
                .filter(flight -> flight.isDepartingFrom(departureAirport))
                .filter(flight -> flight.isGoingTo(arrivalAirport));
    }
}
