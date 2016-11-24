package ca.ulaval.glo4003.air.domain.flight;

import ca.ulaval.glo4003.air.domain.airplane.Airplane;
import ca.ulaval.glo4003.air.domain.airplane.SeatMap;

import java.time.LocalDateTime;

public class Flight {

    private final String departureAirport;
    private final String arrivalAirport;
    private final LocalDateTime departureDate;
    private final String airlineCompany;
    private final Airplane airplane;
    private final AvailableSeats availableSeats;
    private final SeatsPricing seatsPricing;

    public Flight(String departureAirport, String arrivalAirport, LocalDateTime departureDate, String airlineCompany, Airplane airplane, SeatsPricing seatsPricing, AvailableSeatsFactory availableSeatsFactory) {
        this.departureAirport = departureAirport;
        this.arrivalAirport = arrivalAirport;
        this.departureDate = departureDate;
        this.airlineCompany = airlineCompany;
        this.airplane = airplane;
        this.seatsPricing = seatsPricing;
        this.availableSeats = availableSeatsFactory.createFromSeatMap(airplane.getSeatMap());
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

    public void reserveSeats(SeatMap seatMap) {
        availableSeats.reserve(seatMap);
    }

    public void releaseSeats(SeatMap seatMap) {
        availableSeats.release(seatMap);
    }

    private boolean isOnSameDay(LocalDateTime date1, LocalDateTime date2) {
        return date1.getDayOfYear() == date2.getDayOfYear() && date1.getYear() == date2.getYear();
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

    public AvailableSeats getAvailableSeats() {
        return this.availableSeats;
    }

    public SeatsPricing getSeatsPricing() {
        return seatsPricing;
    }
}
