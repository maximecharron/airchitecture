package ca.ulaval.glo4003.air.api.flight;

import ca.ulaval.glo4003.air.api.flight.dto.FlightSearchResultDto;
import ca.ulaval.glo4003.air.domain.flight.FlightSearchResult;
import ca.ulaval.glo4003.air.service.flight.FlightService;
import ca.ulaval.glo4003.air.service.flight.InvalidParameterException;
import ca.ulaval.glo4003.air.transfer.flight.FlightAssembler;
import org.eclipse.jetty.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.ws.rs.WebApplicationException;
import java.time.LocalDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;

@RunWith(MockitoJUnitRunner.class)
public class FlightResourceTest {

    private static final String DEPARTURE_AIRPORT = "YQB";
    private static final String ARRIVAL_AIRPORT = "DUB";
    private static final LocalDateTime DATE = LocalDateTime.of(2025, 12, 24, 22, 59);
    private static final String DATE_STRING = DATE.toString();
    private static final double WEIGHT = 30.0;
    private static final String WEIGHT_STRING = "30.0";
    private static final boolean IS_ONLY_AIRVIVANT = true;
    private static final String IS_ONLY_AIRVIVANT_STRING = "true";

    @Mock
    private FlightService flightService;

    @Mock
    private FlightSearchResultDto flightSearchResultDto;

    @Mock
    private FlightSearchResultDto flightSearchResult;

    private FlightResource flightResource;

    @Before
    public void setup() {
        flightResource = new FlightResource(flightService);
    }

    @Test
    public void givenAFlightResource_whenFindingAllFlightsWithFilters_thenItsDelegatedToTheService() {
        given(flightService.findAllWithFilters(DEPARTURE_AIRPORT, ARRIVAL_AIRPORT, DATE, WEIGHT, IS_ONLY_AIRVIVANT)).willReturn(flightSearchResultDto);

        FlightSearchResultDto searchResult = flightResource.findAllWithFilters(DEPARTURE_AIRPORT, ARRIVAL_AIRPORT, DATE_STRING, WEIGHT_STRING, IS_ONLY_AIRVIVANT_STRING);

        assertEquals(flightSearchResultDto, searchResult);
    }

    @Test
    public void givenABadDatetimeQueryParam_whenFindingAllFlightsWithFilters_then400IsThrown() {
        String badlyFormattedDatetime = "2016-06-bacon";

        try {
            flightResource.findAllWithFilters(DEPARTURE_AIRPORT, ARRIVAL_AIRPORT, badlyFormattedDatetime, WEIGHT_STRING, IS_ONLY_AIRVIVANT_STRING);
            fail("Exception not thrown");
        } catch (WebApplicationException e) {
            assertThat(e.getResponse().getStatus(), is(equalTo(HttpStatus.BAD_REQUEST_400)));
        }
    }

    @Test
    public void givenABadWeightQueryParam_whenFindingAllFlightsWithFilters_then400IsThrown() {
        String badlyFormattedWeight = "30.dariusruckerwagonwheel";

        try {
            flightResource.findAllWithFilters(DEPARTURE_AIRPORT, ARRIVAL_AIRPORT, DATE_STRING, badlyFormattedWeight, IS_ONLY_AIRVIVANT_STRING);
            fail("Exception not thrown");
        } catch (WebApplicationException e) {
            assertThat(e.getResponse().getStatus(), is(equalTo(HttpStatus.BAD_REQUEST_400)));
        }
    }
}
