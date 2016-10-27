package ca.ulaval.glo4003.air.domain.transaction;

import java.time.LocalDateTime;

public class CartItem {

    private int ticketsQuantity;
    private String arrivalAirport;
    private String airlineCompany;
    private LocalDateTime departureDate;

    public CartItem(int ticketsQuantity, String arrivalAirport, String airlineCompany, LocalDateTime departureDate) {
        this.ticketsQuantity = ticketsQuantity;
        this.arrivalAirport = arrivalAirport;
        this.airlineCompany = airlineCompany;
        this.departureDate = departureDate;
    }

    public int getTicketsQuantity() {
        return ticketsQuantity;
    }

    public void setTicketsQuantity(int ticketsQuantity) {
        this.ticketsQuantity = ticketsQuantity;
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
}
