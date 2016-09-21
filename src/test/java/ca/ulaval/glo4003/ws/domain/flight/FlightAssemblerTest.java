package ca.ulaval.glo4003.ws.domain.flight;

import ca.ulaval.glo4003.ws.api.flight.dto.FlightDto;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;


public class FlightAssemblerTest {

    private static final String FLIGHT_NUMBER = "AF215";
    private static final int SEATS = 42;
    private static final String ARRIVAL_AIRPORT = "ABC";
    private static final String DEPARTURE_AIRPORT = "DEF";
    private static final LocalDateTime DATE = LocalDateTime.of(2020, 10, 2, 6, 30);
    private static final String AIRLINE_COMPANY = "AirFrenette";

    private FlightAssembler flightAssembler;

    @Before
    public void setup() {
        flightAssembler = new FlightAssembler();
    }

    @Test
    public void givenAFlight_whenCreatingAFlightDto_thenItHasAllTheRelevantProperties() {
        Flight flight = givenAFlight();

        FlightDto flightDto = flightAssembler.create(flight);

        assertHasAllTheRelevantProperties(flight, flightDto);
    }

    @Test
    public void givenAFlightDto_whenCreatingAFlight_thenItHasAllTheRelevantProperties() {
        FlightDto flightDto = givenAFlightDto();

        Flight flight = flightAssembler.create(flightDto);

        assertHasAllTheRelevantProperties(flight, flightDto);
    }

    private Flight givenAFlight() {
        Flight flight = new Flight();
        flight.setFlightNumber(FLIGHT_NUMBER);
        flight.setAirlineCompany(AIRLINE_COMPANY);
        flight.setAvailableSeats(SEATS);
        flight.setDepartureAirport(DEPARTURE_AIRPORT);
        flight.setArrivalAirport(ARRIVAL_AIRPORT);
        flight.setDepartureDate(DATE);
        return flight;
    }

    private FlightDto givenAFlightDto() {
        FlightDto flightDto = new FlightDto();
        flightDto.flightNumber = FLIGHT_NUMBER;
        flightDto.availableSeats = SEATS;
        flightDto.arrivalAirport = ARRIVAL_AIRPORT;
        flightDto.departureAirport = DEPARTURE_AIRPORT;
        flightDto.departureDate = DATE;
        flightDto.airlineCompany = AIRLINE_COMPANY;
        return flightDto;
    }

    private void assertHasAllTheRelevantProperties(Flight flight, FlightDto flightDto) {
        assertEquals(flight.getFlightNumber(), flightDto.flightNumber);
        assertEquals(flight.getAirlineCompany(), flightDto.airlineCompany);
        assertEquals(flight.getAvailableSeats(), flightDto.availableSeats);
        assertEquals(flight.getDepartureAirport(), flightDto.departureAirport);
        assertEquals(flight.getArrivalAirport(), flightDto.arrivalAirport);
        assertEquals(flight.getDepartureDate(), flightDto.departureDate);
    }
}