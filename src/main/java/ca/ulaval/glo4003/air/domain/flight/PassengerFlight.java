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
        return this.getAvailableSeats().getEconomicSeats() > 0;
    }

    public boolean hasAvailableRegularSeats() {
        return this.getAvailableSeats().getRegularSeats() > 0;
    }

    public boolean hasAvailableBusinessSeats() {
        return this.getAvailableSeats().getBusinessSeats() > 0;
    }

    public AvailableSeats getAvailableSeats() {
        return this.availableSeats;
    }

    public SeatsPricing getSeatsPricing() {
        return seatsPricing;
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
