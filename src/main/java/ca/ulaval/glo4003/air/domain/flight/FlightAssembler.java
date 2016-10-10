package ca.ulaval.glo4003.air.domain.flight;

import ca.ulaval.glo4003.air.api.flight.dto.FlightDto;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FlightAssembler {

    public FlightDto create(Flight flight) {
        FlightDto flightDto = new FlightDto();
        flightDto.flightNumber = flight.getFlightNumber();
        flightDto.airlineCompany = flight.getAirlineCompany();
        flightDto.departureDate = flight.getDepartureDate();
        flightDto.departureAirport = flight.getDepartureAirport();
        flightDto.arrivalAirport = flight.getArrivalAirport();
        flightDto.availableSeats = flight.getAvailableSeats();
        return flightDto;
    }

    public Flight create(FlightDto flightDto) {
        Flight flight = new Flight();
        flight.setFlightNumber(flightDto.flightNumber);
        flight.setAirlineCompany(flightDto.airlineCompany);
        flight.setDepartureDate(flightDto.departureDate);
        flight.setDepartureAirport(flightDto.departureAirport);
        flight.setArrivalAirport(flightDto.arrivalAirport);
        flight.setAvailableSeats(flightDto.availableSeats);
        return flight;
    }

    public List<FlightDto> create(Stream<Flight> flights) {
        return flights.map(this::create).collect(Collectors.toList());
    }
}
