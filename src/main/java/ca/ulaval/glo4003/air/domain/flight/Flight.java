package ca.ulaval.glo4003.air.domain.flight;

import ca.ulaval.glo4003.air.domain.airplane.Airplane;

import java.time.LocalDateTime;

public abstract class Flight {

    private final String departureAirport;
    private final String arrivalAirport;
    private final LocalDateTime departureDate;
    private final String airlineCompany;
    private final Airplane airplane;
    private int availableSeats;
    private float seatPrice;

    public Flight(String departureAirport, String arrivalAirport, LocalDateTime departureDate, String airlineCompany, Airplane airplane, float seatPrice) {
        this.departureAirport = departureAirport;
        this.arrivalAirport = arrivalAirport;
        this.departureDate = departureDate;
        this.airlineCompany = airlineCompany;
        this.airplane = airplane;
        this.availableSeats = this.airplane.getAvailableSeats();
        this.seatPrice = seatPrice;
    }

    public boolean isDepartingFrom(String departureAirport) {
        return this.departureAirport.equals(departureAirport);
    }

    public boolean isGoingTo(String arrivalAirport) {
        return this.arrivalAirport.equals(arrivalAirport);
    }

    public boolean isLeavingOn(LocalDateTime date) {
        return isOnSameDay(date, departureDate);
    }

    public boolean isFromCompany(String airlineCompany) {
        return this.airlineCompany.equals(airlineCompany);
    }

    public boolean isLeavingAfter(LocalDateTime date) {
        return departureDate.isAfter(date);
    }

    public boolean isLeavingWithinXDaysOf(LocalDateTime date, int numberOfDays) {
        return departureDate.isBefore(date.plusDays(numberOfDays));
    }

    public boolean acceptsWeight(double weight) {
        return airplane.acceptsWeight(weight);
    }

    public boolean hasAdditionalWeightOption() {
        return this.airplane.hasAdditionalWeightOption();
    }

    public boolean acceptsAdditionalWeight(double weight) {
        return airplane.acceptsAdditionalWeight(weight);
    }

    public boolean isAirVivant() {
        return airplane.isAirVivant();
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
        return this.availableSeats;
    }

    public float getSeatPrice() {
        return seatPrice;
    }

    public abstract boolean isAirCargo();

    public abstract boolean isPassengerFlight();

    private boolean isOnSameDay(LocalDateTime date1, LocalDateTime date2) {
        return date1.getDayOfYear() == date2.getDayOfYear() && date1.getYear() == date2.getYear();
    }
}
