package ca.ulaval.glo4003.air.transfer.airplane;

import ca.ulaval.glo4003.air.api.airplane.dto.AirplaneDto;
import ca.ulaval.glo4003.air.api.airplane.dto.AirplaneSearchResultDto;
import ca.ulaval.glo4003.air.api.flight.dto.FlightDto;
import ca.ulaval.glo4003.air.api.flight.dto.FlightSearchResultDto;
import ca.ulaval.glo4003.air.domain.airplane.AirLourdAirplane;
import ca.ulaval.glo4003.air.domain.airplane.Airplane;
import ca.ulaval.glo4003.air.domain.airplane.AirplaneSearchResult;
import ca.ulaval.glo4003.air.domain.flight.Flight;
import ca.ulaval.glo4003.air.domain.flight.FlightSearchResult;

import java.util.List;
import java.util.stream.Collectors;

public class AirplaneAssembler {

    public AirplaneSearchResultDto createAirplaneSearchResult(AirplaneSearchResult airplaneSearchResult) {
        AirplaneSearchResultDto airplaneSearchResultDto = new AirplaneSearchResultDto();
        airplaneSearchResultDto.airplanes = airplaneSearchResult.getAirplanes().stream().map(this::createAirplane).collect(Collectors.toList());
        return airplaneSearchResultDto;
    }

    public AirplaneDto createAirplane(Airplane airplane) {
        AirplaneDto airplaneDto = new AirplaneDto();
        airplaneDto.availableSeats = airplane.getAvailableSeats();
        airplaneDto.maximumWeight = airplane.getMaximumWeight();
        airplaneDto.serialNumber = airplane.getSerialNumber();

        if (airplane.isAirLourd()) {
            airplaneDto.isAirLourd = true;
            airplaneDto.acceptedAdditionalWeight = ((AirLourdAirplane) airplane).getAcceptedAdditionalWeight();
        }

        return airplaneDto;
    }
}
