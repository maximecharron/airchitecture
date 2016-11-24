package ca.ulaval.glo4003.air.domain.transaction.cart;

import ca.ulaval.glo4003.air.domain.airplane.SeatMap;

import java.time.LocalDateTime;

public class CartItem {

    private SeatMap seatMap;
    private double luggageWeight;
    private String arrivalAirport;
    private String airlineCompany;
    private LocalDateTime departureDate;

    public CartItem(SeatMap seatMap, String arrivalAirport, String airlineCompany, LocalDateTime departureDate, double luggageWeight) {
        this.seatMap = seatMap;
        this.arrivalAirport = arrivalAirport;
        this.airlineCompany = airlineCompany;
        this.departureDate = departureDate;
        this.luggageWeight = luggageWeight;
    }

    public SeatMap getSeatMap() {
        return seatMap;
    }

    public double getLuggageWeight() {
        return luggageWeight;
    }

    public String getArrivalAirport() {
        return arrivalAirport;
    }

    public String getAirlineCompany() {
        return airlineCompany;
    }

    public LocalDateTime getDepartureDate() {
        return departureDate;
    }
}
