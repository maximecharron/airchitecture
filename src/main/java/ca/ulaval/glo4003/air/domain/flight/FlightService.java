package ca.ulaval.glo4003.air.domain.flight;

import ca.ulaval.glo4003.air.api.flight.dto.FlightSearchDto;
import ca.ulaval.glo4003.air.domain.DateTimeFactory;

import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class FlightService {
    private Logger logger = Logger.getLogger(FlightService.class.getName());

    private FlightRepository flightRepository;
    private FlightAssembler flightAssembler;
    private WeightFilterVerifier weightFilterVerifier;
    private DateTimeFactory dateTimeFactory;

    public FlightService(FlightRepository flightRepository, FlightAssembler flightAssembler, WeightFilterVerifier weightFilterVerifier, DateTimeFactory dateTimeFactory) {
        this.flightRepository = flightRepository;
        this.flightAssembler = flightAssembler;
        this.weightFilterVerifier = weightFilterVerifier;
        this.dateTimeFactory = dateTimeFactory;
    }

    public FlightSearchDto findAllWithFilters(String departureAirport, String arrivalAirport, LocalDateTime departureDate, double weight) {
        logRequest(departureAirport, arrivalAirport, departureDate, weight);
        FlightQueryBuilder query = flightRepository.query()
                .isDepartingFrom(departureAirport)
                .isGoingTo(arrivalAirport);

        if (departureDate != null) { query.isLeavingOn(departureDate); }
        else { query.isLeavingAfter(dateTimeFactory.now()); }

        List<Flight> allFlights = query.toList();
        query.acceptsWeight(weight);
        List<Flight> flightsFilteredByWeight = query.toList();

        boolean flightsWereFilteredByWeight = weightFilterVerifier.verifyFlightsFilteredByWeightWithFilters(flightsFilteredByWeight, allFlights);

        return flightAssembler.create(flightsFilteredByWeight, weight, flightsWereFilteredByWeight);
    }

    private void logRequest(String departureAirport, String arrivalAirport, LocalDateTime departureDate, double weight) {
        String query = "Finding all flights from " + departureAirport + " to " + arrivalAirport + "with a luggage weight of " + weight + "lbs";
        if (departureDate != null) {
            query = query.concat(" on " + departureDate.toString());
        }
        logger.info(query);
    }
}
