package ca.ulaval.glo4003.air.domain.flight;


import ca.ulaval.glo4003.air.domain.airplane.Airplane;

import java.time.LocalDateTime;

public class AirCargoFlight extends Flight{
    public AirCargoFlight(String departureAirport, String arrivalAirport, LocalDateTime departureDate, String airlineCompany, Airplane airplane, float seatPrice) {
        super(departureAirport, arrivalAirport, departureDate, airlineCompany, airplane, seatPrice);
    }

    @Override
    public boolean acceptsWeight(double weight) {
        return true;
    }

    @Override
    public boolean isAirCargo() {
        return true;
    }

    @Override
    public boolean isPassengerFlight() {
        return false;
    }
}