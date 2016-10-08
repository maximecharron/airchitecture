package ca.ulaval.glo4003.air.api.flight.dto;

import java.time.LocalDateTime;

public class FlightDto {

    public String flightNumber;
    public String departureAirport;
    public String arrivalAirport;
    public LocalDateTime departureDate;
    public String airlineCompany;
    public int availableSeats;
}
