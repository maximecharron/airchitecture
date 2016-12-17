package ca.ulaval.glo4003.air.domain.flight;

import ca.ulaval.glo4003.air.transfer.flight.dto.FlightSearchQueryDto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AirCargoFlightMatcher {

    private static final int WITHIN_3_DAYS = 3;

    private final FlightRepository flightRepository;

    public AirCargoFlightMatcher(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    public Map<PassengerFlight, AirCargoFlight> matchWithAirCargoFlights(List<PassengerFlight> passengerFlights, FlightSearchQueryDto searchDto) {
        List<AirCargoFlight> airCargoFlights = findAirCargoFlights(searchDto);

        Map<PassengerFlight, AirCargoFlight> flightsWithAirCargo = new HashMap<>();

        passengerFlights.forEach(flight ->
            airCargoFlights.stream()
                           .filter(airCargoFlight -> airCargoFlight.isLeavingWithinXDaysOf(flight.getDepartureDate(), WITHIN_3_DAYS))
                           .findFirst()
                           .ifPresent(cargo -> flightsWithAirCargo.put(flight, cargo)));

        return flightsWithAirCargo;
    }

    private List<AirCargoFlight> findAirCargoFlights(FlightSearchQueryDto searchDto) {
        FlightQueryBuilder query = flightRepository.query()
                                                   .isDepartingFrom(searchDto.departureAirport)
                                                   .isGoingTo(searchDto.arrivalAirport)
                                                   .acceptsWeight(searchDto.weight);

        if (searchDto.onlyAirVivant) {
            query.isAirVivant();
        }

        return query.getAirCargoFlights();
    }
}
