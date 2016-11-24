package ca.ulaval.glo4003.air.api.transaction.dto;

import ca.ulaval.glo4003.air.api.airplane.dto.SeatMapDto;
import ca.ulaval.glo4003.air.api.flight.dto.AirCargoFlightDto;

public class CartItemDto {

    public SeatMapDto seatMapDto;
    public double luggageWeight;
    public String arrivalAirport;
    public String airlineCompany;
    public String departureDate;
    public String airCargoDepartureDate;
    public String airCargoAirLineCompany;
}
