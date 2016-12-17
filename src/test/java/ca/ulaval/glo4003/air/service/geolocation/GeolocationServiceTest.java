package ca.ulaval.glo4003.air.service.geolocation;

import ca.ulaval.glo4003.air.domain.geolocation.Geolocator;
import ca.ulaval.glo4003.air.transfer.geolocation.NearestAirportAssembler;
import ca.ulaval.glo4003.air.transfer.geolocation.dto.NearestAirportDto;
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

        NearestAirportDto result = geolocationService.findNearestAirport(AN_IP_ADDRESS);

        Assert.assertEquals(result.nearestAirport, NEAREST_AIRPORT_IATA);
    }
}
