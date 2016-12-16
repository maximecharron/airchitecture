package ca.ulaval.glo4003.air.infrastructure.flight;

import ca.ulaval.glo4003.air.domain.flight.Flight;
import ca.ulaval.glo4003.air.domain.flight.FlightQueryBuilder;
import ca.ulaval.glo4003.air.domain.flight.FlightRepository;
import ca.ulaval.glo4003.air.domain.flight.PassengerFlight;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class FlightQueryBuilderInMemoryTest {

    private static final double A_WEIGHT = 40.5;
    private static final boolean A_IS_AIRVIVANT_VALUE = true;
    private static final String ANOTHER_AIRLINE_COMPANY = "AirCharron";
    private static final String INVALID_AIRLINE_COMPANY = "AirFalardeau";
    private static final String AIRLINE_COMPANY = "AirFrenette";
    private static final String ARRIVAL_AIRPORT = "ABC";
    private static final String DEPARTURE_AIRPORT = "DEF";
    private static final LocalDateTime DATE = LocalDateTime.of(2020, 10, 2, 6, 30);

    @Mock
    private PassengerFlight matchingFlight;

    @Mock
    private Flight notMatchingFlight;

    private Map<String, Flight> flights;

    private FlightQueryBuilderInMemory flightQueryBuilderInMemory;

    @Before
    public void setup() {
        flights = new HashMap<>();
        flightQueryBuilderInMemory = new FlightQueryBuilderInMemory(flights);
        givenPersistedFlights();
    }

    @Test
    public void givenPassengerFlightsWithEconomySeats_whenSearchingForFlightsWithEconomySeats_thenOnlyMatchFlightsAreReturned() {
        given(matchingFlight.hasAvailableEconomySeats()).willReturn(true);

        List<PassengerFlight> matchingFlights = flightQueryBuilderInMemory.hasSeatsAvailable(true, false, false).getPassengerFlights();

        assertTrue("no matching flights returned", matchingFlights.size() > 0);
        assertTrue(matchingFlights.stream().allMatch(PassengerFlight::hasAvailableEconomySeats));
    }

    @Test
    public void givenPassengerFlightsWithoutEconomySeats_whenSearchingForFlightsWithEconomySeats_thenOnlyMatchFlightsAreReturned() {
        given(matchingFlight.hasAvailableEconomySeats()).willReturn(false);

        List<PassengerFlight> matchingFlights = flightQueryBuilderInMemory.hasSeatsAvailable(true, true, true).getPassengerFlights();

        assertTrue("A non-supposed matching flight was returned", matchingFlights.size() == 0);
        assertTrue(matchingFlights.stream().allMatch(PassengerFlight::hasAvailableEconomySeats));
    }

    @Test
    public void givenPersistedFlights_whenFindingAllFlightsWithDepartingFromFilter_thenOnlyMatchingFlightsAreReturned() {
        List<PassengerFlight> matchingFlights = flightQueryBuilderInMemory.isDepartingFrom(DEPARTURE_AIRPORT).getPassengerFlights();

        assertTrue(matchingFlights.stream().allMatch(flight -> flight.isDepartingFrom(DEPARTURE_AIRPORT)));
    }

    @Test
    public void givenPersistedFlights_whenFindingAllFlightsWithGoingToFilter_thenOnlyMatchingFlightsAreReturned() {
        List<PassengerFlight> matchingFlights = flightQueryBuilderInMemory.isDepartingFrom(ARRIVAL_AIRPORT).getPassengerFlights();

        assertTrue(matchingFlights.stream().allMatch(flight -> flight.isGoingTo(ARRIVAL_AIRPORT)));
    }

    @Test
    public void givenPersistedFlights_whenFindingAllFlightsWithIsLeavingOnFilter_thenOnlyMatchingFlightsAreReturned() {
        List<PassengerFlight> matchingFlights = flightQueryBuilderInMemory.isLeavingAfterOrOn(DATE).getPassengerFlights();

        assertTrue(matchingFlights.stream().allMatch(flight -> flight.isLeavingAfterOrOn(DATE)));
    }

    @Test
    public void givenPersistedFlights_whenFindingAllFlightsWithIsLeavingAfterOrOnFilter_thenOnlyMatchingFlightsAreReturned() {
        List<PassengerFlight> matchingFlights = flightQueryBuilderInMemory.isLeavingAfterOrOn(DATE).getPassengerFlights();

        assertTrue(matchingFlights.stream().allMatch(flight -> flight.isLeavingAfterOrOn(DATE)));
    }

    @Test
    public void givenPersistedFlights_whenFindingAllFlightsWithAcceptsWeightFilter_thenOnlyMatchingFlightsAreReturned() {
        List<PassengerFlight> matchingFlights = flightQueryBuilderInMemory.acceptsWeight(A_WEIGHT).getPassengerFlights();

        assertTrue(matchingFlights.stream().allMatch(flight -> flight.acceptsWeight(A_WEIGHT)));
    }

    @Test
    public void givenPersistedFlights_whenFindingAllFlightsWithOnlyAirVivantFilter_thenOnlyMatchingFlightsAreReturned() {
        List<PassengerFlight> matchingFlights = flightQueryBuilderInMemory.isAirVivant().getPassengerFlights();

        assertTrue(matchingFlights.stream().allMatch(Flight::isAirVivant));
    }

    @Test
    public void givenPersistedFlights_whenFindingAllFlightsAirlineCompany_thenOnlyMatchingFlightsAreReturned() {
        List<PassengerFlight> matchingFlights = flightQueryBuilderInMemory.hasAirlineCompany(AIRLINE_COMPANY).getPassengerFlights();

        assertTrue(matchingFlights.stream().allMatch(flight -> flight.getAirlineCompany().equals(AIRLINE_COMPANY)));
    }

    @Test
    public void givenPersistedFlights_whenFindingExistingFlight_thenMatchingFlightIsReturned() {
        givenPersistedFlights();

        Optional<PassengerFlight> flight = flightQueryBuilderInMemory.hasAirlineCompany(AIRLINE_COMPANY).isLeavingAfterOrOn(DATE).getOnePassengerFlight();

        assertEquals(matchingFlight, flight.get());
    }

    @Test
    public void givenPersistedFlights_whenFindingNonExistingFlight_thenNullIsReturned() {
        givenPersistedFlights();

        Optional<PassengerFlight> flight = flightQueryBuilderInMemory.hasAirlineCompany(INVALID_AIRLINE_COMPANY).isLeavingAfterOrOn(DATE).getOnePassengerFlight();

        assertFalse(flight.isPresent());
    }

    private void givenPersistedFlights() {
        given(matchingFlight.getAirlineCompany()).willReturn(AIRLINE_COMPANY);
        given(matchingFlight.getArrivalAirport()).willReturn(ARRIVAL_AIRPORT);
        given(matchingFlight.getDepartureDate()).willReturn(DATE);
        given(matchingFlight.isDepartingFrom(DEPARTURE_AIRPORT)).willReturn(true);
        given(matchingFlight.isGoingTo(ARRIVAL_AIRPORT)).willReturn(true);
        given(matchingFlight.isLeavingAfterOrOn(DATE)).willReturn(true);
        given(matchingFlight.isLeavingAfterOrOn(DATE)).willReturn(true);
        given(matchingFlight.isFromCompany(AIRLINE_COMPANY)).willReturn(true);
        given(matchingFlight.acceptsWeight(A_WEIGHT)).willReturn(true);
        given(matchingFlight.isAirVivant()).willReturn(true);
        given(matchingFlight.isPassengerFlight()).willReturn(true);

        given(notMatchingFlight.getAirlineCompany()).willReturn(ANOTHER_AIRLINE_COMPANY);
        given(notMatchingFlight.getArrivalAirport()).willReturn(ARRIVAL_AIRPORT);
        given(notMatchingFlight.getDepartureDate()).willReturn(DATE);
        given(notMatchingFlight.isDepartingFrom(DEPARTURE_AIRPORT)).willReturn(false);
        given(notMatchingFlight.isGoingTo(ARRIVAL_AIRPORT)).willReturn(false);
        given(notMatchingFlight.isLeavingAfterOrOn(DATE)).willReturn(false);
        given(notMatchingFlight.isLeavingAfterOrOn(DATE)).willReturn(false);
        given(notMatchingFlight.isFromCompany(AIRLINE_COMPANY)).willReturn(false);
        given(notMatchingFlight.acceptsWeight(A_WEIGHT)).willReturn(false);
        given(notMatchingFlight.isAirVivant()).willReturn(false);
        given(notMatchingFlight.isPassengerFlight()).willReturn(false);

        flights.put("matching", matchingFlight);
        flights.put("not matching", notMatchingFlight);
    }
}
