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
                                                   .isGoingTo(arrivalAirport);

        if (departureDate != null) {
            query.isLeavingOn(departureDate);
        } else {
            query.isLeavingAfter(dateTimeFactory.now());
        }

        if (isOnlyAirVivant) {
            query.isAirVivant();
        }

        List<PassengerFlight> allPassengerFlights = query.getPassengerFlights();
        query.acceptsWeight(weight);
        List<PassengerFlight> flightsFilteredByWeight = query.getPassengerFlights();

        boolean flightsWereFilteredByWeight = weightFilterVerifier.verifyFlightsFilteredByWeightWithFilters(flightsFilteredByWeight, allPassengerFlights);

        Map<PassengerFlight, AirCargoFlight> flightsWithAirCargo = new HashMap<>();
        if (flightsWereFilteredByWeight && acceptsAirCargo) {
            allPassengerFlights.removeAll(flightsFilteredByWeight);
            flightsWithAirCargo.putAll(searchForAirCargo(allPassengerFlights, departureAirport, arrivalAirport, isOnlyAirVivant));
        }

        FlightSearchResult searchResult = new FlightSearchResult(flightsFilteredByWeight, weight, flightsWereFilteredByWeight, flightsWithAirCargo);
        return flightAssembler.create(searchResult);
    }

    private Map<PassengerFlight, AirCargoFlight> searchForAirCargo(List<PassengerFlight> allFlights, String departureAirport, String arrivalAirport, boolean isOnlyAirVivant) {
        FlightQueryBuilder query = flightRepository.query()
                .isDepartingFrom(departureAirport)
                .isGoingTo(arrivalAirport);

        if (isOnlyAirVivant) {
            query.isAirVivant();
        }

        List<AirCargoFlight> airCargoFlights = query.getAirCargoFlights();
        Map<PassengerFlight, AirCargoFlight> flightsWithAirCargo = new HashMap<>();

        allFlights.forEach(flight -> {
            Optional<AirCargoFlight> optionalAirCargoFlight = airCargoFlights.stream().filter(airCargoFlight -> airCargoFlight.isLeavingWithinXDaysOf(flight.getDepartureDate(), 3)).findFirst();
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
        PassengerFlight flight = findPassengerFlight(airlineCompany, arrivalAirport, departureDate);
        flight.reserveSeats(seatMap);
        this.flightRepository.save(flight);
    }

    public void releasePlacesInFlight(String airlineCompany, String arrivalAirport, LocalDateTime departureDate, SeatMap seatMap) throws FlightNotFoundException {
        PassengerFlight flight = findPassengerFlight(airlineCompany, arrivalAirport, departureDate);
        flight.releaseSeats(seatMap);
        this.flightRepository.save(flight);
    }

    private PassengerFlight findPassengerFlight(String airlineCompany, String arrivalAirport, LocalDateTime departureDate) throws FlightNotFoundException {
        return flightRepository.query()
                               .hasAirlineCompany(airlineCompany)
                               .isGoingTo(arrivalAirport)
                               .isLeavingOn(departureDate)
                               .findOnePassengerFlight()
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
