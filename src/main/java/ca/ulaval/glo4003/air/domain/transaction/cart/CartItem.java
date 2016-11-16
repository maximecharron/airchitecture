package ca.ulaval.glo4003.air.domain.transaction.cart;

import java.time.LocalDateTime;

public class CartItem {

    private int ticketsQuantity;
    private double luggageWeight;
    private double ticketsPrice;
    private String arrivalAirport;
    private String airlineCompany;
    private LocalDateTime departureDate;

    public CartItem(int ticketsQuantity, String arrivalAirport, String airlineCompany, LocalDateTime departureDate, double luggageWeight, double ticketsPrice) {
        this.ticketsQuantity = ticketsQuantity;
        this.arrivalAirport = arrivalAirport;
        this.airlineCompany = airlineCompany;
        this.departureDate = departureDate;
        this.luggageWeight = luggageWeight;
        this.ticketsPrice = ticketsPrice;
    }

    public int getTicketsQuantity() {
        return ticketsQuantity;
    }

    public LocalDateTime getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(LocalDateTime departureDate) {
        this.departureDate = departureDate;
    }

    public String getAirlineCompany() {
        return airlineCompany;
    }

    public void setAirlineCompany(String airlineCompany) {
        this.airlineCompany = airlineCompany;
    }

    public String getArrivalAirport() {
        return arrivalAirport;
    }

    public void setArrivalAirport(String arrivalAirport) {
        this.arrivalAirport = arrivalAirport;
    }

    public void setTicketsQuantity(int ticketsQuantity) {
        this.ticketsQuantity = ticketsQuantity;
    }

    public double getLuggageWeight() {
        return luggageWeight;
    }

    public void setLuggageWeight(double luggageWeight) {
        this.luggageWeight = luggageWeight;
    }

    public double getTicketsPrice() {
        return ticketsPrice;
    }

    public void setTicketsPrice(double ticketsPrice) {
        this.ticketsPrice = ticketsPrice;
    }
}
