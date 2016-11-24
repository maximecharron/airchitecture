package ca.ulaval.glo4003.air.api.flight;

import ca.ulaval.glo4003.air.api.flight.dto.FlightSearchResultDto;
import ca.ulaval.glo4003.air.domain.flight.FlightSearchResult;
import ca.ulaval.glo4003.air.service.flight.FlightService;
import ca.ulaval.glo4003.air.service.flight.InvalidParameterException;
import ca.ulaval.glo4003.air.transfer.flight.FlightAssembler;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Path("/search/flights")
public class FlightResource {

    private final FlightService flightService;

    public FlightResource(FlightService flightService) {
        this.flightService = flightService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public FlightSearchResultDto findAllWithFilters(@QueryParam("from") String departureAirport,
                                                    @QueryParam("to") String arrivalAirport,
                                                    @QueryParam("datetime") String departureDate,
                                                    @QueryParam("weight") String weight,
                                                    @QueryParam("onlyAirVivant") String onlyAirVivant,
                                                    @QueryParam("acceptsAirCargo") String acceptsAirCargo) {
        LocalDateTime parsedDate = null;
        if (departureDate != null) {
            parsedDate = parseDate(departureDate);
        }
        double parsedWeight = 0;
        if (weight != null){
            parsedWeight = parseWeight(weight);
        }
        boolean parsedOnlyAirVivant = false;
        if (onlyAirVivant != null) {
            parsedOnlyAirVivant = Boolean.parseBoolean(onlyAirVivant);
        }
        boolean parsedAcceptsAirCargo = false;
        if (acceptsAirCargo != null) {
            parsedAcceptsAirCargo = Boolean.parseBoolean(acceptsAirCargo);
        }
        try {
            return flightService.findAllWithFilters(departureAirport, arrivalAirport, parsedDate, parsedWeight, parsedOnlyAirVivant, parsedAcceptsAirCargo);
        } catch (InvalidParameterException e){
            throw new WebApplicationException(Response.status(Response.Status.BAD_REQUEST)
                                                      .entity(e.getMessage())
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
