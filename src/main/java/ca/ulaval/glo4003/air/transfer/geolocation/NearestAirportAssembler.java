package ca.ulaval.glo4003.air.transfer.geolocation;

import ca.ulaval.glo4003.air.api.geolocation.dto.NearestAirportDTO;

public class NearestAirportAssembler {

    public NearestAirportDTO create(String airportIATA) {
        NearestAirportDTO nearestAirportDTO = new NearestAirportDTO();
        nearestAirportDTO.nearestAirportIATA = airportIATA;
        return nearestAirportDTO;
    }
}
