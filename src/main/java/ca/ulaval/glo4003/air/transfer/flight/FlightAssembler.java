package ca.ulaval.glo4003.air.transfer.flight;

import ca.ulaval.glo4003.air.api.flight.dto.FlightDto;
import ca.ulaval.glo4003.air.api.flight.dto.FlightSearchDto;
import ca.ulaval.glo4003.air.domain.flight.Flight;

import java.util.List;
import java.util.stream.Collectors;

public class FlightAssembler {
    public FlightDto create(Flight flight, double weight) {
        FlightDto flightDto = new FlightDto();
        flightDto.airlineCompany = flight.getAirlineCompany();
        flightDto.departureDate = flight.getDepartureDate();
        flightDto.departureAirport = flight.getDepartureAirport();
        flightDto.arrivalAirport = flight.getArrivalAirport();
        flightDto.availableSeats = flight.getAvailableSeats();
        flightDto.seatPrice = flight.getSeatPrice();
        flightDto.hasAdditionalWeightOption = flight.hasAdditionalWeightOption();
        flightDto.acceptsAdditionalWeight = flight.acceptsAdditionalWeight(weight);
        return flightDto;
    }

    public FlightSearchDto create(List<Flight> flights, double weight, boolean flightsWereFilteredByWeight) {
        FlightSearchDto flightSearchDto = new FlightSearchDto();
        flightSearchDto.flights = this.create(flights, weight);
        flightSearchDto.flightsWereFilteredByWeight = flightsWereFilteredByWeight;
        return flightSearchDto;
    }

    private List<FlightDto> create(List<Flight> flights, double weight) {
        return flights.stream().map(flight -> create(flight, weight)).collect(Collectors.toList());
    }
}
