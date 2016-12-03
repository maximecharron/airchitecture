package ca.ulaval.glo4003.air.api.geolocation;

import ca.ulaval.glo4003.air.api.geolocation.dto.GeolocationDto;
import ca.ulaval.glo4003.air.api.geolocation.dto.NearestAirportDTO;
import ca.ulaval.glo4003.air.service.geolocation.GeolocationService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;

@RunWith(MockitoJUnitRunner.class)
public class GeolocationResourceTest {

    private static final NearestAirportDTO NEAREST_AIRPORT_DTO = new NearestAirportDTO();
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
        given(geolocationService.findNearestAirport(any(GeolocationDto.class))).willReturn(NEAREST_AIRPORT_DTO);
        NEAREST_AIRPORT_DTO.nearestAirportIATA = NEAREST_AIRPORT_IATA;

        NearestAirportDTO result = geolocationResourceImpl.findNearestAirport();

        assertEquals(result.nearestAirportIATA, NEAREST_AIRPORT_IATA);
    }
}
