package ca.ulaval.glo4003.air.domain.flight;

import ca.ulaval.glo4003.air.transfer.flight.FlightSearchQueryAssembler;
import ca.ulaval.glo4003.air.transfer.flight.dto.FlightSearchQueryDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class AirCargoFlightMatcherTest {

    private static final String DEPARTURE_AIRPORT = "YQB";
    private static final String ARRIVAL_AIRPORT = "DUB";
    private static final LocalDateTime DATE = LocalDateTime.of(2020, 10, 10, 21, 45);
    private static final boolean HAS_ECONOMIC_FLIGHTS = false;
    private static final boolean HAS_REGULAR_FLIGHTS = false;
    private static final boolean HAS_BUSINESS_FLIGHTS = false;
    private static final boolean ONLY_AIRVIVANT = true;
    private static final boolean ACCEPTS_AIRCARGO = true;
    private static double WEIGHT = 40.5;

    @Mock
    private FlightRepository flightRepository;

    @Mock
    private FlightQueryBuilder flightQueryBuilder;

    @Mock
    private PassengerFlight passengerFlight;

    @Mock
    private AirCargoFlight matchingAirCargoFlight;

    @Mock
    private AirCargoFlight nonMatchingAirCargoFlight;

    private AirCargoFlightMatcher airCargoFlightMatcher;

    @Before
    public void setup() {
        given(passengerFlight.getDepartureDate()).willReturn(DATE);

        given(matchingAirCargoFlight.isLeavingWithinXDaysOf(any(), eq(3))).willReturn(true);
        given(nonMatchingAirCargoFlight.isLeavingWithinXDaysOf(any(), eq(3))).willReturn(false);

        given(flightRepository.query()).willReturn(flightQueryBuilder);
        given(flightQueryBuilder.isDepartingFrom(any())).willReturn(flightQueryBuilder);
        given(flightQueryBuilder.isGoingTo(any())).willReturn(flightQueryBuilder);
        given(flightQueryBuilder.acceptsWeight(anyDouble())).willReturn(flightQueryBuilder);
        given(flightQueryBuilder.isAirVivant()).willReturn(flightQueryBuilder);
        given(flightQueryBuilder.getAirCargoFlights()).willReturn(Arrays.asList(matchingAirCargoFlight, nonMatchingAirCargoFlight));
        airCargoFlightMatcher = new AirCargoFlightMatcher(flightRepository);
    }

    @Test
    public void givenSearchFilters_whenMatchingAirCargoFlightsWithPassengerFlights_thenAirCargoFlightsAreFiltered() {
        FlightSearchQueryDto flightSearchQueryDto = new FlightSearchQueryAssembler().create(DEPARTURE_AIRPORT, ARRIVAL_AIRPORT, DATE, WEIGHT, ONLY_AIRVIVANT, ACCEPTS_AIRCARGO, HAS_ECONOMIC_FLIGHTS, HAS_REGULAR_FLIGHTS, HAS_BUSINESS_FLIGHTS);
        List<PassengerFlight> passengerFlights = Arrays.asList(passengerFlight);

        airCargoFlightMatcher.matchWithAirCargoFlights(passengerFlights, flightSearchQueryDto);

        verify(flightQueryBuilder).isDepartingFrom(DEPARTURE_AIRPORT);
        verify(flightQueryBuilder).isGoingTo(ARRIVAL_AIRPORT);
        verify(flightQueryBuilder).acceptsWeight(WEIGHT);
    }

    @Test
    public void givenSearchFiltersWithAOnlyAirVivantFilter_whenMatchingAirCargoFlightsWithPassengerFlights_thenAirCargoFlightsAreFiltered() {
        FlightSearchQueryDto flightSearchQueryDto = new FlightSearchQueryAssembler().create(DEPARTURE_AIRPORT, ARRIVAL_AIRPORT, DATE, WEIGHT, ONLY_AIRVIVANT, ACCEPTS_AIRCARGO, HAS_ECONOMIC_FLIGHTS, HAS_REGULAR_FLIGHTS, HAS_BUSINESS_FLIGHTS);
        List<PassengerFlight> passengerFlights = Arrays.asList(passengerFlight);

        airCargoFlightMatcher.matchWithAirCargoFlights(passengerFlights, flightSearchQueryDto);

        verify(flightQueryBuilder).isAirVivant();
    }

    @Test
    public void givenSearchFilters_whenMatchingAirCargoFlightsWithPassengerFlights_thenTheRepositoryFindsTheCorrespondingAirCargoFlights() {
        FlightSearchQueryDto flightSearchQueryDto = new FlightSearchQueryAssembler().create(DEPARTURE_AIRPORT, ARRIVAL_AIRPORT, DATE, WEIGHT, ONLY_AIRVIVANT, ACCEPTS_AIRCARGO, HAS_ECONOMIC_FLIGHTS, HAS_REGULAR_FLIGHTS, HAS_BUSINESS_FLIGHTS);
        List<PassengerFlight> passengerFlights = Arrays.asList(passengerFlight);

        airCargoFlightMatcher.matchWithAirCargoFlights(passengerFlights, flightSearchQueryDto);

        verify(flightQueryBuilder).getAirCargoFlights();
    }

    @Test
    public void givenAirCargoFlightsAndPassengerFlights_whenMatchingAirCargoFlightsWithPassengerFlights_thenTheAirCargoFlightsAreFilteredToMatchPassengerFlightsDepartureDate() {
        FlightSearchQueryDto flightSearchQueryDto = new FlightSearchQueryAssembler().create(DEPARTURE_AIRPORT, ARRIVAL_AIRPORT, DATE, WEIGHT, ONLY_AIRVIVANT, ACCEPTS_AIRCARGO, HAS_ECONOMIC_FLIGHTS, HAS_REGULAR_FLIGHTS, HAS_BUSINESS_FLIGHTS);
        List<PassengerFlight> passengerFlights = Arrays.asList(passengerFlight);

        airCargoFlightMatcher.matchWithAirCargoFlights(passengerFlights, flightSearchQueryDto);

        verify(matchingAirCargoFlight).isLeavingWithinXDaysOf(DATE, 3);
    }

    @Test
    public void givenAirCargoFlightsAndPassengerFlights_whenMatchingAirCargoFlightsWithPassengerFlights_thenMatchingAirCargoFlightAndPassengerFlightsWillBeReturned() {
        FlightSearchQueryDto flightSearchQueryDto = new FlightSearchQueryAssembler().create(DEPARTURE_AIRPORT, ARRIVAL_AIRPORT, DATE, WEIGHT, ONLY_AIRVIVANT, ACCEPTS_AIRCARGO, HAS_ECONOMIC_FLIGHTS, HAS_REGULAR_FLIGHTS, HAS_BUSINESS_FLIGHTS);
        List<PassengerFlight> passengerFlights = Arrays.asList(passengerFlight);

        Map<PassengerFlight, AirCargoFlight> matchingAirCargoFlights = airCargoFlightMatcher.matchWithAirCargoFlights(passengerFlights, flightSearchQueryDto);

        assertEquals(matchingAirCargoFlight, matchingAirCargoFlights.get(passengerFlight));
    }

    @Test
    public void givenAirCargoFlightsAndPassengerFlights_whenMatchingAirCargoFlightsWithPassengerFlights_thenNonMatchingAirCargoFlightsAreNotReturned() {
        FlightSearchQueryDto flightSearchQueryDto = new FlightSearchQueryAssembler().create(DEPARTURE_AIRPORT, ARRIVAL_AIRPORT, DATE, WEIGHT, ONLY_AIRVIVANT, ACCEPTS_AIRCARGO, HAS_ECONOMIC_FLIGHTS, HAS_REGULAR_FLIGHTS, HAS_BUSINESS_FLIGHTS);
        List<PassengerFlight> passengerFlights = Arrays.asList(passengerFlight);

        Map<PassengerFlight, AirCargoFlight> matchingAirCargoFlights = airCargoFlightMatcher.matchWithAirCargoFlights(passengerFlights, flightSearchQueryDto);

        assertFalse(matchingAirCargoFlights.containsValue(nonMatchingAirCargoFlight));
    }
}
