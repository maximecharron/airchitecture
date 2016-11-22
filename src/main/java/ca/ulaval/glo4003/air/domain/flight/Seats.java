package ca.ulaval.glo4003.air.domain.flight;

import ca.ulaval.glo4003.air.domain.airplane.SeatMap;

public class Seats {

    private int economicSeats;
    private final double economicSeatsPrice;
    private int regularSeats;
    private final double regulatSeatsPrice;
    private int businessSeats;
    private final double businessSeatsPrice;

    public Seats(SeatMap seatMap, SeatsPricing seatsPricing) {
        this.economicSeats = seatMap.getEconomicSeats();
        this.economicSeatsPrice = seatsPricing.getEconomicSeatsPrice();
        this.regularSeats = seatMap.getRegularSeats();
        this.regulatSeatsPrice = seatsPricing.getRegularSeatsPrice();
        this.businessSeats = seatMap.getBusinessSeats();
        this.businessSeatsPrice = seatsPricing.getBusinessSeatsPrice();
    }
}
