package ca.ulaval.glo4003.air.domain.flight;

import ca.ulaval.glo4003.air.domain.airplane.SeatMap;

public class AvailableSeats {

    private int economicSeats;
    private int regularSeats;
    private int businessSeats;

    public AvailableSeats(SeatMap seatMap) {
        this.economicSeats = seatMap.getEconomicSeats();
        this.regularSeats = seatMap.getRegularSeats();
        this.businessSeats = seatMap.getBusinessSeats();
    }

    public void reserve(SeatMap seatMap) {
        this.economicSeats -= seatMap.getEconomicSeats();
        this.regularSeats -= seatMap.getRegularSeats();
        this.businessSeats -= seatMap.getBusinessSeats();
    }

    public void release(SeatMap seatMap) {
        this.economicSeats += seatMap.getEconomicSeats();
        this.regularSeats += seatMap.getRegularSeats();
        this.businessSeats += seatMap.getBusinessSeats();
    }

    public int getEconomicSeats() {
        return economicSeats;
    }

    public int getRegularSeats() {
        return regularSeats;
    }

    public int getBusinessSeats() {
        return businessSeats;
    }
}
