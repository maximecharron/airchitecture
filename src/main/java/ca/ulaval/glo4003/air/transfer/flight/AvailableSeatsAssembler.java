package ca.ulaval.glo4003.air.transfer.flight;

import ca.ulaval.glo4003.air.transfer.flight.dto.AvailableSeatsDto;
import ca.ulaval.glo4003.air.domain.airplane.SeatMap;
import ca.ulaval.glo4003.air.domain.flight.AvailableSeats;

public class AvailableSeatsAssembler {

    public AvailableSeatsDto create(AvailableSeats availableSeats) {
        AvailableSeatsDto availableSeatsDto = new AvailableSeatsDto();
        availableSeatsDto.economicSeats = availableSeats.getEconomicSeats();
        availableSeatsDto.regularSeats = availableSeats.getRegularSeats();
        availableSeatsDto.businessSeats = availableSeats.getBusinessSeats();

        return availableSeatsDto;
    }

    public AvailableSeats create(AvailableSeatsDto availableSeatsDto) {
        SeatMap seatMap = new SeatMap(availableSeatsDto.economicSeats, availableSeatsDto.regularSeats, availableSeatsDto.businessSeats);
        return new AvailableSeats(seatMap);
    }
}
