package ca.ulaval.glo4003.air.domain.flight;

import java.time.LocalDateTime;
import java.util.List;

public interface FlightQueryBuilder {
    FlightQueryBuilder isGoingTo(String airport);

    FlightQueryBuilder isDepartingFrom(String airport);

    FlightQueryBuilder isLeavingOn(LocalDateTime date);

    FlightQueryBuilder isLeavingAfter(LocalDateTime date);

    FlightQueryBuilder acceptsWeight(double weight);

    List<Flight> toList();
}
