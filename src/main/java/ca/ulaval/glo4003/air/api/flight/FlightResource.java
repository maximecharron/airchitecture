package ca.ulaval.glo4003.air.api.flight;

import ca.ulaval.glo4003.air.api.flight.dto.FlightSearchDto;
import ca.ulaval.glo4003.air.domain.flight.FlightService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Path("/search/flights")
public class FlightResource {

    private FlightService flightService;

    public FlightResource(FlightService flightService) {
        this.flightService = flightService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public FlightSearchDto findAllWithFilters(@QueryParam("from") String departureAirport,
                                              @QueryParam("to") String arrivalAirport,
                                              @QueryParam("datetime") String departureDate,
                                              @QueryParam("weight") String weight) {
        validateAirportsArePresent(departureAirport, arrivalAirport);
        validateWeightIsPresent(weight);

        LocalDateTime parsedDate = null;

        if (departureDate != null) {
            parsedDate = parseDate(departureDate);
        }

        return flightService.findAllWithFilters(departureAirport, arrivalAirport, parsedDate, parseWeight(weight));
    }

    private void validateAirportsArePresent(String departureAirport, String arrivalAirport) {
        if (departureAirport == null || arrivalAirport == null) {
            throw new WebApplicationException(Response.status(Response.Status.BAD_REQUEST)
                                                      .entity("Missing departure or arrival airport.")
                                                      .build());
        }
    }

    private void validateWeightIsPresent(String weight) {
        if (weight == null) {
            throw new WebApplicationException(Response.status(Response.Status.BAD_REQUEST)
                                                      .entity("Missing luggage weight.")
                                                      .build());
        }
    }

    private LocalDateTime parseDate(String date) {
        try {
            return LocalDateTime.parse(date, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        } catch (DateTimeParseException e) {
            throw new WebApplicationException(Response.status(Response.Status.BAD_REQUEST)
                                                      .entity("Invalid datetime format. " + e.getMessage())
                                                      .build());
        }
    }

    private double parseWeight(String weight) {
        try {
            return Double.parseDouble(weight);
        } catch (NumberFormatException e) {
            throw new WebApplicationException(Response.status(Response.Status.BAD_REQUEST)
                                                      .entity("Invalid weight format. " + e.getMessage())
                                                      .build());
        }
    }
}
