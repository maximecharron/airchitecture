package ca.ulaval.glo4003.air.infrastructure.geolocation;

import ca.ulaval.glo4003.air.domain.geolocation.Geolocator;

import java.util.Random;

public class DummyAirportGeolocator implements Geolocator {

    private static final String STAB_CITY_AIRPORT = "SNN";
    private static final String SAINT_SAUVEUR_AIRPORT = "YQB";
    private static final String KNIFE_WIELDING_TENTACLE_AIRPORT = "DUB";

    private static final String[] DANGEROUS_AIRPORTS = {STAB_CITY_AIRPORT, SAINT_SAUVEUR_AIRPORT, KNIFE_WIELDING_TENTACLE_AIRPORT};


    @Override
    public String findNearestAirport(String ipAddress) {
        int randomAirport = new Random().nextInt(DANGEROUS_AIRPORTS.length);
        return DANGEROUS_AIRPORTS[randomAirport];
    }
}
