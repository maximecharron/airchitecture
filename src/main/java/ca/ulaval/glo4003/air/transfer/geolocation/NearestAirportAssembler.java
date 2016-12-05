package ca.ulaval.glo4003.air.transfer.geolocation;

import ca.ulaval.glo4003.air.api.geolocation.dto.NearestAirportDto;

public class NearestAirportAssembler {

    public NearestAirportDto create(String airportIATA) {
        NearestAirportDto nearestAirportDto = new NearestAirportDto();
        nearestAirportDto.nearestAirport = airportIATA;
        return nearestAirportDto;
    }
}
