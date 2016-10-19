package ca.ulaval.glo4003.air.domain.flight;

import java.time.LocalDateTime;
import java.util.stream.Stream;

public interface FlightRepository {

    Stream<Flight> findAllWithFilters(String departureAirport, String arrivalAirport, LocalDateTime departureDate, double weight);

    Stream<Flight> findFuture(String departureAirport, String arrivalAirport, double weight);

    void save(Flight flight);
}
