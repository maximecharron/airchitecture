package ca.ulaval.glo4003.air.infrastructure.flight;

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
        flights.put(createFlightKey(flight), flight);
    }

    private String createFlightKey(Flight flight) {
        return flight.getAirlineCompany() + flight.getArrivalAirport() + flight.getDepartureDate().toString();
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
        public FlightQueryBuilder isLeavingWithinXDaysOf(LocalDateTime date, int numberOfDays) {
            predicates.add(flight -> flight.isLeavingWithinXDaysOf(date, numberOfDays));
            return this;
        }

        @Override
        public FlightQueryBuilder acceptsWeight(double weight) {
            predicates.add(flight -> flight.acceptsWeight(weight));
            return this;
        }

        @Override
        public FlightQueryBuilder isAirVivant() {
            predicates.add(flight -> flight.isAirVivant());
            return this;
        }

        @Override
        public FlightQueryBuilder isAirCargo() {
            predicates.add(flight -> flight.isAirCargo());
            return this;
        }

        @Override
        public FlightQueryBuilder isNotAirCargo() {
            predicates.add(flight -> !flight.isAirCargo());
            return this;
        }

        @Override
        public FlightQueryBuilder hasAirlineCompany(String airlineCompany) {
            predicates.add(flight -> flight.isFromCompany(airlineCompany));
            return this;
        }

        @Override
        public List<Flight> toList() {
            Stream<Flight> filteredFlights = filterFlights();
            return filteredFlights.collect(Collectors.toList());
        }

        @Override
        public Optional<Flight> findOne() {
            Stream<Flight> filteredFlights = filterFlights();
            return filteredFlights.findFirst();
        }

        private Stream<Flight> filterFlights() {
            Stream<Flight> flightsToFilter = flights.values().stream();
            for (Predicate<Flight> predicate : predicates) {
                flightsToFilter = flightsToFilter.filter(predicate);
            }
            return flightsToFilter;
        }
    }

}
