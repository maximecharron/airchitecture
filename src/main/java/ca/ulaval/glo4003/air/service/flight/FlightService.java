package ca.ulaval.glo4003.air.service.flight;

import ca.ulaval.glo4003.air.domain.DateTimeFactory;
import ca.ulaval.glo4003.air.domain.airplane.SeatMap;
import ca.ulaval.glo4003.air.domain.flight.*;
import ca.ulaval.glo4003.air.service.user.UserService;
import ca.ulaval.glo4003.air.transfer.flight.FlightAssembler;
import ca.ulaval.glo4003.air.transfer.flight.dto.FlightSearchQueryDto;
import ca.ulaval.glo4003.air.transfer.flight.dto.FlightSearchResultDto;

import java.text.MessageFormat;
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
    private final FlightSortingStrategy flightSortingStrategy;
    private final FlightAssembler flightAssembler;
    private final UserService userService;

    public FlightService(FlightRepository flightRepository, WeightFilterVerifier weightFilterVerifier, DateTimeFactory dateTimeFactory, FlightSortingStrategy flightSortingStrategy, FlightAssembler flightAssembler, UserService userService) {
        this.flightRepository = flightRepository;
        this.weightFilterVerifier = weightFilterVerifier;
        this.dateTimeFactory = dateTimeFactory;
        this.flightSortingStrategy = flightSortingStrategy;
        this.flightAssembler = flightAssembler;
        this.userService = userService;
    }

    public FlightSearchResultDto findAllWithFilters(String accessToken, FlightSearchQueryDto searchDto) {
        validateAirportsArePresent(searchDto.departureAirport, searchDto.arrivalAirport);
        validateWeightIsPresent(searchDto.weight);
        logRequest(searchDto);

        if (accessToken != null) {
            userService.incrementAuthenticatedUserSearchPreferences(accessToken,
                searchDto.onlyAirVivant,
                searchDto.hasEconomySeats,
                searchDto.hasRegularSeats,
                searchDto.hasBusinessSeats);
        }

        FlightQueryBuilder query = flightRepository.query()
                                                   .isDepartingFrom(searchDto.departureAirport)
                                                   .isGoingTo(searchDto.arrivalAirport);

        if (searchDto.departureDate != null) {
            query.isLeavingAfter(searchDto.departureDate);
        } else {
            query.isLeavingAfter(dateTimeFactory.now());
        }

        if (searchDto.onlyAirVivant) {
            query.isAirVivant();
        }

        if (searchDto.hasEconomySeats || searchDto.hasBusinessSeats || searchDto.hasRegularSeats) {
            query.hasSeatsAvailable(searchDto.hasEconomySeats, searchDto.hasRegularSeats, searchDto.hasBusinessSeats);
        }

        List<PassengerFlight> allPassengerFlights = query.getPassengerFlights();
        query.acceptsWeight(searchDto.weight);
        List<PassengerFlight> flightsFilteredByWeight = query.getPassengerFlights();

        boolean flightsWereFilteredByWeight = weightFilterVerifier.verifyFlightsFilteredByWeightWithFilters(flightsFilteredByWeight, allPassengerFlights);

        Map<PassengerFlight, AirCargoFlight> flightsWithAirCargo = new HashMap<>();
        if (flightsWereFilteredByWeight && searchDto.acceptsAirCargo) {
            allPassengerFlights.removeAll(flightsFilteredByWeight);
            flightsWithAirCargo.putAll(searchForAirCargo(allPassengerFlights, searchDto.departureAirport, searchDto.arrivalAirport, searchDto.onlyAirVivant));
        }

        List<PassengerFlight> sortedFlights = flightSortingStrategy.sort(allPassengerFlights);//todo : Ralex plz take these flights

        FlightSearchResult searchResult = new FlightSearchResult(flightsFilteredByWeight, searchDto.weight, flightsWereFilteredByWeight, flightsWithAirCargo);
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

    public void reserveSpaceInAirCargoFlight(String airlineCompany, String arrivalAirport, LocalDateTime departureDate, double luggageWeight) throws FlightNotFoundException {
        AirCargoFlight flight = findAirCargoFlight(airlineCompany, arrivalAirport, departureDate);
        flight.reserveSpace(luggageWeight);
        this.flightRepository.save(flight);
    }

    public void releaseSpaceInAirCargoFlight(String airlineCompany, String arrivalAirport, LocalDateTime departureDate, double luggageWeight) throws FlightNotFoundException {
        AirCargoFlight flight = findAirCargoFlight(airlineCompany, arrivalAirport, departureDate);
        flight.releaseSpace(luggageWeight);
        this.flightRepository.save(flight);
    }

    private AirCargoFlight findAirCargoFlight(String airlineCompany, String arrivalAirport, LocalDateTime departureDate) throws FlightNotFoundException {
        return flightRepository.query()
                               .hasAirlineCompany(airlineCompany)
                               .isGoingTo(arrivalAirport)
                               .isLeavingAfter(departureDate)
                               .findOneAirCargoFlight()
                               .orElseThrow(() -> new FlightNotFoundException("Flight " + airlineCompany + " " + arrivalAirport + " does not exists."));
    }

    private PassengerFlight findPassengerFlight(String airlineCompany, String arrivalAirport, LocalDateTime departureDate) throws FlightNotFoundException {
        return flightRepository.query()
                               .hasAirlineCompany(airlineCompany)
                               .isGoingTo(arrivalAirport)
                               .isLeavingAfter(departureDate)
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

    private void logRequest(FlightSearchQueryDto flightSearchQueryDto) {
        StringBuilder builder = new StringBuilder();
        Object[] messageArguments = {
            flightSearchQueryDto.departureAirport,
            flightSearchQueryDto.arrivalAirport,
            flightSearchQueryDto.weight,
            flightSearchQueryDto.onlyAirVivant,
            flightSearchQueryDto.hasEconomySeats,
            flightSearchQueryDto.hasRegularSeats,
            flightSearchQueryDto.hasBusinessSeats,
            flightSearchQueryDto.departureDate
        };

        builder.append("Finding all flights from {0} to {1}");
        builder.append(" with a luggage weight of {2} lbs");
        builder.append(" with airVivant = {3}");
        builder.append(" with: Economic Flight = {4}");
        builder.append(" with: Regular Flight = {5}");
        builder.append(" with: Business Flight = {6}");
        if (flightSearchQueryDto.departureDate != null) {
            builder.append(" on {7}");
        }

        MessageFormat messageFormat = new MessageFormat(builder.toString());
        logger.info(messageFormat.format(messageArguments));

    }
}
