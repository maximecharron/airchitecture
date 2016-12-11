package ca.ulaval.glo4003.air.transfer.flight.dto;

import java.time.LocalDateTime;

public class PassengerFlightDto {

    public String departureAirport;
    public String arrivalAirport;
    public LocalDateTime departureDate;
    public String airlineCompany;
    public AvailableSeatsDto availableSeatsDto;
    public SeatsPricingDto seatsPricingDto;
    public boolean hasAdditionalWeightOption;
    public boolean acceptsAdditionalWeight;
    public AirCargoFlightDto airCargoFlight;
}
