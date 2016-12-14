package ca.ulaval.glo4003.air.infrastructure.flight;

import ca.ulaval.glo4003.air.domain.flight.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FlightRepositoryInMemory implements FlightRepository {

    private Map<String, Flight> flights = new HashMap<>();

    @Override
    public void save(Flight flight) {
        flights.put(createFlightKey(flight), flight);
    }

    private String createFlightKey(Flight flight) {
        return flight.getAirlineCompany() + flight.getArrivalAirport() + flight.getDepartureDate().toString();
    }

    @Override
    public FlightQueryBuilder query() {
        return new FlightQueryBuilderInMemory(flights);
    }
}
