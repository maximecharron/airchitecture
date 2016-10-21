package ca.ulaval.glo4003.air.domain.flight;

import ca.ulaval.glo4003.air.api.flight.dto.FlightDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class FlightServiceTest {

    private static final String DEPARTURE_AIRPORT = "YQB";
    private static final String ARRIVAL_AIRPORT = "DUB";
    private static final LocalDateTime DATE = LocalDateTime.of(2020, 10, 10, 21, 45);
    private static double WEIGHT = 40.5;

    @Mock
    private FlightRepository flightRepository;

    @Mock
    private FlightAssembler flightAssembler;

    @Mock
    private Flight flight;

    @Mock
    private FlightDto flightDto;

    private FlightService flightService;

    @Before
    public void setup() {
        flightService = new FlightService(flightRepository, flightAssembler);
    }

    @Test
    public void givenSearchFiltersWithADepartureDate_whenFindingAllMatchingFlights_thenTheRepositoryFindsCorrespondingFlights() {
        flightService.findAllWithFilters(DEPARTURE_AIRPORT, ARRIVAL_AIRPORT, DATE, WEIGHT);

        verify(flightRepository).findAllWithFilters(DEPARTURE_AIRPORT, ARRIVAL_AIRPORT, DATE, WEIGHT);
    }

    @Test
    public void givenSearchFiltersWithNoDepartureDate_whenFindingAllMatchingFlights_thenTheRepositoryFindsFutureFlights() {
        flightService.findAllWithFilters(DEPARTURE_AIRPORT, ARRIVAL_AIRPORT, null, WEIGHT);

        verify(flightRepository).findFuture(DEPARTURE_AIRPORT, ARRIVAL_AIRPORT, WEIGHT);
    }

    @Test
    public void givenPersistedFlights_whenFindingAllFlightsWithFilters_thenReturnDtos() {
        Stream<Flight> flightStream = Stream.of(flight);
        given(flightRepository.findAllWithFilters(DEPARTURE_AIRPORT, ARRIVAL_AIRPORT, DATE, WEIGHT)).willReturn(flightStream);
        given(flightAssembler.create(flightStream, WEIGHT)).willReturn(Collections.singletonList(flightDto));

        List<FlightDto> flightDtos = flightService.findAllWithFilters(DEPARTURE_AIRPORT, ARRIVAL_AIRPORT, DATE, WEIGHT);

        assertThat(flightDtos, hasItem(flightDto));
    }
}