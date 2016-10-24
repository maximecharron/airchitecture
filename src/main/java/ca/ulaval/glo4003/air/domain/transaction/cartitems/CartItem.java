package ca.ulaval.glo4003.air.domain.transaction.cartitems;

import java.time.LocalDateTime;

public class CartItem {

    private int ticketsQuantity;
    private String flightNumber;
    private LocalDateTime departureDate;

    public CartItem(int ticketsQuantity, String flightNumber, LocalDateTime departureDate) {
        this.ticketsQuantity = ticketsQuantity;
        this.flightNumber = flightNumber;
        this.departureDate = departureDate;
    }

    public int getTicketsQuantity() {
        return ticketsQuantity;
    }

    public void setTicketsQuantity(int ticketsQuantity) {
        this.ticketsQuantity = ticketsQuantity;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public LocalDateTime getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(LocalDateTime departureDate) {
        this.departureDate = departureDate;
    }
}
