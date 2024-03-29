package ca.ulaval.glo4003.air.api.flight;

import ca.ulaval.glo4003.air.service.flight.FlightService;
import ca.ulaval.glo4003.air.service.flight.InvalidParameterException;
import ca.ulaval.glo4003.air.transfer.flight.FlightSearchQueryAssembler;
import ca.ulaval.glo4003.air.transfer.flight.dto.FlightSearchQueryDto;
import ca.ulaval.glo4003.air.transfer.flight.dto.FlightSearchResultDto;

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
                                                    @QueryParam("weight") double weight,
                                                    @QueryParam("onlyAirVivant") boolean onlyAirVivant,
                                                    @QueryParam("acceptsAirCargo") boolean acceptsAirCargo,
                                                    @QueryParam("hasEconomySeats") boolean hasEconomySeats,
                                                    @QueryParam("hasRegularSeats") boolean hasRegularSeats,
                                                    @QueryParam("hasBusinessSeats") boolean hasBusinessSeats,
                                                    @HeaderParam("X-Access-Token") String accessToken) {
        LocalDateTime parsedDate = null;
        if (departureDate != null) {
            parsedDate = parseDate(departureDate);
        }
        try {
            FlightSearchQueryDto flightSearchQueryDto = new FlightSearchQueryAssembler().create(departureAirport,
                arrivalAirport, parsedDate,
                weight, onlyAirVivant, acceptsAirCargo,
                hasEconomySeats, hasRegularSeats, hasBusinessSeats);

            return flightService.findAllWithFilters(accessToken, flightSearchQueryDto);
        } catch (InvalidParameterException e) {
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
}
