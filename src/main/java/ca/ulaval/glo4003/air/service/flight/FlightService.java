package ca.ulaval.glo4003.air.service.flight;

import ca.ulaval.glo4003.air.api.flight.dto.FlightSearchResultDto;
import ca.ulaval.glo4003.air.domain.DateTimeFactory;
import ca.ulaval.glo4003.air.domain.flight.*;
import ca.ulaval.glo4003.air.transfer.flight.FlightAssembler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Logger;

public class FlightService {

    private final Logger logger = Logger.getLogger(FlightService.class.getName());

    private final FlightRepository flightRepository;
    private final DateTimeFactory dateTimeFactory;
    private final FlightAssembler flightAssembler;

    public FlightService(FlightRepository flightRepository, DateTimeFactory dateTimeFactory, FlightAssembler flightAssembler) {
        this.flightRepository = flightRepository;
        this.dateTimeFactory = dateTimeFactory;
        this.flightAssembler = flightAssembler;
    }

    public FlightSearchResultDto findAllWithFilters(String departureAirport, String arrivalAirport, LocalDateTime departureDate, double weight, boolean isOnlyAirVivant) {

        validateAirportsArePresent(departureAirport, arrivalAirport);
        validateWeightIsPresent(weight);
        logRequest(departureAirport, arrivalAirport, departureDate, weight, isOnlyAirVivant);

        FlightQueryBuilder query = flightRepository.query()
                                                   .isDepartingFrom(departureAirport)
                                                   .isGoingTo(arrivalAirport);

        if (departureDate != null) {
            query.isLeavingOn(departureDate);
        } else {
            query.isLeavingAfter(dateTimeFactory.now());
        }

        if (isOnlyAirVivant) {
            query.isAirVivant();
        }

        List<Flight> allFlights = query.toList();
        query.acceptsWeight(weight);
        List<Flight> flightsFilteredByWeight = query.toList();

        boolean flightsWereFilteredByWeight = flightsFilteredByWeight.size() < allFlights.size();
        FlightSearchResult searchResult = new FlightSearchResult(flightsFilteredByWeight, weight, flightsWereFilteredByWeight);
        return flightAssembler.create(searchResult);
    }

    private void logRequest(String departureAirport, String arrivalAirport, LocalDateTime departureDate, double weight, boolean isOnlyAirVivant) {
        String query = "Finding all flights from " + departureAirport + " to " + arrivalAirport + "with a luggage weight of " + weight + "lbs" + "with a boolean value for being airvivant is" + isOnlyAirVivant;
        if (departureDate != null) {
            query = query.concat(" on " + departureDate.toString());
        }
        logger.info(query);
    }

    public void reservePlacesInFlight(String airlineCompany, String arrivalAirport, LocalDateTime departureDate, int ticketsQuantity) throws FlightNotFoundException {
        Flight flight = findFlight(airlineCompany, arrivalAirport, departureDate);
        flight.reservePlaces(ticketsQuantity);
        this.flightRepository.save(flight);
    }

    public void releasePlacesInFlight(String airlineCompany, String arrivalAirport, LocalDateTime departureDate, int ticketsQuantity) throws FlightNotFoundException {
        Flight flight = findFlight(airlineCompany, arrivalAirport, departureDate);
        flight.releasePlaces(ticketsQuantity);
        this.flightRepository.save(flight);
    }

    private Flight findFlight(String airlineCompany, String arrivalAirport, LocalDateTime departureDate) throws FlightNotFoundException {
        return flightRepository.query()
                               .hasAirlineCompany(airlineCompany)
                               .isGoingTo(arrivalAirport)
                               .isLeavingOn(departureDate)
                               .findOne()
                               .orElseThrow(() -> new FlightNotFoundException("Flight " + airlineCompany + " " + arrivalAirport + " does not exists."));
    }

    private void validateAirportsArePresent(String departureAirport, String arrivalAirport) {
        if (departureAirport == null || arrivalAirport == null) {
            throw new InvalidParameterException("Missing departure or arrival airport.");
        }
    }

    private void validateWeightIsPresent(double weight) {
        if (weight == 0) {
            throw new InvalidParameterException("Missing luggage weight.");
        }
    }
}
