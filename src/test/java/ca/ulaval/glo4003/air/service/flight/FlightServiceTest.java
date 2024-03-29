package ca.ulaval.glo4003.air.service.flight;

import ca.ulaval.glo4003.air.domain.DateTimeFactory;
import ca.ulaval.glo4003.air.domain.airplane.SeatMap;
import ca.ulaval.glo4003.air.domain.flight.*;
import ca.ulaval.glo4003.air.service.user.UserService;
import ca.ulaval.glo4003.air.transfer.flight.FlightSearchQueryAssembler;
import ca.ulaval.glo4003.air.transfer.flight.PassengerFlightAssembler;
import ca.ulaval.glo4003.air.transfer.flight.dto.FlightSearchQueryDto;
import ca.ulaval.glo4003.air.transfer.flight.dto.FlightSearchResultDto;
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

    private static final SeatMap A_SEAT_MAP = new SeatMap(150, 100, 50);
    private static final String AIRLINE_COMPANY = "AirFrenette";
    private static final String DEPARTURE_AIRPORT = "YQB";
    private static final String ARRIVAL_AIRPORT = "DUB";
    private static final LocalDateTime DATE = LocalDateTime.of(2020, 10, 10, 21, 45);
    private static final LocalDateTime NOW_DATE = LocalDateTime.now();
    private static final boolean HAS_ECONOMIC_FLIGHTS = false;
    private static final boolean HAS_REGULAR_FLIGHTS = false;
    private static final boolean HAS_BUSINESS_FLIGHTS = false;
    private static final String ACCESS_TOKEN = "hooked on a feeling";
    private static double WEIGHT = 40.5;
    private static boolean ONLY_AIRVIVANT = true;
    private static boolean ACCEPTS_AIRCARGO = false;
    private static boolean FLIGHT_WERE_FILTERED_BY_WEIGHT_RESULT = true;

    @Mock
    private UserService userService;

    @Mock
    private FlightRepository flightRepository;

    @Mock
    private FlightQueryBuilder flightQueryBuilder;

    @Mock
    private WeightFilterVerifier weightFilterVerifier;

    @Mock
    private DateTimeFactory dateTimeFactory;

    @Mock
    private PassengerFlight passengerFlight;

    @Mock
    private List<PassengerFlight> passengerFlights;

    @Mock
    private List<PassengerFlight> flightsFilteredByWeight;

    @Mock
    private FlightSortingStrategy flightSortingStrategy;

    @Mock
    private PassengerFlightAssembler passengerFlightAssembler;

    @Mock
    private FlightSearchResultDto flightSearchResultDto;

    @Mock
    private AirCargoFlightMatcher airCargoFlightMatcher;

    private FlightService flightService;

    @Before
    public void setup() {
        given(flightRepository.query()).willReturn(flightQueryBuilder);
        given(flightQueryBuilder.isDepartingFrom(any())).willReturn(flightQueryBuilder);
        given(flightQueryBuilder.isGoingTo(any())).willReturn(flightQueryBuilder);
        given(flightQueryBuilder.isLeavingAfterOrOn(any())).willReturn(flightQueryBuilder);
        given(flightQueryBuilder.isLeavingOn(any())).willReturn(flightQueryBuilder);
        given(flightQueryBuilder.acceptsWeight(anyDouble())).willReturn(flightQueryBuilder);
        given(flightQueryBuilder.isAirVivant()).willReturn(flightQueryBuilder);
        given(flightQueryBuilder.hasAirlineCompany(anyString())).willReturn(flightQueryBuilder);
        flightService = new FlightService(flightRepository, weightFilterVerifier, dateTimeFactory, flightSortingStrategy, passengerFlightAssembler, userService, airCargoFlightMatcher);
    }

    @Test
    public void givenAValidAccessToken_whenFindingFlights_userSearchPreferencesAreUpdated() throws Exception {
        FlightSearchQueryDto flightSearchQueryDto = new FlightSearchQueryAssembler().create(DEPARTURE_AIRPORT, ARRIVAL_AIRPORT, DATE, WEIGHT, ONLY_AIRVIVANT, ACCEPTS_AIRCARGO, HAS_ECONOMIC_FLIGHTS, HAS_REGULAR_FLIGHTS, HAS_BUSINESS_FLIGHTS);

        flightService.findAllWithFilters(ACCESS_TOKEN, flightSearchQueryDto);

        verify(userService).incrementAuthenticatedUserSearchPreferences(ACCESS_TOKEN, ONLY_AIRVIVANT, HAS_ECONOMIC_FLIGHTS, HAS_REGULAR_FLIGHTS, HAS_BUSINESS_FLIGHTS);
    }

    @Test
    public void givenSearchFiltersWithADepartureDate_whenFindingAllMatchingFlights_thenTheRepositoryFindsCorrespondingFlights() throws Exception {
        FlightSearchQueryDto flightSearchQueryDto = new FlightSearchQueryAssembler().create(DEPARTURE_AIRPORT, ARRIVAL_AIRPORT, DATE, WEIGHT, ONLY_AIRVIVANT, ACCEPTS_AIRCARGO, HAS_ECONOMIC_FLIGHTS, HAS_REGULAR_FLIGHTS, HAS_BUSINESS_FLIGHTS);

        flightService.findAllWithFilters(ACCESS_TOKEN, flightSearchQueryDto);

        verify(flightQueryBuilder).isLeavingAfterOrOn(DATE);
    }

    @Test
    public void givenSearchFiltersWithNoDepartureDate_whenFindingAllMatchingFlights_thenTheRepositoryFindsFutureFlights() throws Exception {
        given(dateTimeFactory.now()).willReturn(NOW_DATE);

        FlightSearchQueryDto flightSearchQueryDto = new FlightSearchQueryAssembler().create(DEPARTURE_AIRPORT, ARRIVAL_AIRPORT, null, WEIGHT, ONLY_AIRVIVANT, ACCEPTS_AIRCARGO, HAS_ECONOMIC_FLIGHTS, HAS_REGULAR_FLIGHTS, HAS_BUSINESS_FLIGHTS);

        flightService.findAllWithFilters(ACCESS_TOKEN, flightSearchQueryDto);

        verify(flightQueryBuilder).isLeavingAfterOrOn(NOW_DATE);
    }

    @Test
    public void givenSearchFiltersWithADepartingAirport_whenFindingAllMatchingFlights_thenTheRepositoryFindsCorrespondingFlights() throws Exception {
        FlightSearchQueryDto flightSearchQueryDto = new FlightSearchQueryAssembler().create(DEPARTURE_AIRPORT, ARRIVAL_AIRPORT, DATE, WEIGHT, ONLY_AIRVIVANT, ACCEPTS_AIRCARGO, HAS_ECONOMIC_FLIGHTS, HAS_REGULAR_FLIGHTS, HAS_BUSINESS_FLIGHTS);

        flightService.findAllWithFilters(ACCESS_TOKEN, flightSearchQueryDto);

        verify(flightQueryBuilder).isDepartingFrom(DEPARTURE_AIRPORT);
    }

    @Test
    public void givenSearchFiltersWithAnArrivalAirport_whenFindingAllMatchingFlights_thenTheRepositoryFindsCorrespondingFlights() throws Exception {
        FlightSearchQueryDto flightSearchQueryDto = new FlightSearchQueryAssembler().create(DEPARTURE_AIRPORT, ARRIVAL_AIRPORT, DATE, WEIGHT, ONLY_AIRVIVANT, ACCEPTS_AIRCARGO, HAS_ECONOMIC_FLIGHTS, HAS_REGULAR_FLIGHTS, HAS_BUSINESS_FLIGHTS);

        flightService.findAllWithFilters(ACCESS_TOKEN, flightSearchQueryDto);

        verify(flightQueryBuilder).isGoingTo(ARRIVAL_AIRPORT);
    }

    @Test
    public void givenSearchFiltersWithAWeight_whenFindingAllMatchingFlights_thenTheRepositoryFindsCorrespondingFlights() throws Exception {
        FlightSearchQueryDto flightSearchQueryDto = new FlightSearchQueryAssembler().create(DEPARTURE_AIRPORT, ARRIVAL_AIRPORT, DATE, WEIGHT, ONLY_AIRVIVANT, ACCEPTS_AIRCARGO, HAS_ECONOMIC_FLIGHTS, HAS_REGULAR_FLIGHTS, HAS_BUSINESS_FLIGHTS);

        flightService.findAllWithFilters(ACCESS_TOKEN, flightSearchQueryDto);

        verify(flightQueryBuilder).acceptsWeight(WEIGHT);
    }

    @Test
    public void givenSearchFiltersWithAOnlyAirVivantFilter_whenFindingAllMatchingFlights_thenTheRepositoryFindsCorrespondingFlights() throws Exception {
        FlightSearchQueryDto flightSearchQueryDto = new FlightSearchQueryAssembler().create(DEPARTURE_AIRPORT, ARRIVAL_AIRPORT, DATE, WEIGHT, ONLY_AIRVIVANT, ACCEPTS_AIRCARGO, HAS_ECONOMIC_FLIGHTS, HAS_REGULAR_FLIGHTS, HAS_BUSINESS_FLIGHTS);

        flightService.findAllWithFilters(ACCESS_TOKEN, flightSearchQueryDto);

        verify(flightQueryBuilder).isAirVivant();
    }

    @Test
    public void givenPersistedFlights_whenFindingAllFlightsWithFilters_thenVerifiesIfFlightsWereFilteredByWeight() throws Exception {
        given(flightQueryBuilder.getPassengerFlights()).willReturn(passengerFlights).willReturn(flightsFilteredByWeight);
        given(weightFilterVerifier.verifyFlightsFilteredByWeightWithFilters(flightsFilteredByWeight, passengerFlights))
            .willReturn(FLIGHT_WERE_FILTERED_BY_WEIGHT_RESULT);

        FlightSearchQueryDto flightSearchQueryDto = new FlightSearchQueryAssembler().create(DEPARTURE_AIRPORT, ARRIVAL_AIRPORT, DATE, WEIGHT, ONLY_AIRVIVANT, ACCEPTS_AIRCARGO, HAS_ECONOMIC_FLIGHTS, HAS_REGULAR_FLIGHTS, HAS_BUSINESS_FLIGHTS);

        flightService.findAllWithFilters(ACCESS_TOKEN, flightSearchQueryDto);

        verify(weightFilterVerifier).verifyFlightsFilteredByWeightWithFilters(any(), eq(passengerFlights));
    }

    @Test
    public void givenPersistedFlights_whenFindingAllFlightsWithFilters_thenFiltersWithoutWeightBeforeFilteringByWeight() {
        FlightSearchQueryDto flightSearchQueryDto = new FlightSearchQueryAssembler().create(DEPARTURE_AIRPORT, ARRIVAL_AIRPORT, DATE, WEIGHT, ONLY_AIRVIVANT, ACCEPTS_AIRCARGO, HAS_ECONOMIC_FLIGHTS, HAS_REGULAR_FLIGHTS, HAS_BUSINESS_FLIGHTS);

        flightService.findAllWithFilters(ACCESS_TOKEN, flightSearchQueryDto);

        InOrder inOrder = inOrder(flightQueryBuilder);
        inOrder.verify(flightQueryBuilder).getPassengerFlights();
        inOrder.verify(flightQueryBuilder).acceptsWeight(WEIGHT);
        inOrder.verify(flightQueryBuilder).getPassengerFlights();
    }

    @Test
    public void givenPersistedFlights_whenFindingAllFlightsWithFilters_thenReturnFlightSearchResult() throws Exception {
        given(flightQueryBuilder.getPassengerFlights()).willReturn(passengerFlights).willReturn(flightsFilteredByWeight);
        given(weightFilterVerifier.verifyFlightsFilteredByWeightWithFilters(flightsFilteredByWeight, passengerFlights)).willReturn(FLIGHT_WERE_FILTERED_BY_WEIGHT_RESULT);
        willReturn(flightSearchResultDto).given(passengerFlightAssembler).create(any(FlightSearchResult.class));

        FlightSearchQueryDto flightSearchQueryDto = new FlightSearchQueryAssembler().create(DEPARTURE_AIRPORT, ARRIVAL_AIRPORT, DATE, WEIGHT, ONLY_AIRVIVANT, ACCEPTS_AIRCARGO, HAS_ECONOMIC_FLIGHTS, HAS_REGULAR_FLIGHTS, HAS_BUSINESS_FLIGHTS);

        FlightSearchResultDto result = flightService.findAllWithFilters(ACCESS_TOKEN, flightSearchQueryDto);

        assertEquals(result, flightSearchResultDto);
    }

    @Test
    public void givenAValidFlightIdentifier_whenReservingPlacesForFlight_thenFindFlight() throws FlightNotFoundException {
        willReturn(Optional.of(passengerFlight)).given(flightQueryBuilder).getOnePassengerFlight();
        flightService.reservePlacesInFlight(AIRLINE_COMPANY, ARRIVAL_AIRPORT, DATE, A_SEAT_MAP);

        verify(flightQueryBuilder).hasAirlineCompany(AIRLINE_COMPANY);
        verify(flightQueryBuilder).isLeavingOn(DATE);
        verify(flightQueryBuilder).getOnePassengerFlight();
    }

    @Test
    public void givenAValidFlightIdentifier_whenReservingPlacesForFlight_thenReservesPlaces() throws FlightNotFoundException {
        willReturn(Optional.of(passengerFlight)).given(flightQueryBuilder).getOnePassengerFlight();

        flightService.reservePlacesInFlight(AIRLINE_COMPANY, ARRIVAL_AIRPORT, DATE, A_SEAT_MAP);

        verify(passengerFlight).reserveSeats(A_SEAT_MAP);
    }

    @Test
    public void givenAValidFlightIdentifier_whenReservingPlacesForFlight_thenUpdateFlight() throws FlightNotFoundException {
        willReturn(Optional.of(passengerFlight)).given(flightQueryBuilder).getOnePassengerFlight();

        flightService.reservePlacesInFlight(AIRLINE_COMPANY, ARRIVAL_AIRPORT, DATE, A_SEAT_MAP);

        verify(flightRepository).save(passengerFlight);
    }

    @Test(expected = FlightNotFoundException.class)
    public void givenAnInValidFlightIdentifier_whenReservingPlacesForFlight_thenUpdateFlight() throws FlightNotFoundException {
        willReturn(Optional.empty()).given(flightQueryBuilder).getOnePassengerFlight();

        flightService.reservePlacesInFlight(AIRLINE_COMPANY, ARRIVAL_AIRPORT, DATE, A_SEAT_MAP);
    }

    @Test
    public void givenAValidFlightIdentifier_whenReleasingPlacesForFlight_thenFindFlight() throws FlightNotFoundException {
        willReturn(Optional.of(passengerFlight)).given(flightQueryBuilder).getOnePassengerFlight();

        flightService.releasePlacesInFlight(AIRLINE_COMPANY, ARRIVAL_AIRPORT, DATE, A_SEAT_MAP);

        verify(flightQueryBuilder).hasAirlineCompany(AIRLINE_COMPANY);
        verify(flightQueryBuilder).isLeavingOn(DATE);
        verify(flightQueryBuilder).getOnePassengerFlight();
    }

    @Test
    public void givenAValidFlightIdentifier_whenReleasingPlacesForFlight_thenReleasesPlaces() throws FlightNotFoundException {
        willReturn(Optional.of(passengerFlight)).given(flightQueryBuilder).getOnePassengerFlight();

        flightService.releasePlacesInFlight(AIRLINE_COMPANY, ARRIVAL_AIRPORT, DATE, A_SEAT_MAP);

        verify(passengerFlight).releaseSeats(A_SEAT_MAP);
    }

    @Test
    public void givenAValidFlightIdentifier_whenReleasingPlacesForFlight_thenUpdateFlight() throws FlightNotFoundException {
        willReturn(Optional.of(passengerFlight)).given(flightQueryBuilder).getOnePassengerFlight();

        flightService.reservePlacesInFlight(AIRLINE_COMPANY, ARRIVAL_AIRPORT, DATE, A_SEAT_MAP);

        verify(flightRepository).save(passengerFlight);
    }

    @Test(expected = FlightNotFoundException.class)
    public void givenAnInValidFlightIdentifier_whenReleasingPlacesForFlight_thenUpdateFlight() throws FlightNotFoundException {
        willReturn(Optional.empty()).given(flightQueryBuilder).getOnePassengerFlight();

        flightService.releasePlacesInFlight(AIRLINE_COMPANY, ARRIVAL_AIRPORT, DATE, A_SEAT_MAP);
    }

    @Test(expected = InvalidParameterException.class)
    public void givenAMissingDepartureAirport_whenFindingAllFlightsWithFilters_then400IsThrown() throws Exception {
        String departureAirport = null;

        FlightSearchQueryDto flightSearchQueryDto = new FlightSearchQueryAssembler().create(departureAirport, ARRIVAL_AIRPORT, DATE, WEIGHT, ONLY_AIRVIVANT, ACCEPTS_AIRCARGO, HAS_ECONOMIC_FLIGHTS, HAS_REGULAR_FLIGHTS, HAS_BUSINESS_FLIGHTS);

        flightService.findAllWithFilters(ACCESS_TOKEN, flightSearchQueryDto);
    }

    @Test(expected = InvalidParameterException.class)
    public void givenAMissingArrivalAirport_whenFindingAllFlightsWithFilters_then400IsThrown() throws Exception {
        String arrivalAirport = null;

        FlightSearchQueryDto flightSearchQueryDto = new FlightSearchQueryAssembler().create(DEPARTURE_AIRPORT, arrivalAirport, DATE, WEIGHT, ONLY_AIRVIVANT, ACCEPTS_AIRCARGO, HAS_ECONOMIC_FLIGHTS, HAS_REGULAR_FLIGHTS, HAS_BUSINESS_FLIGHTS);

        flightService.findAllWithFilters(ACCESS_TOKEN, flightSearchQueryDto);
    }

    @Test(expected = InvalidParameterException.class)
    public void givenAMissingWeight_whenFindingAllFlightsWithFilters_then400IsThrown() throws Exception {
        double weight = 0;

        FlightSearchQueryDto flightSearchQueryDto = new FlightSearchQueryAssembler().create(DEPARTURE_AIRPORT, ARRIVAL_AIRPORT, DATE, weight, ONLY_AIRVIVANT, ACCEPTS_AIRCARGO, HAS_ECONOMIC_FLIGHTS, HAS_REGULAR_FLIGHTS, HAS_BUSINESS_FLIGHTS);

        flightService.findAllWithFilters(ACCESS_TOKEN, flightSearchQueryDto);
    }
}
