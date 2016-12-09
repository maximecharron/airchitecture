package ca.ulaval.glo4003.air.transfer.airplane;

import ca.ulaval.glo4003.air.transfer.airplane.dto.AirplaneDto;
import ca.ulaval.glo4003.air.transfer.airplane.dto.AirplaneSearchResultDto;
import ca.ulaval.glo4003.air.domain.airplane.AirLourdAirplane;
import ca.ulaval.glo4003.air.domain.airplane.Airplane;
import ca.ulaval.glo4003.air.domain.airplane.AirplaneSearchResult;

import java.util.stream.Collectors;

public class AirplaneAssembler {

    private final SeatMapAssembler seatMapAssembler;

    public AirplaneAssembler(SeatMapAssembler seatMapAssembler) {
        this.seatMapAssembler = seatMapAssembler;
    }

    public AirplaneSearchResultDto createAirplaneSearchResult(AirplaneSearchResult airplaneSearchResult) {
        AirplaneSearchResultDto airplaneSearchResultDto = new AirplaneSearchResultDto();
        airplaneSearchResultDto.airplanes = airplaneSearchResult.getAirplanes().stream().map(this::createAirplane).collect(Collectors.toList());
        return airplaneSearchResultDto;
    }

    public AirplaneDto createAirplane(Airplane airplane) {
        AirplaneDto airplaneDto = new AirplaneDto();
        airplaneDto.seatMapDto = seatMapAssembler.create(airplane.getSeatMap());
        airplaneDto.maximumWeight = airplane.getMaximumWeight();
        airplaneDto.serialNumber = airplane.getSerialNumber();

        if (airplane.isAirLourd()) {
            airplaneDto.isAirLourd = true;
            airplaneDto.acceptedAdditionalWeight = ((AirLourdAirplane) airplane).getAcceptedAdditionalWeight();
        }

        return airplaneDto;
    }
}
