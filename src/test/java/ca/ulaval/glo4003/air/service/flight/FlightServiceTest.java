package ca.ulaval.glo4003.air.service.flight;

import ca.ulaval.glo4003.air.api.flight.dto.FlightSearchResultDto;
import ca.ulaval.glo4003.air.domain.DateTimeFactory;
import ca.ulaval.glo4003.air.domain.airplane.SeatMap;
import ca.ulaval.glo4003.air.domain.flight.*;
import ca.ulaval.glo4003.air.transfer.flight.FlightAssembler;
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
import static org.mockito.BDDMockito.willThrow;
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
    private static double WEIGHT = 40.5;
    private static boolean ONLY_AIRVIVANT = true;
    private static boolean ACCEPTS_AIRCARGO = false;
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
    private PassengerFlight passengerFlight;

    @Mock
    private List<PassengerFlight> passengerFlights;

    @Mock
    private List<PassengerFlight> flightsFilteredByWeight;

    @Mock
    private FlightAssembler flightAssembler;

    @Mock
    private FlightSearchResultDto flightSearchResultDto;

    private FlightService flightService;

    @Before
    public void setup() {
        given(flightRepository.query()).willReturn(flightQueryBuilder);
        given(flightQueryBuilder.isDepartingFrom(any())).willReturn(flightQueryBuilder);
        given(flightQueryBuilder.isGoingTo(any())).willReturn(flightQueryBuilder);
        given(flightQueryBuilder.isLeavingAfter(any())).willReturn(flightQueryBuilder);
        given(flightQueryBuilder.isLeavingOn(any())).willReturn(flightQueryBuilder);
        given(flightQueryBuilder.acceptsWeight(anyDouble())).willReturn(flightQueryBuilder);
        given(flightQueryBuilder.isAirVivant()).willReturn(flightQueryBuilder);
        given(flightQueryBuilder.hasAirlineCompany(anyString())).willReturn(flightQueryBuilder);
        flightService = new FlightService(flightRepository, weightFilterVerifier, dateTimeFactory, flightAssembler);
    }

    @Test
    public void givenSearchFiltersWithADepartureDate_whenFindingAllMatchingFlights_thenTheRepositoryFindsCorrespondingFlights() {
        flightService.findAllWithFilters(DEPARTURE_AIRPORT, ARRIVAL_AIRPORT, DATE, WEIGHT, ONLY_AIRVIVANT, ACCEPTS_AIRCARGO);

        verify(flightQueryBuilder).isLeavingOn(DATE);
    }

    @Test
    public void givenSearchFiltersWithNoDepartureDate_whenFindingAllMatchingFlights_thenTheRepositoryFindsFutureFlights() {
        given(dateTimeFactory.now()).willReturn(NOW_DATE);

        flightService.findAllWithFilters(DEPARTURE_AIRPORT, ARRIVAL_AIRPORT, null, WEIGHT, ONLY_AIRVIVANT, ACCEPTS_AIRCARGO);

        verify(flightQueryBuilder).isLeavingAfter(NOW_DATE);
    }

    @Test
    public void givenSearchFiltersWithADepartingAirport_whenFindingAllMatchingFlights_thenTheRepositoryFindsCorrespondingFlights() {
        flightService.findAllWithFilters(DEPARTURE_AIRPORT, ARRIVAL_AIRPORT, DATE, WEIGHT, ONLY_AIRVIVANT, ACCEPTS_AIRCARGO);

        verify(flightQueryBuilder).isDepartingFrom(DEPARTURE_AIRPORT);
    }

    @Test
    public void givenSearchFiltersWithAnArrivalAirport_whenFindingAllMatchingFlights_thenTheRepositoryFindsCorrespondingFlights() {
        flightService.findAllWithFilters(DEPARTURE_AIRPORT, ARRIVAL_AIRPORT, DATE, WEIGHT, ONLY_AIRVIVANT, ACCEPTS_AIRCARGO);

        verify(flightQueryBuilder).isGoingTo(ARRIVAL_AIRPORT);
    }

    @Test
    public void givenSearchFiltersWithAWeight_whenFindingAllMatchingFlights_thenTheRepositoryFindsCorrespondingFlights() {
        flightService.findAllWithFilters(DEPARTURE_AIRPORT, ARRIVAL_AIRPORT, DATE, WEIGHT, ONLY_AIRVIVANT, ACCEPTS_AIRCARGO);

        verify(flightQueryBuilder).acceptsWeight(WEIGHT);
    }

    @Test
    public void givenSearchFiltersWithAOnlyAirVivantFilter_whenFindingAllMatchingFlights_thenTheRepositoryFindsCorrespondingFlights() {
        flightService.findAllWithFilters(DEPARTURE_AIRPORT, ARRIVAL_AIRPORT, DATE, WEIGHT, ONLY_AIRVIVANT, ACCEPTS_AIRCARGO);

        verify(flightQueryBuilder).isAirVivant();
    }

    @Test
    public void givenPersistedFlights_whenFindingAllFlightsWithFilters_thenVerifiesIfFlightsWereFilteredByWeight() {
        given(flightQueryBuilder.getPassengerFlights()).willReturn(passengerFlights).willReturn(flightsFilteredByWeight);
        given(weightFilterVerifier.verifyFlightsFilteredByWeightWithFilters(flightsFilteredByWeight, passengerFlights))
            .willReturn(FLIGHT_WERE_FILTERED_BY_WEIGHT_RESULT);

        flightService.findAllWithFilters(DEPARTURE_AIRPORT, ARRIVAL_AIRPORT, DATE, WEIGHT, ONLY_AIRVIVANT, ACCEPTS_AIRCARGO);

        verify(weightFilterVerifier).verifyFlightsFilteredByWeightWithFilters(flightsFilteredByWeight, passengerFlights);
    }

    @Test
    public void givenPersistedFlights_whenFindingAllFlightsWithFilters_thenFiltersWithoutWeightBeforeFilteringByWeight() {
        flightService.findAllWithFilters(DEPARTURE_AIRPORT, ARRIVAL_AIRPORT, DATE, WEIGHT, ONLY_AIRVIVANT, ACCEPTS_AIRCARGO);

        InOrder inOrder = inOrder(flightQueryBuilder);
        inOrder.verify(flightQueryBuilder).getPassengerFlights();
        inOrder.verify(flightQueryBuilder).acceptsWeight(WEIGHT);
        inOrder.verify(flightQueryBuilder).getPassengerFlights();
    }

    @Test
    public void givenPersistedFlights_whenFindingAllFlightsWithFilters_thenReturnFlightSearchResult() {
        given(flightQueryBuilder.getPassengerFlights()).willReturn(passengerFlights).willReturn(flightsFilteredByWeight);
        given(weightFilterVerifier.verifyFlightsFilteredByWeightWithFilters(flightsFilteredByWeight, passengerFlights)).willReturn(FLIGHT_WERE_FILTERED_BY_WEIGHT_RESULT);
        willReturn(flightSearchResultDto).given(flightAssembler).create(any(FlightSearchResult.class));

        FlightSearchResultDto result = flightService.findAllWithFilters(DEPARTURE_AIRPORT, ARRIVAL_AIRPORT, DATE, WEIGHT, ONLY_AIRVIVANT, ACCEPTS_AIRCARGO);

        assertEquals(result, flightSearchResultDto);
    }

    @Test
    public void givenAValidFlightIdentifier_whenReservingPlacesForFlight_thenFindFlight() throws FlightNotFoundException {
        willReturn(Optional.of(passengerFlight)).given(flightQueryBuilder).findOnePassengerFlight();
        flightService.reservePlacesInFlight(AIRLINE_COMPANY, ARRIVAL_AIRPORT, DATE, A_SEAT_MAP);

        verify(flightQueryBuilder).hasAirlineCompany(AIRLINE_COMPANY);
        verify(flightQueryBuilder).isLeavingOn(DATE);
        verify(flightQueryBuilder).findOnePassengerFlight();
    }

    @Test
    public void givenAValidFlightIdentifier_whenReservingPlacesForFlight_thenReservesPlaces() throws FlightNotFoundException {
        willReturn(Optional.of(passengerFlight)).given(flightQueryBuilder).findOnePassengerFlight();

        flightService.reservePlacesInFlight(AIRLINE_COMPANY, ARRIVAL_AIRPORT, DATE, A_SEAT_MAP);

        verify(passengerFlight).reserveSeats(A_SEAT_MAP);
    }

    @Test
    public void givenAValidFlightIdentifier_whenReservingPlacesForFlight_thenUpdateFlight() throws FlightNotFoundException {
        willReturn(Optional.of(passengerFlight)).given(flightQueryBuilder).findOnePassengerFlight();

        flightService.reservePlacesInFlight(AIRLINE_COMPANY, ARRIVAL_AIRPORT, DATE, A_SEAT_MAP);

        verify(flightRepository).save(passengerFlight);
    }

    @Test(expected = FlightNotFoundException.class)
    public void givenAnInValidFlightIdentifier_whenReservingPlacesForFlight_thenUpdateFlight() throws FlightNotFoundException {
        willReturn(Optional.empty()).given(flightQueryBuilder).findOnePassengerFlight();

        flightService.reservePlacesInFlight(AIRLINE_COMPANY, ARRIVAL_AIRPORT, DATE, A_SEAT_MAP);
    }

    @Test
    public void givenAValidFlightIdentifier_whenReleasingPlacesForFlight_thenFindFlight() throws FlightNotFoundException {
        willReturn(Optional.of(passengerFlight)).given(flightQueryBuilder).findOnePassengerFlight();

        flightService.releasePlacesInFlight(AIRLINE_COMPANY, ARRIVAL_AIRPORT, DATE, A_SEAT_MAP);

        verify(flightQueryBuilder).hasAirlineCompany(AIRLINE_COMPANY);
        verify(flightQueryBuilder).isLeavingOn(DATE);
        verify(flightQueryBuilder).findOnePassengerFlight();
    }

    @Test
    public void givenAValidFlightIdentifier_whenReleasingPlacesForFlight_thenReleasesPlaces() throws FlightNotFoundException {
        willReturn(Optional.of(passengerFlight)).given(flightQueryBuilder).findOnePassengerFlight();

        flightService.releasePlacesInFlight(AIRLINE_COMPANY, ARRIVAL_AIRPORT, DATE, A_SEAT_MAP);

        verify(passengerFlight).releaseSeats(A_SEAT_MAP);
    }

    @Test
    public void givenAValidFlightIdentifier_whenReleasingPlacesForFlight_thenUpdateFlight() throws FlightNotFoundException {
        willReturn(Optional.of(passengerFlight)).given(flightQueryBuilder).findOnePassengerFlight();

        flightService.reservePlacesInFlight(AIRLINE_COMPANY, ARRIVAL_AIRPORT, DATE, A_SEAT_MAP);

        verify(flightRepository).save(passengerFlight);
    }

    @Test(expected = FlightNotFoundException.class)
    public void givenAnInValidFlightIdentifier_whenReleasingPlacesForFlight_thenUpdateFlight() throws FlightNotFoundException {
        willReturn(Optional.empty()).given(flightQueryBuilder).findOnePassengerFlight();

        flightService.releasePlacesInFlight(AIRLINE_COMPANY, ARRIVAL_AIRPORT, DATE, A_SEAT_MAP);
    }

    @Test(expected = InvalidParameterException.class)
    public void givenAMissingDepartureAirport_whenFindingAllFlightsWithFilters_then400IsThrown() {
        String departureAirport = null;

        flightService.findAllWithFilters(departureAirport, ARRIVAL_AIRPORT, DATE, WEIGHT, ONLY_AIRVIVANT, ACCEPTS_AIRCARGO);
    }

    @Test(expected = InvalidParameterException.class)
    public void givenAMissingArrivalAirport_whenFindingAllFlightsWithFilters_then400IsThrown() {
        String arrivalAirport = null;

        flightService.findAllWithFilters(DEPARTURE_AIRPORT, arrivalAirport, DATE, WEIGHT, ONLY_AIRVIVANT, ACCEPTS_AIRCARGO);
    }

    @Test(expected = InvalidParameterException.class)
    public void givenAMissingWeight_whenFindingAllFlightsWithFilters_then400IsThrown() {
        double weight = 0;

        flightService.findAllWithFilters(DEPARTURE_AIRPORT, ARRIVAL_AIRPORT, DATE, weight, ONLY_AIRVIVANT, ACCEPTS_AIRCARGO);
    }
}