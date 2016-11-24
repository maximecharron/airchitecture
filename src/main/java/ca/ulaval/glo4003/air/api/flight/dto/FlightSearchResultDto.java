package ca.ulaval.glo4003.air.api.flight.dto;

import java.util.List;

public class FlightSearchResultDto {

    public List<PassengerFlightDto> flights;
    public boolean flightsWereFilteredByWeight;
}
