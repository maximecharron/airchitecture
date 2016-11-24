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

    FlightQueryBuilder hasAirlineCompany(String flightNumber);

    List<PassengerFlight> getPassengerFlights();

    List<AirCargoFlight> getAirCargoFlights();

    Optional<PassengerFlight> findOnePassengerFlight();
}
