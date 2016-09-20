package ca.ulaval.glo4003.ws.domain.flight;

import ca.ulaval.glo4003.ws.api.flight.dto.FlightDto;

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
}
