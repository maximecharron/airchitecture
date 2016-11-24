package ca.ulaval.glo4003.air.api.flight.dto;

import java.time.LocalDateTime;

public class FlightDto {

    public String departureAirport;
    public String arrivalAirport;
    public LocalDateTime departureDate;
    public String airlineCompany;
    public AvailableSeatsDto availableSeatsDto;
    public SeatsPricingDto seatsPricingDto;
    public boolean hasAdditionalWeightOption;
    public boolean acceptsAdditionalWeight;
}
