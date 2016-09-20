package ca.ulaval.glo4003.ws.api.flight;

import ca.ulaval.glo4003.ws.api.flight.dto.FlightDto;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.time.LocalDateTime;
import java.util.List;

@Path("/search/flights")
public interface FlightResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    List<FlightDto> findAllWithFilters(@QueryParam("from") String departureAirport,
                                       @QueryParam("to") String arrivalAirport,
                                       @QueryParam("datetime") String departureDate);
}
