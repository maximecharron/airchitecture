package ca.ulaval.glo4003.air.domain.flight;

import ca.ulaval.glo4003.air.domain.airplane.Airplane;
import ca.ulaval.glo4003.air.domain.airplane.SeatMap;

import java.time.LocalDateTime;


public class PassengerFlight extends Flight {

    private final AvailableSeats availableSeats;
    private final SeatsPricing seatsPricing;

    public PassengerFlight(String departureAirport, String arrivalAirport, LocalDateTime departureDate, String airlineCompany, Airplane airplane, SeatsPricing seatsPricing, AvailableSeatsFactory availableSeatsFactory) {
        super(departureAirport, arrivalAirport, departureDate, airlineCompany, airplane);
        this.seatsPricing = seatsPricing;
        this.availableSeats = availableSeatsFactory.createFromSeatMap(airplane.getSeatMap());
    }

    public boolean hasAvailableEconomySeats() {
        return this.availableSeats.hasEconomicSeats();
    }

    public boolean hasAvailableRegularSeats() {
        return this.availableSeats.hasRegularSeats();
    }

    public boolean hasAvailableBusinessSeats() {
        return this.availableSeats.hasRegularSeats();
    }

    public AvailableSeats getAvailableSeats() {
        return this.availableSeats;
    }

    public int totalSeatsLeft() {
        return availableSeats.totalSeatsLeft();
    }

    public SeatsPricing getSeatsPricing() {
        return seatsPricing;
    }

    public double getEconomicSeatsPrice() {
        return seatsPricing.getEconomicSeatsPrice();
    }

    public double getRegularSeatsPrice() {
        return seatsPricing.getRegularSeatsPrice();
    }

    public double getBusinessSeatsPrice() {
        return seatsPricing.getBusinessSeatsPrice();
    }

    public void reserveSeats(SeatMap seatMap) {
        availableSeats.reserve(seatMap);
    }

    public void releaseSeats(SeatMap seatMap) {
        availableSeats.release(seatMap);
    }

    @Override
    public boolean isAirCargo() {
        return false;
    }

    @Override
    public boolean isPassengerFlight() {
        return true;
    }
}
