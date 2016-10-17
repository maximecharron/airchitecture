package ca.ulaval.glo4003.air.api.flight;

import ca.ulaval.glo4003.air.api.flight.dto.FlightDto;
import ca.ulaval.glo4003.air.domain.flight.FlightService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@Path("/search/flights")
public class FlightResource {

    private FlightService flightService;

    public FlightResource(FlightService flightService) {
        this.flightService = flightService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<FlightDto> findAllWithFilters(@QueryParam("from") String departureAirport,
                                              @QueryParam("to") String arrivalAirport,
                                              @QueryParam("datetime") String departureDate) {
        validateAirportsArePresent(departureAirport, arrivalAirport);

        LocalDateTime dateTime = null;

        if (departureDate != null) {
            try {
                dateTime = LocalDateTime.parse(departureDate, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            } catch (DateTimeParseException e) {
                throw new WebApplicationException(Response.status(Response.Status.BAD_REQUEST)
                                                          .entity("Invalid datetime format. " + e.getMessage())
                                                          .build());
            }
        }

        return flightService.findAllWithFilters(departureAirport, arrivalAirport, dateTime);
    }

    private void validateAirportsArePresent(String departureAirport, String arrivalAirport) {
        if (departureAirport == null || arrivalAirport == null) {
            throw new WebApplicationException(Response.status(Response.Status.BAD_REQUEST)
                                                      .entity("Missing departure or arrival airport.")
                                                      .build());
        }
    }
}
