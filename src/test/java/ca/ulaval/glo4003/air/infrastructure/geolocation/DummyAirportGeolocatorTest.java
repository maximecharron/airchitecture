package ca.ulaval.glo4003.air.infrastructure.geolocation;

import org.junit.Before;
import org.junit.Test;

public class DummyAirportGeolocatorTest {

    private static final String AN_IP_ADDRESS = "127.0.0.1";
    private DummyAirportGeolocator dummyAirportGeolocator;

    @Before
    public void setup() {
        dummyAirportGeolocator = new DummyAirportGeolocator();
    }

    @Test
    public void whenGeolocating_thenAValidAirportIATAIsReturned() {
        String result = this.dummyAirportGeolocator.findNearestAirport(AN_IP_ADDRESS);

        assert (result.length() == 3);
    }
}
