package ca.ulaval.glo4003.air.persistence.flight;

import ca.ulaval.glo4003.air.domain.flight.Flight;
import ca.ulaval.glo4003.air.domain.flight.FlightCounter;
import ca.ulaval.glo4003.air.domain.flight.FlightRepository;
import ca.ulaval.glo4003.air.domain.flight.WeightFilterVerifier;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class FlightRepositoryInMemory implements FlightRepository, FlightCounter {

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
    public long countWithFilters(String departureAirport, String arrivalAirport, LocalDateTime departureDate) {
        Stream<Flight> flights = this.findAllWithFilters(departureAirport, arrivalAirport, departureDate);
        return flights.count();

    }

    @Override
    public long countFuture(String departureAirport, String arrivalAirport) {
        Stream<Flight> flights = this.findFuture(departureAirport, arrivalAirport);
        return flights.count();
    }
}
