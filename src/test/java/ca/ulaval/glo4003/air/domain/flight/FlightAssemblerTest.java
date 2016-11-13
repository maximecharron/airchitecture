package ca.ulaval.glo4003.air.domain.flight;

import ca.ulaval.glo4003.air.api.flight.dto.FlightDto;
import ca.ulaval.glo4003.air.api.flight.dto.FlightSearchResultDto;
import ca.ulaval.glo4003.air.domain.flight.airplane.Airplane;
import ca.ulaval.glo4003.air.transfer.flight.FlightAssembler;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyDouble;

@RunWith(MockitoJUnitRunner.class)
public class FlightAssemblerTest {

    private static final String FLIGHT_NUMBER = "AF215";
    private static final int SEATS = 42;
    private static final String ARRIVAL_AIRPORT = "ABC";
    private static final String DEPARTURE_AIRPORT = "DEF";
    private static final LocalDateTime DATE = LocalDateTime.of(2020, 10, 2, 6, 30);
    private static final float A_PRICE = 124f;
    private static final double WEIGHT = 30.0;
    private static final boolean A_FILTERED_BY_WEIGHT_RESULT = true;
    private static final String AIRLINE_COMPANY = "AirFrenette";

    @Mock
    private Airplane airplane;
    private FlightAssembler flightAssembler;

    @Before
    public void setup() {
        given(airplane.hasAdditionalWeightOption()).willReturn(true);
        given(airplane.acceptsWeight(anyDouble())).willReturn(true);
        given(airplane.acceptsAdditionalWeight(anyDouble())).willReturn(true);
        given(airplane.getAvailableSeats()).willReturn(SEATS);
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
        List<Flight> flights = Stream.of(flight).collect(Collectors.toList());
        FlightSearchResult flightSearchResult = new FlightSearchResult(flights, WEIGHT, A_FILTERED_BY_WEIGHT_RESULT);

        FlightSearchResultDto flightSearchResultDto = flightAssembler.create(flightSearchResult);

        assertHasAllTheRelevantProperties(flight, flightSearchResultDto.flights.get(0));
    }

    @Test
    public void givenFlightFilteredByWeightResult_whenCreatingAFlightSearchDtoWithThisResult_thenItHasTheSameFlightFilteredByWeightResult() {
        Flight flight = givenAFlight();
        List<Flight> flights = Stream.of(flight).collect(Collectors.toList());
        FlightSearchResult flightSearchResult = new FlightSearchResult(flights, WEIGHT, A_FILTERED_BY_WEIGHT_RESULT);

        FlightSearchResultDto flightSearchResultDto = flightAssembler.create(flightSearchResult);

        assertEquals(A_FILTERED_BY_WEIGHT_RESULT, flightSearchResultDto.flightsWereFilteredByWeight);
    }

    private Flight givenAFlight() {
        return new Flight(DEPARTURE_AIRPORT, ARRIVAL_AIRPORT, DATE, AIRLINE_COMPANY, airplane, A_PRICE);
    }

    private void assertHasAllTheRelevantProperties(Flight flight, FlightDto flightDto) {
        assertEquals(flight.getAirlineCompany(), flightDto.airlineCompany);
        assertEquals(flight.getAvailableSeats(), flightDto.availableSeats);
        assertEquals(flight.getDepartureAirport(), flightDto.departureAirport);
        assertEquals(flight.getArrivalAirport(), flightDto.arrivalAirport);
        assertEquals(flight.getDepartureDate(), flightDto.departureDate);
        assertEquals(flight.hasAdditionalWeightOption(), flightDto.hasAdditionalWeightOption);
        assertEquals(flight.acceptsAdditionalWeight(WEIGHT), flightDto.acceptsAdditionalWeight);
    }
}