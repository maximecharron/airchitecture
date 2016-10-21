package ca.ulaval.glo4003.air.domain.flight;

import ca.ulaval.glo4003.air.api.flight.dto.FlightDto;
import ca.ulaval.glo4003.air.api.flight.dto.FlightSearchDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class FlightService {

    private Logger logger = Logger.getLogger(FlightService.class.getName());

    private FlightRepository flightRepository;
    private FlightAssembler flightAssembler;
    private WeightFilterVerifier weightFilterVerifier;

    public FlightService(FlightRepository flightRepository, FlightAssembler flightAssembler, WeightFilterVerifier weightFilterVerifier) {
        this.flightRepository = flightRepository;
        this.flightAssembler = flightAssembler;
        this.weightFilterVerifier = weightFilterVerifier;
    }

    public FlightSearchDto findAllWithFilters(String departureAirport, String arrivalAirport, LocalDateTime departureDate, double weight) {
        logRequest(departureAirport, arrivalAirport, departureDate, weight);

        Stream<Flight> flights;

        boolean flightsWereFilteredByWeight;
        if (departureDate != null) {
            flights = flightRepository.findAllWithFilters(departureAirport, arrivalAirport, departureDate);
            flightsWereFilteredByWeight = weightFilterVerifier.verifyFlightsFilteredByWeightWithFilters(flights, departureAirport, arrivalAirport, departureDate);
        } else {
            LocalDateTime today = LocalDateTime.now();
            flights = flightRepository.findAllWithFilters(departureAirport, arrivalAirport, today);
            flightsWereFilteredByWeight = weightFilterVerifier.verifyFlightsFilteredByWeightWithFilters(flights, departureAirport, arrivalAirport, today);
        }

        return flightAssembler.create(flights, weight, flightsWereFilteredByWeight);
    }

    private void logRequest(String departureAirport, String arrivalAirport, LocalDateTime departureDate, double weight) {
        String query = "Finding all flights from " + departureAirport + " to " + arrivalAirport + "with a luggage weight of " + weight + "lbs";
        if (departureDate != null) {
            query = query.concat(" on " + departureDate.toString());
        }
        logger.info(query);
    }
}
