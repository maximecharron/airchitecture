package ca.ulaval.glo4003.air.persistence.flight;

import ca.ulaval.glo4003.air.domain.flight.Flight;
import ca.ulaval.glo4003.air.domain.flight.FlightQueryBuilder;
import ca.ulaval.glo4003.air.domain.flight.FlightRepository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FlightRepositoryInMemory implements FlightRepository {
    private Map<String, Flight> flights = new HashMap<>();

    @Override
    public void save(Flight flight) {
        flights.put(flight.getFlightNumber(), flight);
    }

    @Override
    public FlightQueryBuilder query() {
        return new MemoryFlightQueryBuilder();
    }

    private class MemoryFlightQueryBuilder implements FlightQueryBuilder {
        private Set<Predicate<Flight>> predicates = new HashSet<>();

        @Override
        public FlightQueryBuilder isGoingTo(String airport) {
            predicates.add(flight -> flight.isGoingTo(airport));
            return this;
        }

        @Override
        public FlightQueryBuilder isDepartingFrom(String airport) {
            predicates.add(flight -> flight.isDepartingFrom(airport));
            return this;
        }

        @Override
        public FlightQueryBuilder isLeavingOn(LocalDateTime date) {
            predicates.add(flight -> flight.isLeavingOn(date));
            return this;
        }

        @Override
        public FlightQueryBuilder isLeavingAfter(LocalDateTime date) {
            predicates.add(flight -> flight.isLeavingAfter(date));
            return this;
        }

        @Override
        public FlightQueryBuilder acceptsWeight(double weight) {
            predicates.add(flight -> flight.acceptsWeight(weight));
            return this;
        }

        @Override
        public FlightQueryBuilder hasFlightNumber(String flightNumber) {
            predicates.add(flight -> flight.getFlightNumber().equals(flightNumber));
            return this;
        }

        @Override
        public List<Flight> toList() {
            Stream<Flight> flightStream = filterFlightStream();
            return flightStream.collect(Collectors.toList());
        }

        @Override
        public Optional<Flight> findOne() {
            Stream<Flight> flightStream = filterFlightStream();
            return flightStream.findFirst();
        }

        private Stream<Flight> filterFlightStream() {
            Stream<Flight> flightStream = flights.values().stream();
            for (Predicate<Flight> predicate: predicates) {
                flightStream = flightStream.filter(predicate);
            }
            return flightStream;
        }
    }
}
