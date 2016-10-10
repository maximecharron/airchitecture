package ca.ulaval.glo4003.air.domain.flight;

import java.util.stream.Stream;

public interface FlightRepository {

    Stream<Flight> findAllWithFilters(FlightFilters flightFilters);

    void save(Flight flight);
}
