package ca.ulaval.glo4003.air.domain.airplane;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SeatMap seatMap = (SeatMap) o;
        return economicSeats == seatMap.economicSeats &&
            regularSeats == seatMap.regularSeats &&
            businessSeats == seatMap.businessSeats;
    }

    @Override
    public int hashCode() {
        return Objects.hash(economicSeats, regularSeats, businessSeats);
    }
}
