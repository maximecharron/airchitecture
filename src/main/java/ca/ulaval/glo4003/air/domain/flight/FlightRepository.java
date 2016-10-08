package ca.ulaval.glo4003.air.domain.flight;

import java.time.LocalDateTime;
import java.util.stream.Stream;

public interface FlightRepository {

    Stream<Flight> findAllWithFilters(String departureAirport, String arrivalAirport, LocalDateTime departureDate);

    void save(Flight flight);
}
