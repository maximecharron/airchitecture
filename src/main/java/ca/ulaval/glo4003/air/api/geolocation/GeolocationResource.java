package ca.ulaval.glo4003.air.api.geolocation;

import ca.ulaval.glo4003.air.transfer.geolocation.dto.NearestAirportDto;
import ca.ulaval.glo4003.air.service.geolocation.GeolocationService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/geolocation")
public class GeolocationResource {

    private static final String DUMMY_IP = "127.0.0.1";
    private final GeolocationService geolocationService;

    public GeolocationResource(GeolocationService geolocationService) {
        this.geolocationService = geolocationService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public NearestAirportDto findNearestAirport() {
        return geolocationService.findNearestAirport(DUMMY_IP);
    }
}
