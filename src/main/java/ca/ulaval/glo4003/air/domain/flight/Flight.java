package ca.ulaval.glo4003.air.domain.flight;

import ca.ulaval.glo4003.air.domain.airplane.Airplane;

import java.time.LocalDateTime;

public abstract class Flight {

    private final String departureAirport;
    private final String arrivalAirport;
    private final LocalDateTime departureDate;
    private final String airlineCompany;
    protected final Airplane airplane;

    public Flight(String departureAirport, String arrivalAirport, LocalDateTime departureDate, String airlineCompany, Airplane airplane) {
        this.departureAirport = departureAirport;
        this.arrivalAirport = arrivalAirport;
        this.departureDate = departureDate;
        this.airlineCompany = airlineCompany;
        this.airplane = airplane;
    }

    public boolean isDepartingFrom(String departureAirport) {
        return this.departureAirport.equals(departureAirport);
    }

    public boolean isGoingTo(String arrivalAirport) {
        return this.arrivalAirport.equals(arrivalAirport);
    }

    public boolean isFromCompany(String airlineCompany) {
        return this.airlineCompany.equals(airlineCompany);
    }

    public boolean isLeavingAfterOrOn(LocalDateTime date) {
        return departureDate.isAfter(date) || departureDate.isEqual(date);
    }

    public boolean isLeavingWithinXDaysOf(LocalDateTime date, int numberOfDays) {
        return departureDate.isBefore(date.plusDays(numberOfDays));
    }

    abstract public boolean acceptsWeight(double weight);

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

    public abstract boolean isAirCargo();

    public abstract boolean isPassengerFlight();
}
