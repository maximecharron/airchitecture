package ca.ulaval.glo4003.air.api.weightdetection;

import ca.ulaval.glo4003.air.api.weightdetection.dto.WeightDetectionDto;
import ca.ulaval.glo4003.air.domain.weightdetection.WeightDetectionService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/weightDetection")
public class WeightDetectionResource {

    private WeightDetectionService weightDetectionService;

    public WeightDetectionResource(WeightDetectionService weightDetectionService) {
        this.weightDetectionService = weightDetectionService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public WeightDetectionDto detectWeight() {
        return weightDetectionService.detectWeight();
    }
}
