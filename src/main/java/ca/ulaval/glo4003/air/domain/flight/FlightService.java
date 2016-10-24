package ca.ulaval.glo4003.air.domain.flight;

import ca.ulaval.glo4003.air.api.flight.dto.FlightDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class FlightService {

    private Logger logger = Logger.getLogger(FlightService.class.getName());

    private FlightRepository flightRepository;
    private FlightAssembler flightAssembler;

    public FlightService(FlightRepository flightRepository, FlightAssembler flightAssembler) {
        this.flightRepository = flightRepository;
        this.flightAssembler = flightAssembler;
    }

    public List<FlightDto> findAllWithFilters(String departureAirport, String arrivalAirport, LocalDateTime departureDate) {
        logRequest(departureAirport, arrivalAirport, departureDate);

        Stream<Flight> flights;

        if (departureDate != null) {
            flights = flightRepository.findAllWithFilters(departureAirport, arrivalAirport, departureDate);
        } else {
            flights = flightRepository.findFuture(departureAirport, arrivalAirport);
        }

        return flightAssembler.create(flights);
    }

    private void logRequest(String departureAirport, String arrivalAirport, LocalDateTime departureDate) {
        String query = "Finding all flights from " + departureAirport + " to " + arrivalAirport;
        if (departureDate != null) {
            query = query.concat(" on " + departureDate.toString());
        }
        logger.info(query);
    }
}