package ca.ulaval.glo4003.air.service.geolocation;

import ca.ulaval.glo4003.air.domain.geolocation.Geolocator;
import ca.ulaval.glo4003.air.transfer.geolocation.NearestAirportAssembler;
import ca.ulaval.glo4003.air.transfer.geolocation.dto.NearestAirportDto;

public class GeolocationService {

    private final Geolocator geolocator;
    private final NearestAirportAssembler nearestAirportAssembler;

    public GeolocationService(Geolocator geolocator, NearestAirportAssembler nearestAirportAssembler) {
        this.geolocator = geolocator;
        this.nearestAirportAssembler = nearestAirportAssembler;
    }

    public NearestAirportDto findNearestAirport(String ipAddress) {
        String nearestAirport = geolocator.findNearestAirport(ipAddress);
        return nearestAirportAssembler.create(nearestAirport);
    }

}
