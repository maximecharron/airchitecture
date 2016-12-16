package ca.ulaval.glo4003.air.domain.flight;

import ca.ulaval.glo4003.air.transfer.flight.dto.FlightSearchQueryDto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class AirCargoFlightMatcher {

    private final FlightRepository flightRepository;

    public AirCargoFlightMatcher(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    public Map<PassengerFlight, AirCargoFlight> matchWithAirCargoFlights(List<PassengerFlight> passengerFlights, FlightSearchQueryDto searchDto) {
        List<AirCargoFlight> airCargoFlights = findAirCargoFlights(searchDto);

        Map<PassengerFlight, AirCargoFlight> flightsWithAirCargo = new HashMap<>();

        passengerFlights.forEach(flight -> {
            Optional<AirCargoFlight> optionalAirCargoFlight = airCargoFlights.stream().filter(airCargoFlight -> airCargoFlight.isLeavingWithinXDaysOf(flight.getDepartureDate(), 3)).findFirst();
            if (optionalAirCargoFlight.isPresent()) {
                flightsWithAirCargo.put(flight, optionalAirCargoFlight.get());
            }
        });

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
