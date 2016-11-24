package ca.ulaval.glo4003.air.service.flight;

import ca.ulaval.glo4003.air.api.flight.dto.FlightSearchResultDto;
import ca.ulaval.glo4003.air.domain.DateTimeFactory;
import ca.ulaval.glo4003.air.domain.airplane.SeatMap;
import ca.ulaval.glo4003.air.domain.flight.*;
import ca.ulaval.glo4003.air.transfer.flight.FlightAssembler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

public class FlightService {

    private final Logger logger = Logger.getLogger(FlightService.class.getName());

    private final FlightRepository flightRepository;
    private final WeightFilterVerifier weightFilterVerifier;
    private final DateTimeFactory dateTimeFactory;
    private final FlightAssembler flightAssembler;

    public FlightService(FlightRepository flightRepository, WeightFilterVerifier weightFilterVerifier, DateTimeFactory dateTimeFactory, FlightAssembler flightAssembler) {
        this.flightRepository = flightRepository;
        this.weightFilterVerifier = weightFilterVerifier;
        this.dateTimeFactory = dateTimeFactory;
        this.flightAssembler = flightAssembler;
    }

    public FlightSearchResultDto findAllWithFilters(String departureAirport, String arrivalAirport, LocalDateTime departureDate, double weight, boolean isOnlyAirVivant, boolean acceptsAirCargo) {

        validateAirportsArePresent(departureAirport, arrivalAirport);
        validateWeightIsPresent(weight);
        logRequest(departureAirport, arrivalAirport, departureDate, weight, isOnlyAirVivant);

        FlightQueryBuilder query = flightRepository.query()
                                                   .isDepartingFrom(departureAirport)
                                                   .isGoingTo(arrivalAirport)
                                                   .isNotAirCargo();

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

        boolean flightsWereFilteredByWeight = weightFilterVerifier.verifyFlightsFilteredByWeightWithFilters(flightsFilteredByWeight, allFlights);

        Map<Flight, Flight> flightsWithAirCargo = new HashMap<>();
        if (flightsWereFilteredByWeight && acceptsAirCargo) {
            allFlights.removeAll(flightsFilteredByWeight);
            flightsWithAirCargo.putAll(searchForAirCargo(allFlights, departureAirport, arrivalAirport, isOnlyAirVivant));
        }

        FlightSearchResult searchResult = new FlightSearchResult(flightsFilteredByWeight, weight, flightsWereFilteredByWeight, flightsWithAirCargo);
        return flightAssembler.create(searchResult);
    }

    private Map<Flight, Flight> searchForAirCargo(List<Flight> allFlights, String departureAirport, String arrivalAirport, boolean isOnlyAirVivant) {
        FlightQueryBuilder query = flightRepository.query()
                .isDepartingFrom(departureAirport)
                .isGoingTo(arrivalAirport)
                .isAirCargo();

        if (isOnlyAirVivant) {
            query.isAirVivant();
        }

        List<Flight> airCargoFlights = query.toList();
        Map<Flight, Flight> flightsWithAirCargo = new HashMap<>();

        allFlights.forEach(flight -> {
            Optional<Flight> optionalAirCargoFlight = airCargoFlights.stream().filter(airCargoFlight -> airCargoFlight.isLeavingWithinXDaysOf(flight.getDepartureDate(), 3)).findFirst();
            if (optionalAirCargoFlight.isPresent()) {
                flightsWithAirCargo.put(flight, optionalAirCargoFlight.get());
            }
        });

        return flightsWithAirCargo;
    }

    private void logRequest(String departureAirport, String arrivalAirport, LocalDateTime departureDate, double weight, boolean isOnlyAirVivant) {
        String query = "Finding all flights from " + departureAirport + " to " + arrivalAirport + " with a luggage weight of " + weight + " lbs with a boolean value for being airVivant is " + isOnlyAirVivant;
        if (departureDate != null) {
            query = query.concat(" on " + departureDate.toString());
        }
        logger.info(query);
    }

    public void reservePlacesInFlight(String airlineCompany, String arrivalAirport, LocalDateTime departureDate, SeatMap seatMap) throws FlightNotFoundException {
        Flight flight = findFlight(airlineCompany, arrivalAirport, departureDate);
        flight.reserveSeats(seatMap);
        this.flightRepository.save(flight);
    }

    public void releasePlacesInFlight(String airlineCompany, String arrivalAirport, LocalDateTime departureDate, SeatMap seatMap) throws FlightNotFoundException {
        Flight flight = findFlight(airlineCompany, arrivalAirport, departureDate);
        flight.releaseSeats(seatMap);
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
