package ca.ulaval.glo4003.air.api.flight;

import ca.ulaval.glo4003.air.api.flight.dto.FlightDto;
import ca.ulaval.glo4003.air.api.flight.dto.FlightSearchDto;
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
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class FlightResourceTest {

    private static final String DEPARTURE_AIRPORT = "YQB";
    private static final String ARRIVAL_AIRPORT = "DUB";
    private static final LocalDateTime DATE = LocalDateTime.of(2025, 12, 24, 22, 59);
    private static final String DATE_STRING = DATE.toString();
    private static final double WEIGHT = 30.0;
    private static final String WEIGHT_STRING = "30.0";

    @Mock
    private FlightService flightService;

    @Mock
    private FlightSearchDto flightSearchDto;

    private FlightResource flightResource;

    @Before
    public void setup() {
        flightResource = new FlightResource(flightService);
    }

    @Test
    public void givenAFlightResource_whenFindingAllFlightsWithFilters_thenItsDelegatedToTheService() {
        given(flightService.findAllWithFilters(DEPARTURE_AIRPORT, ARRIVAL_AIRPORT, DATE, WEIGHT)).willReturn(flightSearchDto);

        FlightSearchDto flightSearchDto = flightResource.findAllWithFilters(DEPARTURE_AIRPORT, ARRIVAL_AIRPORT, DATE_STRING, WEIGHT_STRING);

        assertEquals(this.flightSearchDto, flightSearchDto);
    }

    @Test
    public void givenABadDatetimeQueryParam_whenFindingAllFlightsWithFilters_then400IsThrown() {
        String badlyFormattedDatetime = "2016-06-bacon";

        try {
            flightResource.findAllWithFilters(DEPARTURE_AIRPORT, ARRIVAL_AIRPORT, badlyFormattedDatetime, WEIGHT_STRING);
            fail("Exception not thrown");
        } catch(WebApplicationException e) {
            assertThat(e.getResponse().getStatus(), is(equalTo(HttpStatus.BAD_REQUEST_400)));
        }
    }

    @Test
    public void givenAMissingDepartureAirport_whenFindingAllFlightsWithFilters_then400IsThrown() {
        String departureAirport = null;

        try {
            flightResource.findAllWithFilters(departureAirport, ARRIVAL_AIRPORT, DATE_STRING, WEIGHT_STRING);
            fail("Exception not thrown");
        } catch(WebApplicationException e) {
            assertThat(e.getResponse().getStatus(), is(equalTo(HttpStatus.BAD_REQUEST_400)));
        }
    }

    @Test
    public void givenAMissingArrivalAirport_whenFindingAllFlightsWithFilters_then400IsThrown() {
        String arrivalAirport = null;

        try {
            flightResource.findAllWithFilters(DEPARTURE_AIRPORT, arrivalAirport, DATE_STRING, WEIGHT_STRING);
            fail("Exception not thrown");
        } catch(WebApplicationException e) {
            assertThat(e.getResponse().getStatus(), is(equalTo(HttpStatus.BAD_REQUEST_400)));
        }
    }

    @Test
    public void givenAMissingWeight_whenFindingAllFlightsWithFilters_then400IsThrown() {
        String weight = null;

        try {
            flightResource.findAllWithFilters(DEPARTURE_AIRPORT, ARRIVAL_AIRPORT, DATE_STRING, weight);
            fail("Exception not thrown");
        } catch(WebApplicationException e) {
            assertThat(e.getResponse().getStatus(), is(equalTo(HttpStatus.BAD_REQUEST_400)));
        }
    }

    @Test
    public void givenABadWeightQueryParam_whenFindingAllFlightsWithFilters_then400IsThrown() {
        String badlyFormattedWeight = "30.dariusruckerwagonwheel";

        try {
            flightResource.findAllWithFilters(DEPARTURE_AIRPORT, ARRIVAL_AIRPORT, DATE_STRING, badlyFormattedWeight);
            fail("Exception not thrown");
        } catch(WebApplicationException e) {
            assertThat(e.getResponse().getStatus(), is(equalTo(HttpStatus.BAD_REQUEST_400)));
        }
    }
}
