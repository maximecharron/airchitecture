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
        public FlightQueryBuilder isAirVivant() {
            predicates.add(flight -> flight.isAirVivant());
            return this;
        }

        @Override
        public FlightQueryBuilder hasEconomySeatsAvailable() {
            predicates.add(flight -> flight.isPassengerFlight() && ((PassengerFlight)flight).hasAvailableEconomySeats());
            return this;
        }

        @Override
        public FlightQueryBuilder hasRegularSeatsAvailable() {
            predicates.add(flight -> flight.isPassengerFlight() && ((PassengerFlight)flight).hasAvailableRegularSeats());
            return this;
        }

        @Override
        public FlightQueryBuilder hasBusinessSeatsAvailable() {
            predicates.add(flight -> flight.isPassengerFlight() && ((PassengerFlight)flight).hasAvailableBusinessSeats());
            return this;
        }

        @Override
        public FlightQueryBuilder hasAirlineCompany(String airlineCompany) {
            predicates.add(flight -> flight.isFromCompany(airlineCompany));
            return this;
        }

        @Override
        public List<PassengerFlight> getPassengerFlights() {
            Stream<Flight> filteredFlights = filterFlights();
            return filterPassengerFlights(filteredFlights).collect(Collectors.toList());
        }

        @Override
        public List<AirCargoFlight> getAirCargoFlights() {
            Stream<Flight> filteredFlights = filterFlights();
            return filterAirCargoFlights(filteredFlights).collect(Collectors.toList());
        }

        @Override
        public Optional<PassengerFlight> findOnePassengerFlight() {
            Stream<Flight> filteredFlights = filterFlights();
            return filterPassengerFlights(filteredFlights).findFirst();
        }

        @Override
        public Optional<AirCargoFlight> findOneAirCargoFlight() {
            Stream<Flight> filteredFlights = filterFlights();
            return filterAirCargoFlights(filteredFlights).findFirst();
        }

        private Stream<PassengerFlight> filterPassengerFlights(Stream<Flight> flights) {
            return flights.filter(flight -> flight.isPassengerFlight()).map(flight -> (PassengerFlight) flight);
        }

        private Stream<AirCargoFlight> filterAirCargoFlights(Stream<Flight> flights) {
            return flights.filter(flight -> flight.isAirCargo()).map(flight -> (AirCargoFlight) flight);
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
