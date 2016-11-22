package ca.ulaval.glo4003.air.domain.airplane;

public class SeatMap {

    private final int economicSeats;
    private final int regularSeats;
    private final int businessSeats;

    public SeatMap(int economicSeats, int regularSeats, int businessSeats) {
        this.economicSeats = economicSeats;
        this.regularSeats = regularSeats;
        this.businessSeats = businessSeats;
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
