package ca.ulaval.glo4003.air.transfer.airplane.dto;

public class SeatMapDto {

    public int economicSeats;
    public int regularSeats;
    public int businessSeats;

    public SeatMapDto() {
    }

    public SeatMapDto(int economicSeats, int regularSeats, int businessSeats) {
        this.economicSeats = economicSeats;
        this.regularSeats = regularSeats;
        this.businessSeats = businessSeats;
    }
}
