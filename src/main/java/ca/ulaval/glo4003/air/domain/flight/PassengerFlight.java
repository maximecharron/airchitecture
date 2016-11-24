package ca.ulaval.glo4003.air.domain.flight;

import ca.ulaval.glo4003.air.domain.airplane.Airplane;

import java.time.LocalDateTime;

/**
 * Created by bishop on 2016-11-23.
 */
public class PassengerFlight extends Flight {
    public PassengerFlight(String departureAirport, String arrivalAirport, LocalDateTime departureDate, String airlineCompany, Airplane airplane, float seatPrice) {
        super(departureAirport, arrivalAirport, departureDate, airlineCompany, airplane, seatPrice);
    }

    public void reservePlaces(int ticketsQuantity) {
        availableSeats = availableSeats - ticketsQuantity;
    }

    public void releasePlaces(int ticketsQuantity) {
        availableSeats = availableSeats + ticketsQuantity;
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
