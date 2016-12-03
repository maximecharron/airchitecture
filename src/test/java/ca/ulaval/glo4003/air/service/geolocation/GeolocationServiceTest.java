package ca.ulaval.glo4003.air.service.geolocation;

import ca.ulaval.glo4003.air.api.geolocation.dto.GeolocationDto;
import ca.ulaval.glo4003.air.api.geolocation.dto.NearestAirportDTO;
import ca.ulaval.glo4003.air.domain.geolocation.Geolocator;
import ca.ulaval.glo4003.air.transfer.geolocation.NearestAirportAssembler;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyString;

@RunWith(MockitoJUnitRunner.class)
public class GeolocationServiceTest {

    private static final String NEAREST_AIRPORT_IATA = "YQB";
    private static final String AN_IP_ADDRESS = "127.0.0.1";

    @Mock
    private Geolocator geolocator;

    private NearestAirportAssembler nearestAirportAssembler;
    private GeolocationService geolocationService;

    @Before
    public void setup() {
        nearestAirportAssembler = new NearestAirportAssembler();
        geolocationService = new GeolocationService(geolocator, nearestAirportAssembler);
    }

    @Test
    public void givenAnIPAddressToGeolocate_whenGeolocalizing_thenTheNearestAirportIsReturned() {
        given(geolocator.findNearestAirport(anyString())).willReturn(NEAREST_AIRPORT_IATA);
        GeolocationDto geolocationDto = new GeolocationDto();
        geolocationDto.ipAddress = AN_IP_ADDRESS;

        NearestAirportDTO result = geolocationService.findNearestAirport(geolocationDto);

        Assert.assertEquals(result.nearestAirportIATA, NEAREST_AIRPORT_IATA);
    }
}
