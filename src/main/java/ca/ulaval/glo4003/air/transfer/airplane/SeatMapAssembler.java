package ca.ulaval.glo4003.air.transfer.airplane;

import ca.ulaval.glo4003.air.domain.airplane.SeatMap;
import ca.ulaval.glo4003.air.transfer.airplane.dto.SeatMapDto;

public class SeatMapAssembler {

    public SeatMapDto create(SeatMap seatMap) {
        SeatMapDto seatMapDto = new SeatMapDto();
        seatMapDto.economicSeats = seatMap.getEconomicSeats();
        seatMapDto.regularSeats = seatMap.getRegularSeats();
        seatMapDto.businessSeats = seatMap.getBusinessSeats();

        return seatMapDto;
    }

    public SeatMap create(SeatMapDto seatMapDto) {
        return new SeatMap(seatMapDto.economicSeats, seatMapDto.regularSeats, seatMapDto.businessSeats);
    }
}
