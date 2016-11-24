package ca.ulaval.glo4003.air.transfer.flight;

import ca.ulaval.glo4003.air.api.flight.dto.FlightDto;
import ca.ulaval.glo4003.air.api.flight.dto.FlightSearchResultDto;
import ca.ulaval.glo4003.air.domain.flight.Flight;
import ca.ulaval.glo4003.air.domain.flight.FlightSearchResult;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FlightAssembler {

    public FlightDto create(Flight flight, double weight, Flight airCargoFlight) {
        FlightDto flightDto = new FlightDto();
        flightDto.airlineCompany = flight.getAirlineCompany();
        flightDto.departureDate = flight.getDepartureDate();
        flightDto.departureAirport = flight.getDepartureAirport();
        flightDto.arrivalAirport = flight.getArrivalAirport();
        flightDto.availableSeats = flight.getAvailableSeats();
        flightDto.seatPrice = flight.getSeatPrice();
        flightDto.hasAdditionalWeightOption = flight.hasAdditionalWeightOption();
        flightDto.acceptsAdditionalWeight = flight.acceptsAdditionalWeight(weight);
        flightDto.airCargoFlight = create;
        return flightDto;
    }

    public FlightSearchResultDto create(FlightSearchResult flightSearchResult) {
        FlightSearchResultDto flightSearchResultDto = new FlightSearchResultDto();
        flightSearchResultDto.flights = this.create(flightSearchResult.getFlightsFilteredByWeight(), flightSearchResult.getWeight(), flightSearchResult.getFlightsWithAirCargo());
        flightSearchResultDto.flightsWereFilteredByWeight = flightSearchResult.isFlightsWereFilteredByWeight();
        return flightSearchResultDto;
    }

    private List<FlightDto> create(List<Flight> flights, double weight, Map<Flight, Flight> flightsWithAirCargo) {
        return flights.stream().map(flight -> create(flight, weight, flightsWithAirCargo.get(flight))).collect(Collectors.toList());
    }
}
