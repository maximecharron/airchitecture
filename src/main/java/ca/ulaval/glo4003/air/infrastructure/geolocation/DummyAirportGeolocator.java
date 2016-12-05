package ca.ulaval.glo4003.air.infrastructure.geolocation;

import ca.ulaval.glo4003.air.domain.geolocation.Geolocator;

import java.util.Random;

public class DummyAirportGeolocator implements Geolocator {

    private static final String SHANNON_AIRPORT = "SNN";
    private static final String QUEBEC_AIRPORT = "YQB";
    private static final String DUBLIN_AIRPORT = "DUB";

    private static final String[] SOME_AIRPORTS = {SHANNON_AIRPORT, QUEBEC_AIRPORT, DUBLIN_AIRPORT};


    @Override
    public String findNearestAirport(String ipAddress) {
        int randomAirport = new Random().nextInt(SOME_AIRPORTS.length);
        return SOME_AIRPORTS[randomAirport];
    }
}
