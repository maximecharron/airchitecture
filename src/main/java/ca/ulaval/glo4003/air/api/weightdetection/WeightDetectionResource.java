package ca.ulaval.glo4003.air.api.weightdetection;

import ca.ulaval.glo4003.air.api.weightdetection.dto.WeightDetectionDto;
import ca.ulaval.glo4003.air.domain.weightdetection.WeightDetectionService;
import ca.ulaval.glo4003.air.transfer.weightdetection.WeightDetectionAssembler;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/weightDetection")
public class WeightDetectionResource {

    private final WeightDetectionService weightDetectionService;
    private final WeightDetectionAssembler weightDetectionAssembler;

    public WeightDetectionResource(WeightDetectionService weightDetectionService, WeightDetectionAssembler weightDetectionAssembler) {
        this.weightDetectionService = weightDetectionService;
        this.weightDetectionAssembler = weightDetectionAssembler;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public WeightDetectionDto detectWeight() {
        double weight = weightDetectionService.detectWeight();
        return weightDetectionAssembler.create(weight);
    }
}
