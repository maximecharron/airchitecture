package ca.ulaval.glo4003.air.domain.flight;

import ca.ulaval.glo4003.air.domain.DateTimeFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class FlightServiceTest {
    private static final int TICKETS_QUANTITY = 54;
    private static final String AIRLINE_COMPANY = "AirFrenette";
    private static final String DEPARTURE_AIRPORT = "YQB";
    private static final String ARRIVAL_AIRPORT = "DUB";
    private static final LocalDateTime DATE = LocalDateTime.of(2020, 10, 10, 21, 45);
    private static final LocalDateTime NOW_DATE = LocalDateTime.now();
    private static double WEIGHT = 40.5;
    private static boolean FLIGHT_WERE_FILTERED_BY_WEIGHT_RESULT = true;

    @Mock
    private FlightRepository flightRepository;
    @Mock
    private FlightQueryBuilder flightQueryBuilder;
    @Mock
    private WeightFilterVerifier weightFilterVerifier;
    @Mock
    private DateTimeFactory dateTimeFactory;
    @Mock
    private Flight flight;
    @Mock
    private List<Flight> flights;
    @Mock
    private List<Flight> flightsFilteredByWeight;

    private FlightService flightService;

    @Before
    public void setup() {
        given(flightRepository.query()).willReturn(flightQueryBuilder);
        given(flightQueryBuilder.isDepartingFrom(any())).willReturn(flightQueryBuilder);
        given(flightQueryBuilder.isGoingTo(any())).willReturn(flightQueryBuilder);
        given(flightQueryBuilder.isLeavingAfter(any())).willReturn(flightQueryBuilder);
        given(flightQueryBuilder.isLeavingOn(any())).willReturn(flightQueryBuilder);
        given(flightQueryBuilder.acceptsWeight(anyDouble())).willReturn(flightQueryBuilder);
        given(flightQueryBuilder.hasAirlineCompany(anyString())).willReturn(flightQueryBuilder);
        flightService = new FlightService(flightRepository, weightFilterVerifier, dateTimeFactory);
    }

    @Test
    public void givenSearchFiltersWithADepartureDate_whenFindingAllMatchingFlights_thenTheRepositoryFindsCorrespondingFlights() {
        flightService.findAllWithFilters(DEPARTURE_AIRPORT, ARRIVAL_AIRPORT, DATE, WEIGHT);

        verify(flightQueryBuilder).isDepartingFrom(DEPARTURE_AIRPORT);
        verify(flightQueryBuilder).isGoingTo(ARRIVAL_AIRPORT);
        verify(flightQueryBuilder).isLeavingOn(DATE);
        verify(flightQueryBuilder).acceptsWeight(WEIGHT);
    }

    @Test
    public void givenSearchFiltersWithNoDepartureDate_whenFindingAllMatchingFlights_thenTheRepositoryFindsFutureFlights() {
        given(dateTimeFactory.now()).willReturn(NOW_DATE);

        flightService.findAllWithFilters(DEPARTURE_AIRPORT, ARRIVAL_AIRPORT, null, WEIGHT);

        verify(flightQueryBuilder).isLeavingAfter(NOW_DATE);
    }

    @Test
    public void givenPersistedFlights_whenFindingAllFlightsWithFilters_thenVerifiesIfFlightsWereFilteredByWeight() {
        given(flightQueryBuilder.toList()).willReturn(flights).willReturn(flightsFilteredByWeight);
        given(weightFilterVerifier.verifyFlightsFilteredByWeightWithFilters(flightsFilteredByWeight, flights))
            .willReturn(FLIGHT_WERE_FILTERED_BY_WEIGHT_RESULT);

        flightService.findAllWithFilters(DEPARTURE_AIRPORT, ARRIVAL_AIRPORT, DATE, WEIGHT);

        verify(weightFilterVerifier).verifyFlightsFilteredByWeightWithFilters(flightsFilteredByWeight, flights);
    }

    @Test
    public void givenPersistedFlights_whenFindingAllFlightsWithFilters_thenFiltersWithoutWeightBeforeFilteringByWeight() {
        flightService.findAllWithFilters(DEPARTURE_AIRPORT, ARRIVAL_AIRPORT, DATE, WEIGHT);

        InOrder inOrder = inOrder(flightQueryBuilder);
        inOrder.verify(flightQueryBuilder).toList();
        inOrder.verify(flightQueryBuilder).acceptsWeight(WEIGHT);
        inOrder.verify(flightQueryBuilder).toList();
    }

    @Test
    public void givenPersistedFlights_whenFindingAllFlightsWithFilters_thenReturnFlightSearchResult() {
        given(flightQueryBuilder.toList()).willReturn(flights).willReturn(flightsFilteredByWeight);
        given(weightFilterVerifier.verifyFlightsFilteredByWeightWithFilters(flightsFilteredByWeight, flights)).willReturn(FLIGHT_WERE_FILTERED_BY_WEIGHT_RESULT);

        FlightSearchResult result = flightService.findAllWithFilters(DEPARTURE_AIRPORT, ARRIVAL_AIRPORT, DATE, WEIGHT);

        assertEquals(result, new FlightSearchResult(flightsFilteredByWeight, WEIGHT, FLIGHT_WERE_FILTERED_BY_WEIGHT_RESULT));
    }

    @Test
    public void givenAValidFlightIdentifier_whenReservingPlacesForFlight_thenFindFlight() throws FlightNotFoundException {
        willReturn(Optional.of(flight)).given(flightQueryBuilder).findOne();

        flightService.reservePlacesInFlight(AIRLINE_COMPANY, ARRIVAL_AIRPORT, DATE, TICKETS_QUANTITY);

        verify(flightQueryBuilder).hasAirlineCompany(AIRLINE_COMPANY);
        verify(flightQueryBuilder).isLeavingOn(DATE);
        verify(flightQueryBuilder).findOne();
    }

    @Test
    public void givenAValidFlightIdentifier_whenReservingPlacesForFlight_thenReservesPlaces() throws FlightNotFoundException {
        willReturn(Optional.of(flight)).given(flightQueryBuilder).findOne();

        flightService.reservePlacesInFlight(AIRLINE_COMPANY, ARRIVAL_AIRPORT, DATE, TICKETS_QUANTITY);

        verify(flight).reservePlaces(TICKETS_QUANTITY);
    }

    @Test
    public void givenAValidFlightIdentifier_whenReservingPlacesForFlight_thenUpdateFlight() throws FlightNotFoundException {
        willReturn(Optional.of(flight)).given(flightQueryBuilder).findOne();

        flightService.reservePlacesInFlight(AIRLINE_COMPANY, ARRIVAL_AIRPORT, DATE, TICKETS_QUANTITY);

        verify(flightRepository).save(flight);
    }

    @Test(expected = FlightNotFoundException.class)
    public void givenAnInValidFlightIdentifier_whenReservingPlacesForFlight_thenUpdateFlight() throws FlightNotFoundException {
        willReturn(Optional.empty()).given(flightQueryBuilder).findOne();

        flightService.reservePlacesInFlight(AIRLINE_COMPANY, ARRIVAL_AIRPORT, DATE, TICKETS_QUANTITY);
    }

    @Test
    public void givenAValidFlightIdentifier_whenReleasingPlacesForFlight_thenFindFlight() throws FlightNotFoundException {
        willReturn(Optional.of(flight)).given(flightQueryBuilder).findOne();

        flightService.releasePlacesInFlight(AIRLINE_COMPANY, ARRIVAL_AIRPORT, DATE, TICKETS_QUANTITY);

        verify(flightQueryBuilder).hasAirlineCompany(AIRLINE_COMPANY);
        verify(flightQueryBuilder).isLeavingOn(DATE);
        verify(flightQueryBuilder).findOne();
    }

    @Test
    public void givenAValidFlightIdentifier_whenReleasingPlacesForFlight_thenReleasesPlaces() throws FlightNotFoundException {
        willReturn(Optional.of(flight)).given(flightQueryBuilder).findOne();

        flightService.releasePlacesInFlight(AIRLINE_COMPANY, ARRIVAL_AIRPORT, DATE, TICKETS_QUANTITY);

        verify(flight).releasePlaces(TICKETS_QUANTITY);
    }

    @Test
    public void givenAValidFlightIdentifier_whenReleasingPlacesForFlight_thenUpdateFlight() throws FlightNotFoundException {
        willReturn(Optional.of(flight)).given(flightQueryBuilder).findOne();

        flightService.reservePlacesInFlight(AIRLINE_COMPANY, ARRIVAL_AIRPORT, DATE, TICKETS_QUANTITY);

        verify(flightRepository).save(flight);
    }

    @Test(expected = FlightNotFoundException.class)
    public void givenAnInValidFlightIdentifier_whenReleasingPlacesForFlight_thenUpdateFlight() throws FlightNotFoundException {
        willReturn(Optional.empty()).given(flightQueryBuilder).findOne();

        flightService.releasePlacesInFlight(AIRLINE_COMPANY, ARRIVAL_AIRPORT, DATE, TICKETS_QUANTITY);
    }
}