package ca.ulaval.glo4003.air.domain.flight;

import java.time.LocalDateTime;

public class Flight {

    private String flightNumber;
    private String departureAirport;
    private String arrivalAirport;
    private LocalDateTime departureDate;
    private String airlineCompany;
    private int availableSeats;

    public boolean isDepartingFrom(String departureAirport) {
        return this.departureAirport.equals(departureAirport);
    }

    public boolean isGoingTo(String arrivalAirport) {
        return this.arrivalAirport.equals(arrivalAirport);
    }

    public boolean isLeavingOn(LocalDateTime date) {
        return departureDate.isEqual(date);
    }

    public boolean isFuture() {
        return departureDate.isAfter(LocalDateTime.now());
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getDepartureAirport() {
        return departureAirport;
    }

    public void setDepartureAirport(String departureAirport) {
        this.departureAirport = departureAirport;
    }

    public String getArrivalAirport() {
        return arrivalAirport;
    }

    public void setArrivalAirport(String arrivalAirport) {
        this.arrivalAirport = arrivalAirport;
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

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }
}