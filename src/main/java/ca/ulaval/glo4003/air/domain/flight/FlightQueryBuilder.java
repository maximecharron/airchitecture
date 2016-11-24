package ca.ulaval.glo4003.air.domain.flight;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface FlightQueryBuilder {

    FlightQueryBuilder isGoingTo(String airport);

    FlightQueryBuilder isDepartingFrom(String airport);

    FlightQueryBuilder isLeavingOn(LocalDateTime date);

    FlightQueryBuilder isLeavingAfter(LocalDateTime date);

    FlightQueryBuilder acceptsWeight(double weight);

    FlightQueryBuilder isAirVivant();

    FlightQueryBuilder hasEconomicSeatsAvailable();

    FlightQueryBuilder hasRegularSeatsAvailable();

    FlightQueryBuilder hasBusinessSeatsAvailable();

    FlightQueryBuilder hasAirlineCompany(String flightNumber);

    List<Flight> toList();

    Optional<Flight> findOne();

}
