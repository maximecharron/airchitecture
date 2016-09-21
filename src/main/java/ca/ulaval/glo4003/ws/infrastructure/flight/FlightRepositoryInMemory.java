package ca.ulaval.glo4003.ws.infrastructure.flight;

import ca.ulaval.glo4003.ws.domain.flight.Flight;
import ca.ulaval.glo4003.ws.domain.flight.FlightRepository;

import java.time.LocalDate;
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
    public Stream<Flight> findAllWithFilters(String departureAirport, String arrivalAirport, LocalDateTime date) {
        return flights.values()
                      .stream()
                      .filter(f -> date != null ? f.isLeavingOn(date) : f.isFuture())
                      .filter(f -> f.isDepartingFrom(departureAirport))
                      .filter(f -> f.isGoingTo(arrivalAirport));
    }
}
