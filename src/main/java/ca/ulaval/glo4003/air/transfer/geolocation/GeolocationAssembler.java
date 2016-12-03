package ca.ulaval.glo4003.air.transfer.geolocation;

import ca.ulaval.glo4003.air.api.geolocation.dto.GeolocationDto;

public class GeolocationAssembler {

    public GeolocationDto create(String ipAddress) {
        GeolocationDto geolocationDto = new GeolocationDto();
        geolocationDto.ipAddress = ipAddress;
        return geolocationDto;
    }
}
