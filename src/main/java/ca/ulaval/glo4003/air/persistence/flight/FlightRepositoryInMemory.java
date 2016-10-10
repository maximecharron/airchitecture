package ca.ulaval.glo4003.air.persistence.flight;

import ca.ulaval.glo4003.air.domain.flight.Flight;
import ca.ulaval.glo4003.air.domain.flight.FlightFilters;
import ca.ulaval.glo4003.air.domain.flight.FlightRepository;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class FlightRepositoryInMemory implements FlightRepository {

    private Map<String, Flight> flights = new HashMap<>();

    @Override
    public void save(Flight flight) {
        flights.put(flight.getFlightNumber(), flight);
    }

    @Override
    public Stream<Flight> findAllWithFilters(FlightFilters flightFilters) {

        Optional<String> departureAirport = flightFilters.getDepartureAirport();
        Optional<String> arrivalAirport = flightFilters.getArrivalAirport();
        Optional<LocalDateTime> departureDate = flightFilters.getDepartureDate();

        return flights.values()
                      .stream()
                      .filter(flightsWithDepartureDate(departureDate))
                      .filter(flightsWithDepartureAirport(departureAirport))
                      .filter(flightsWithArrivalAirport(arrivalAirport));
    }

    private Predicate<Flight> flightsWithDepartureDate(Optional<LocalDateTime> departureDate) {
        return flight -> departureDate.isPresent() ? flight.isLeavingAfter(departureDate.get()) : flight.isFuture();
    }

    private Predicate<Flight> flightsWithDepartureAirport(Optional<String> departureAirport) {
        return flight -> !departureAirport.isPresent() || flight.isDepartingFrom(departureAirport.get());
    }

    private Predicate<Flight> flightsWithArrivalAirport(Optional<String> arrivalAirport) {
        return flight -> !arrivalAirport.isPresent() || flight.isGoingTo(arrivalAirport.get());
    }
}
