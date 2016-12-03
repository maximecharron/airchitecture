package ca.ulaval.glo4003.air.service.geolocation;

import ca.ulaval.glo4003.air.api.geolocation.dto.GeolocationDto;
import ca.ulaval.glo4003.air.api.geolocation.dto.NearestAirportDTO;
import ca.ulaval.glo4003.air.domain.geolocation.Geolocator;
import ca.ulaval.glo4003.air.transfer.geolocation.NearestAirportAssembler;

public class GeolocationService {

    private final Geolocator geolocator;
    private final NearestAirportAssembler nearestAirportAssembler;

    public GeolocationService(Geolocator geolocator, NearestAirportAssembler nearestAirportAssembler) {
        this.geolocator = geolocator;
        this.nearestAirportAssembler = nearestAirportAssembler;
    }

    public NearestAirportDTO findNearestAirport(GeolocationDto geolocationDto) {
        String nearestAirport = geolocator.findNearestAirport(geolocationDto.ipAddress);
        return nearestAirportAssembler.create(nearestAirport);
    }

}
