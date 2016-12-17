package ca.ulaval.glo4003.air.infrastructure.flight;

import ca.ulaval.glo4003.air.domain.flight.AirCargoFlight;
import ca.ulaval.glo4003.air.domain.flight.Flight;
import ca.ulaval.glo4003.air.domain.flight.FlightQueryBuilder;
import ca.ulaval.glo4003.air.domain.flight.PassengerFlight;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class FlightQueryBuilderInMemory implements FlightQueryBuilder {
    private Map<String, Flight> flights = new HashMap<>();
    private Set<Predicate<Flight>> predicates = new HashSet<>();

    FlightQueryBuilderInMemory(Map<String, Flight> flights) {
        this.flights = flights;
    }

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
    public FlightQueryBuilder isLeavingOn(LocalDateTime date) {
        predicates.add(flight -> flight.isLeavingOn(date));
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
    public FlightQueryBuilder hasSeatsAvailable(boolean economySeats, boolean regularSeats, boolean businessSeats) {
        predicates.add(flight -> flight.isPassengerFlight() && (((PassengerFlight) flight).hasAvailableEconomySeats() == economySeats
            || ((PassengerFlight) flight).hasAvailableRegularSeats() == regularSeats
            || ((PassengerFlight) flight).hasAvailableBusinessSeats() == businessSeats));
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
    public Optional<PassengerFlight> getOnePassengerFlight() {
        Stream<Flight> filteredFlights = filterFlights();
        return filterPassengerFlights(filteredFlights).findFirst();
    }

    @Override
    public Optional<AirCargoFlight> getOneAirCargoFlight() {
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
