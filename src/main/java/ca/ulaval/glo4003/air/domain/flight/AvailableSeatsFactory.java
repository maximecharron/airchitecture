package ca.ulaval.glo4003.air.domain.flight;

import ca.ulaval.glo4003.air.domain.airplane.SeatMap;

public class AvailableSeatsFactory {

    public AvailableSeats createFromSeatMap(SeatMap seatMap) {
        return new AvailableSeats(seatMap);
    }
}
