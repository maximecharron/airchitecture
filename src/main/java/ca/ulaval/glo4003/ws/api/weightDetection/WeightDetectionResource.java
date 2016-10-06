package ca.ulaval.glo4003.ws.api.weightDetection;

import ca.ulaval.glo4003.ws.api.weightDetection.dto.WeightDetectionDto;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/weightDetection")
public interface WeightDetectionResource {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    WeightDetectionDto detectWeight();
}
