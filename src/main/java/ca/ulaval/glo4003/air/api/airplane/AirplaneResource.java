package ca.ulaval.glo4003.air.api.airplane;

import ca.ulaval.glo4003.air.api.airplane.dto.AirplaneDto;
import ca.ulaval.glo4003.air.api.airplane.dto.AirplaneSearchResultDto;
import ca.ulaval.glo4003.air.api.airplane.dto.AirplaneUpdateDto;
import ca.ulaval.glo4003.air.domain.airplane.AirplaneNotFoundException;
import ca.ulaval.glo4003.air.domain.user.InvalidTokenException;
import ca.ulaval.glo4003.air.domain.user.UnauthorizedException;
import ca.ulaval.glo4003.air.service.airplane.AirplaneService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.logging.Logger;

@Path("/airplanes")
public class AirplaneResource {

    private final Logger logger = Logger.getLogger(AirplaneResource.class.getName());

    private final AirplaneService airplaneService;

    public AirplaneResource(AirplaneService airplaneService) {
        this.airplaneService = airplaneService;
    }

    @GET
    @Path("/search")
    @Produces(MediaType.APPLICATION_JSON)
    public AirplaneSearchResultDto findAllWithFilters(@QueryParam("needsToBeAirLourd") boolean needsToBeAirLourd) {
        return airplaneService.findAllWithFilters(needsToBeAirLourd);
    }

    @PUT
    @Path("/{serialNumber}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public AirplaneDto update(AirplaneUpdateDto airplaneUpdateDto, @PathParam("serialNumber") String serialNumber, @HeaderParam("X-Access-Token") String token) {
        try {
            return this.airplaneService.updateAirplane(token, serialNumber, airplaneUpdateDto);
        } catch (InvalidTokenException | UnauthorizedException e) {
            logger.info("Airplane update failed because: " + e.getMessage());
            throw new WebApplicationException(Response.status(Response.Status.UNAUTHORIZED)
                    .entity("Token is invalid or the user does not have the required permissions.")
                    .build());
        } catch (AirplaneNotFoundException e) {
            logger.info("Airplane update failed because: " + e.getMessage());
            throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND)
                    .entity("This airplane does not exist.")
                    .build());
        }
    }
}
