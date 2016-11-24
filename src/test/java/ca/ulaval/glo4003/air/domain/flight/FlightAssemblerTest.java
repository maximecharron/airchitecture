package ca.ulaval.glo4003.air.domain.flight;

import ca.ulaval.glo4003.air.api.flight.dto.FlightDto;
import ca.ulaval.glo4003.air.api.flight.dto.FlightSearchResultDto;
import ca.ulaval.glo4003.air.domain.airplane.Airplane;
import ca.ulaval.glo4003.air.domain.airplane.SeatMap;
import ca.ulaval.glo4003.air.transfer.flight.AvailableSeatsAssembler;
import ca.ulaval.glo4003.air.transfer.flight.FlightAssembler;
import ca.ulaval.glo4003.air.transfer.flight.SeatsPricingAssembler;
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

    private static final SeatMap SEAT_MAP = new SeatMap(20, 15, 30);
    private static final String ARRIVAL_AIRPORT = "ABC";
    private static final String DEPARTURE_AIRPORT = "DEF";
    private static final LocalDateTime DATE = LocalDateTime.of(2020, 10, 2, 6, 30);
    private static final SeatsPricing SEATS_PRICING = new SeatsPricing(12.99, 150, 689.45);
    private static final double WEIGHT = 30.0;
    private static final boolean A_FILTERED_BY_WEIGHT_RESULT = true;
    private static final String AIRLINE_COMPANY = "AirFrenette";

    @Mock
    private Airplane airplane;

    @Mock
    private AvailableSeatsFactory availableSeatsFactory;

    @Mock
    private AvailableSeats availableSeats;

    private FlightAssembler flightAssembler;

    @Before
    public void setup() {
        given(airplane.hasAdditionalWeightOption()).willReturn(true);
        given(airplane.acceptsWeight(anyDouble())).willReturn(true);
        given(airplane.acceptsAdditionalWeight(anyDouble())).willReturn(true);
        given(airplane.getSeatMap()).willReturn(SEAT_MAP);

        given(availableSeatsFactory.createFromSeatMap(SEAT_MAP)).willReturn(availableSeats);

        flightAssembler = new FlightAssembler(new AvailableSeatsAssembler(), new SeatsPricingAssembler());
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
        return new Flight(DEPARTURE_AIRPORT, ARRIVAL_AIRPORT, DATE, AIRLINE_COMPANY, airplane, SEATS_PRICING, availableSeatsFactory);
    }

    private void assertHasAllTheRelevantProperties(Flight flight, FlightDto flightDto) {
        assertEquals(flight.getAirlineCompany(), flightDto.airlineCompany);
        assertEquals(flight.getAvailableSeats().getEconomicSeats(), flightDto.availableSeatsDto.economicSeats);
        assertEquals(flight.getAvailableSeats().getRegularSeats(), flightDto.availableSeatsDto.regularSeats);
        assertEquals(flight.getAvailableSeats().getBusinessSeats(), flightDto.availableSeatsDto.businessSeats);
        assertEquals(flight.getSeatsPricing().getEconomicSeatsPrice(), flightDto.seatsPricingDto.economicSeatsPrice, 0);
        assertEquals(flight.getSeatsPricing().getRegularSeatsPrice(), flightDto.seatsPricingDto.regularSeatsPrice, 0);
        assertEquals(flight.getSeatsPricing().getBusinessSeatsPrice(), flightDto.seatsPricingDto.businessSeatsPrice, 0);
        assertEquals(flight.getDepartureAirport(), flightDto.departureAirport);
        assertEquals(flight.getArrivalAirport(), flightDto.arrivalAirport);
        assertEquals(flight.getDepartureDate(), flightDto.departureDate);
        assertEquals(flight.hasAdditionalWeightOption(), flightDto.hasAdditionalWeightOption);
        assertEquals(flight.acceptsAdditionalWeight(WEIGHT), flightDto.acceptsAdditionalWeight);
    }
}