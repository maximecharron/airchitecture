package ca.ulaval.glo4003.ws.infrastructure.flight;

import ca.ulaval.glo4003.ws.domain.flight.Flight;
import ca.ulaval.glo4003.ws.domain.flight.FlightRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.core.IsNot.not;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class FlightRepositoryInMemoryTest {

    private static final String FLIGHT_NUMBER = "AF215";
    private static final String ANOTHER_FLIGHT_NUMBER = "AF216";
    private static final String ARRIVAL_AIRPORT = "ABC";
    private static final String DEPARTURE_AIRPORT = "DEF";
    private static final LocalDateTime DATE = LocalDateTime.of(2020, 10, 2, 6, 30);

    @Mock
    private Flight matchingFlight;

    @Mock
    private Flight notMatchingFlight;

    private FlightRepository flightRepository;

    @Before
    public void setup() {
        flightRepository = new FlightRepositoryInMemory();
        given(matchingFlight.getFlightNumber()).willReturn(FLIGHT_NUMBER);
        given(notMatchingFlight.getFlightNumber()).willReturn(ANOTHER_FLIGHT_NUMBER);
    }

    @Test
    public void givenPersistedFlights_whenFindingAllFlightsWithFilters_thenOnlyMatchingFlightsAreReturned() {
        givenPersistedFlights();

        Stream<Flight> matchingFlightsStream = flightRepository.findAllWithFilters(DEPARTURE_AIRPORT, ARRIVAL_AIRPORT, DATE);
        List<Flight> matchingFlights = matchingFlightsStream.collect(Collectors.toList());

        assertThat(matchingFlights, hasItem(matchingFlight));
        assertThat(matchingFlights, not(hasItem(notMatchingFlight)));
    }

    @Test
    public void givenPersistedFlights_whenFindingAllFlightsWithoutADateFilter_thenOnlyFutureFlightsAreReturned() {
        givenPersistedFlights();

        Stream<Flight> matchingFlightsStream = flightRepository.findAllWithFilters(DEPARTURE_AIRPORT, ARRIVAL_AIRPORT, null);
        List<Flight> matchingFlights = matchingFlightsStream.collect(Collectors.toList());

        assertThat(matchingFlights, hasItem(matchingFlight));
        assertThat(matchingFlights, not(hasItem(notMatchingFlight)));
    }

    private void givenPersistedFlights() {
        given(matchingFlight.isDepartingFrom(DEPARTURE_AIRPORT)).willReturn(true);
        given(matchingFlight.isGoingTo(ARRIVAL_AIRPORT)).willReturn(true);
        given(matchingFlight.isLeavingAfter(DATE)).willReturn(true);
        given(matchingFlight.isFuture()).willReturn(true);

        given(notMatchingFlight.isDepartingFrom(DEPARTURE_AIRPORT)).willReturn(false);
        given(notMatchingFlight.isGoingTo(ARRIVAL_AIRPORT)).willReturn(false);
        given(notMatchingFlight.isLeavingAfter(DATE)).willReturn(false);
        given(notMatchingFlight.isFuture()).willReturn(false);

        flightRepository.save(matchingFlight);
        flightRepository.save(notMatchingFlight);
    }
}