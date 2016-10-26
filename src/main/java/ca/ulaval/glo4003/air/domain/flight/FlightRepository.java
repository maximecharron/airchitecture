package ca.ulaval.glo4003.air.domain.flight;

import java.time.LocalDateTime;
import java.util.Optional;

public interface FlightRepository {
    void save(Flight flight);
    FlightQueryBuilder query();

    Optional<Flight> findOne(String flightNumber, LocalDateTime departureDate);
}
