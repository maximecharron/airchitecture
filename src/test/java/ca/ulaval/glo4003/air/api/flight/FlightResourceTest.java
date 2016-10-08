package ca.ulaval.glo4003.air.api.flight;

import ca.ulaval.glo4003.air.api.flight.dto.FlightDto;
import ca.ulaval.glo4003.air.domain.flight.FlightService;
import jersey.repackaged.com.google.common.collect.Lists;
import org.eclipse.jetty.http.HttpStatus;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.ws.rs.WebApplicationException;
import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.fail;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class FlightResourceTest {

    private static final String DEPARTURE_AIRPORT = "YQB";
    private static final String ARRIVAL_AIRPORT = "DUB";
    private static final LocalDateTime DATE = LocalDateTime.of(2025, 12, 24, 22, 59);
    private static final String DATE_STRING = DATE.toString();

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Mock
    private FlightService flightService;

    @Mock
    private FlightDto flightDto;

    private FlightResource flightResource;

    @Before
    public void setup() {
        flightResource = new FlightResource(flightService);
    }

    @Test
    public void givenAFlightController_whenFindingAllFlightsWithFilters_thenItsDelegatedToTheService() {
        given(flightService.findAllWithFilters(DEPARTURE_AIRPORT, ARRIVAL_AIRPORT, DATE)).willReturn(Lists.newArrayList(flightDto));

        List<FlightDto> flightDtos = flightResource.findAllWithFilters(DEPARTURE_AIRPORT, ARRIVAL_AIRPORT, DATE_STRING);

        verify(flightService).findAllWithFilters(DEPARTURE_AIRPORT, ARRIVAL_AIRPORT, DATE);
        assertThat(flightDtos, hasItem(flightDto));
    }

    @Test
    public void givenABadDatetimeQueryParam_whenFindingAllFlightsWithFilters_then400IsThrown() {
        String badlyFormattedDatetime = "2016-06-bacon";

        try {
            flightResource.findAllWithFilters(DEPARTURE_AIRPORT, ARRIVAL_AIRPORT, badlyFormattedDatetime);
            fail("Exception not thrown");
        } catch(WebApplicationException e) {
            assertThat(e.getResponse().getStatus(), is(equalTo(HttpStatus.BAD_REQUEST_400)));
        }
    }
}