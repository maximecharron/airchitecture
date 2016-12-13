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
        public FlightQueryBuilder isLeavingAfterOrOn(LocalDateTime date) {
            predicates.add(flight -> flight.isLeavingAfterOrOn(date));
            return this;
        }

        @Override
        public FlightQueryBuilder acceptsWeight(double weight) {
            predicates.add(flight -> flight.acceptsWeight(weight));
            return this;
        }

        @Override
        public FlightQueryBuilder isAirVivant() {
            predicates.add(Flight::isAirVivant);
            return this;
        }

        @Override
        public FlightQueryBuilder hasSeatsAvailable(boolean economySeats, boolean regularSeats, boolean businessSeats){
            predicates.add(flight -> flight.isPassengerFlight() && (((PassengerFlight)flight).hasAvailableEconomySeats() == economySeats
                    || ((PassengerFlight)flight).hasAvailableRegularSeats() == regularSeats
                    || ((PassengerFlight)flight).hasAvailableBusinessSeats() == businessSeats));
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
            return flights.filter(Flight::isPassengerFlight)
                          .map(flight -> (PassengerFlight) flight);
        }

        private Stream<AirCargoFlight> filterAirCargoFlights(Stream<Flight> flights) {
            return flights.filter(Flight::isAirCargo)
                          .map(flight -> (AirCargoFlight) flight);
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
