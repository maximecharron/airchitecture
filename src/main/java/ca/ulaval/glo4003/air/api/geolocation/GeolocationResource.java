package ca.ulaval.glo4003.air.api.geolocation;

import ca.ulaval.glo4003.air.api.geolocation.dto.GeolocationDto;
import ca.ulaval.glo4003.air.api.geolocation.dto.NearestAirportDTO;
import ca.ulaval.glo4003.air.service.geolocation.GeolocationService;
import ca.ulaval.glo4003.air.transfer.geolocation.GeolocationAssembler;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/geolocation")
public class GeolocationResource {

    private final GeolocationService geolocationService;

    public GeolocationResource(GeolocationService geolocationService) {
        this.geolocationService = geolocationService;
    }

    @GET
    @Path("findNearestAirport")
    @Produces(MediaType.APPLICATION_JSON)
    public NearestAirportDTO findNearestAirport() {
        /*
        Would probably use a parameter like "@Context HttpServletRequest req" in real world scenario,
        or a token passed by the load-balancer/reverse proxy.
        But in our case this is completely irrelevant. So simply use static localhost IP address.
         */
        GeolocationDto geolocationDto = new GeolocationAssembler().create("127.0.0.1");

        return geolocationService.findNearestAirport(geolocationDto);
    }
}
