package ca.ulaval.glo4003.air.service.airplane;

import ca.ulaval.glo4003.air.api.airplane.dto.AirplaneUpdateDto;
import ca.ulaval.glo4003.air.api.airplane.dto.AirplaneDto;
import ca.ulaval.glo4003.air.api.airplane.dto.AirplaneSearchResultDto;
import ca.ulaval.glo4003.air.domain.airplane.*;
import ca.ulaval.glo4003.air.domain.user.InvalidTokenException;
import ca.ulaval.glo4003.air.domain.user.UnauthorizedException;
import ca.ulaval.glo4003.air.domain.user.User;
import ca.ulaval.glo4003.air.service.user.UserService;
import ca.ulaval.glo4003.air.transfer.airplane.AirplaneAssembler;

import java.util.List;
import java.util.logging.Logger;

public class AirplaneService {

    private final Logger logger = Logger.getLogger(AirplaneService.class.getName());

    private final AirplaneRepository airplaneRepository;
    private final AirplaneAssembler airplaneAssembler;
    private final UserService userService;

    public AirplaneService(AirplaneRepository airplaneRepository, AirplaneAssembler airplaneAssembler, UserService userService) {
        this.airplaneRepository = airplaneRepository;
        this.airplaneAssembler = airplaneAssembler;
        this.userService = userService;
    }

    public AirplaneSearchResultDto findAllWithFilters(boolean needsToBeAirLourd) {

        logRequest(needsToBeAirLourd);

        List<Airplane> airplanes;
        if (needsToBeAirLourd) {
            airplanes = airplaneRepository.findAirLourdAirplanes();
        } else {
            airplanes = airplaneRepository.findAll();
        }

        AirplaneSearchResult searchResult = new AirplaneSearchResult(airplanes);
        return airplaneAssembler.createAirplaneSearchResult(searchResult);
    }

    public AirplaneDto updateAirplane(String token, String airplaneSerialNumber, AirplaneUpdateDto airplaneUpdateDto) throws InvalidTokenException, UnauthorizedException, AirplaneNotFoundException {
        User user = userService.authenticateUser(token);
        if (!user.isAdmin()) {
            throw new UnauthorizedException("The user does not have the required permissions to modify an airplane.");
        }
        Airplane airplane = airplaneRepository.find(airplaneSerialNumber).orElseThrow(() -> new AirplaneNotFoundException("Could not find the airplane with serial number " + airplaneSerialNumber));
        updateAirplane(airplane, airplaneUpdateDto);

        return airplaneAssembler.createAirplane(airplane);
    }

    private void updateAirplane(Airplane airplane, AirplaneUpdateDto airplaneUpdateDto) {
        if (airplane.isAirLourd()) {
            AirLourdAirplane airLourdAirplane = (AirLourdAirplane) airplane;
            airLourdAirplane.setAcceptedAdditionalWeight(airplaneUpdateDto.acceptedMaximumWeight);
        }
    }

    private void logRequest(boolean needsToBeAirLourd) {
        if (needsToBeAirLourd) {
            logger.info("Finding all AirLourd airplanes.");
        } else {
            logger.info("Finding all airplanes.");
        }
    }
}
