package ca.ulaval.glo4003.air.api.flight.dto;

import java.time.LocalDateTime;

public class FlightDto {

    public String departureAirport;
    public String arrivalAirport;
    public LocalDateTime departureDate;
    public String airlineCompany;
    public int availableSeats;
    public float seatPrice;
    public boolean hasAdditionalWeightOption;
    public boolean acceptsAdditionalWeight;
    public FlightDto airCargoFlight;
}
