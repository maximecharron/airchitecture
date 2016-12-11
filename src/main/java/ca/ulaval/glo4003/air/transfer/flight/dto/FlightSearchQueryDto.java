package ca.ulaval.glo4003.air.transfer.flight.dto;

import java.time.LocalDateTime;

public class FlightSearchQueryDto {

    public String departureAirport;
    public String arrivalAirport;
    public LocalDateTime departureDate;
    public double weight;
    public boolean onlyAirVivant;
    public boolean acceptsAirCargo;
    public boolean hasEconomySeats;
    public boolean hasRegularSeats;
    public boolean hasBusinessSeats;
}
