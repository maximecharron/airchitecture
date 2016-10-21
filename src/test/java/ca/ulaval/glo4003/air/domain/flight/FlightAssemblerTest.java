package ca.ulaval.glo4003.air.domain.flight;

import ca.ulaval.glo4003.air.api.flight.dto.FlightDto;
import ca.ulaval.glo4003.air.api.flight.dto.FlightSearchDto;
import ca.ulaval.glo4003.air.domain.airplane.Airplane;
import ca.ulaval.glo4003.air.domain.airplane.AirplaneWeightType;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;


public class FlightAssemblerTest {

    private static final String FLIGHT_NUMBER = "AF215";
    private static final int SEATS = 42;
    private static final String ARRIVAL_AIRPORT = "ABC";
    private static final String DEPARTURE_AIRPORT = "DEF";
    private static final LocalDateTime DATE = LocalDateTime.of(2020, 10, 2, 6, 30);
    private static final double WEIGHT = 30.0;
    private static final boolean A_FILTERED_BY_WEIGHT_RESULT = true;
    private static final String AIRLINE_COMPANY = "AirFrenette";
    private static final AirplaneWeightType A_WEIGHT_TYPE = AirplaneWeightType.AirLeger;

    private FlightAssembler flightAssembler;

    @Before
    public void setup() {
        flightAssembler = new FlightAssembler();
    }

    @Test
    public void givenAFlight_whenCreatingAFlightDto_thenItHasAllTheRelevantProperties() {
        Flight flight = givenAFlight();

        FlightDto flightDto = flightAssembler.create(flight, WEIGHT);

        assertHasAllTheRelevantProperties(flight, flightDto);
    }

    @Test
    public void givenFlights_whenCreatingAFlightSearchDto_thenFlightsAreMappedToTheirEquivalentDto() {
        Flight flight = givenAFlight();
        Stream<Flight> flightStream = Stream.of(flight);

        FlightSearchDto flightSearchDto = flightAssembler.create(flightStream, WEIGHT, A_FILTERED_BY_WEIGHT_RESULT);

        assertHasAllTheRelevantProperties(flight, flightSearchDto.flights.get(0));
    }

    @Test
    public void givenFlightFilteredByWeightResult_whenCreatingAFlightSearchDtoWithThisResult_thenItHasTheSameFlightFilteredByWeightResult() {
        Flight flight = givenAFlight();
        Stream<Flight> flightStream = Stream.of(flight);

        FlightSearchDto flightSearchDto = flightAssembler.create(flightStream, WEIGHT, A_FILTERED_BY_WEIGHT_RESULT);

        assertEquals(A_FILTERED_BY_WEIGHT_RESULT, flightSearchDto.flightsWereFilteredByWeight);
    }

    private Flight givenAFlight() {
        Airplane airplane = new Airplane(SEATS, A_WEIGHT_TYPE);
        Flight flight = new Flight(FLIGHT_NUMBER, DEPARTURE_AIRPORT, ARRIVAL_AIRPORT, DATE, AIRLINE_COMPANY, airplane);
        return flight;
    }

    private void assertHasAllTheRelevantProperties(Flight flight, FlightDto flightDto) {
        assertEquals(flight.getFlightNumber(), flightDto.flightNumber);
        assertEquals(flight.getAirlineCompany(), flightDto.airlineCompany);
        assertEquals(flight.getAvailableSeats(), flightDto.availableSeats);
        assertEquals(flight.getDepartureAirport(), flightDto.departureAirport);
        assertEquals(flight.getArrivalAirport(), flightDto.arrivalAirport);
        assertEquals(flight.getDepartureDate(), flightDto.departureDate);
        assertEquals(flight.acceptsAdditionalWeight(WEIGHT), flightDto.acceptsAdditionalWeight);
    }
}