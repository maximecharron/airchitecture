package ca.ulaval.glo4003.air.domain.flight;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface FlightQueryBuilder {

    FlightQueryBuilder isGoingTo(String airport);

    FlightQueryBuilder isDepartingFrom(String airport);

    FlightQueryBuilder isLeavingAfterOrOn(LocalDateTime date);

    FlightQueryBuilder acceptsWeight(double weight);

    FlightQueryBuilder isAirVivant();

    FlightQueryBuilder hasSeatsAvailable(boolean economySeats, boolean regularSeats, boolean businessSeats);

    FlightQueryBuilder hasAirlineCompany(String flightNumber);

    List<PassengerFlight> getPassengerFlights();

    List<AirCargoFlight> getAirCargoFlights();

    Optional<PassengerFlight> findOnePassengerFlight();

    Optional<AirCargoFlight> findOneAirCargoFlight();
}
