package ca.ulaval.glo4003.air.domain.flight;

import ca.ulaval.glo4003.air.api.flight.dto.FlightDto;
import ca.ulaval.glo4003.air.api.flight.dto.FlightSearchDto;
import ca.ulaval.glo4003.air.domain.airplane.Airplane;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FlightAssembler {

    private List<FlightDto> create(Stream<Flight> flights, double weight) {
        return flights.map(flight -> create(flight, weight)).collect(Collectors.toList());
    }

    public FlightDto create(Flight flight, double weight) {
        FlightDto flightDto = new FlightDto();
        flightDto.flightNumber = flight.getFlightNumber();
        flightDto.airlineCompany = flight.getAirlineCompany();
        flightDto.departureDate = flight.getDepartureDate();
        flightDto.departureAirport = flight.getDepartureAirport();
        flightDto.arrivalAirport = flight.getArrivalAirport();
        flightDto.availableSeats = flight.getAvailableSeats();
        flightDto.acceptsAdditionalWeight = flight.acceptsAdditionalWeight(weight);
        return flightDto;
    }

    public FlightSearchDto create(Stream<Flight> flights, double weight, boolean flightsWereFilteredByWeight) {
        FlightSearchDto flightSearchDto = new FlightSearchDto();
        flightSearchDto.flights = this.create(flights, weight);
        flightSearchDto.flightsWereFilteredByWeight = flightsWereFilteredByWeight;
        return flightSearchDto;
    }
}
