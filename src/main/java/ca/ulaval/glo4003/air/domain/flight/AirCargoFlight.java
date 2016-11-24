package ca.ulaval.glo4003.air.domain.flight;


import ca.ulaval.glo4003.air.domain.airplane.Airplane;

import java.time.LocalDateTime;

public class AirCargoFlight extends Flight{

    private double price;

    public AirCargoFlight(String departureAirport, String arrivalAirport, LocalDateTime departureDate, String airlineCompany, Airplane airplane, double price) {
        super(departureAirport, arrivalAirport, departureDate, airlineCompany, airplane);
        this.price = price;
    }



    @Override
    public boolean isAirCargo() {
        return true;
    }

    @Override
    public boolean isPassengerFlight() {
        return false;
    }

    public double getPrice() {
        return price;
    }
}