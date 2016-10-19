package ca.ulaval.glo4003.air.domain.flight;

import ca.ulaval.glo4003.air.domain.airplane.Airplane;

import java.time.LocalDateTime;

public class Flight {

    private final String flightNumber;
    private final String departureAirport;
    private final String arrivalAirport;
    private final LocalDateTime departureDate;
    private final String airlineCompany;
    private final Airplane airplane;

    public Flight(String flightNumber, String departureAirport, String arrivalAirport, LocalDateTime departureDate, String airlineCompany, Airplane airplane) {
        this.flightNumber = flightNumber;
        this.departureAirport = departureAirport;
        this.arrivalAirport = arrivalAirport;
        this.departureDate = departureDate;
        this.airlineCompany = airlineCompany;
        this.airplane = airplane;
    }

    public boolean isDepartingFrom(String departureAirport) {
        return this.departureAirport != null && this.departureAirport.equals(departureAirport);
    }

    public boolean isGoingTo(String arrivalAirport) {
        return this.arrivalAirport != null && this.arrivalAirport.equals(arrivalAirport);
    }

    public boolean isLeavingOn(LocalDateTime date) {
        return departureDate != null && (departureDate.isAfter(date) || departureDate.isEqual(date));
    }

    public boolean isFuture() {
        return departureDate != null && departureDate.isAfter(LocalDateTime.now());
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public String getDepartureAirport() {
        return departureAirport;
    }

    public String getArrivalAirport() {
        return arrivalAirport;
    }

    public LocalDateTime getDepartureDate() {
        return departureDate;
    }

    public String getAirlineCompany() {
        return airlineCompany;
    }

    public int getAvailableSeats() {
        return airplane.getAvailableSeats();
    }

    public boolean acceptsWeight(double weight) {
        return airplane.acceptsWeight(weight);
    }

    public boolean acceptsAdditionalWeight(double weight) {
        return airplane.acceptsAdditionalWeight(weight);
    }
}