package ca.ulaval.glo4003.air.api.geolocation;

import ca.ulaval.glo4003.air.service.geolocation.GeolocationService;
import ca.ulaval.glo4003.air.transfer.geolocation.dto.NearestAirportDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyString;

@RunWith(MockitoJUnitRunner.class)
public class GeolocationResourceTest {

    private static final NearestAirportDto NEAREST_AIRPORT_DTO = new NearestAirportDto();
    private static final String NEAREST_AIRPORT_IATA = "YQB";

    @Mock
    private GeolocationService geolocationService;

    private GeolocationResource geolocationResourceImpl;

    @Before
    public void setup() {
        geolocationResourceImpl = new GeolocationResource(geolocationService);
    }

    @Test
    public void givenAnIPAddressToGeolocalize_whenGeolocalizing_thenNearestAirportIsFound() {
        given(geolocationService.findNearestAirport(anyString())).willReturn(NEAREST_AIRPORT_DTO);
        NEAREST_AIRPORT_DTO.nearestAirport = NEAREST_AIRPORT_IATA;

        NearestAirportDto result = geolocationResourceImpl.findNearestAirport();

        assertEquals(result.nearestAirport, NEAREST_AIRPORT_IATA);
    }
}
