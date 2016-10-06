package ca.ulaval.glo4003.ws.domain.flight;

import ca.ulaval.glo4003.ws.api.flight.dto.FlightDto;
import jersey.repackaged.com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.Arrays;
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
    public void givenPersistedFlights_whenFindingAllFlightsWithFilters_thenReturnDtos() {
        Stream<Flight> flightStream = Stream.of(this.flight);
        given(flightRepository.findAllWithFilters(DEPARTURE_AIRPORT, ARRIVAL_AIRPORT, DATE)).willReturn(flightStream);
        given(flightAssembler.create(flightStream)).willReturn(Collections.singletonList(flightDto));

        List<FlightDto> flightDtos = flightService.findAllWithFilters(DEPARTURE_AIRPORT, ARRIVAL_AIRPORT, DATE);

        verify(flightRepository).findAllWithFilters(DEPARTURE_AIRPORT, ARRIVAL_AIRPORT, DATE);
        verify(flightAssembler).create(flightStream);
        assertThat(flightDtos, hasItem(flightDto));
    }
}