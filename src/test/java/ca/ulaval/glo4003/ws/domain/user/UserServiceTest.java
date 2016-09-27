package ca.ulaval.glo4003.ws.domain.user;

import ca.ulaval.glo4003.ws.api.flight.dto.FlightDto;
import ca.ulaval.glo4003.ws.domain.flight.Flight;
import ca.ulaval.glo4003.ws.domain.flight.FlightAssembler;
import ca.ulaval.glo4003.ws.domain.flight.FlightRepository;
import ca.ulaval.glo4003.ws.domain.flight.FlightService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

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
        assert(true);
    }
}