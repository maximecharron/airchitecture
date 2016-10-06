package ca.ulaval.glo4003.ws.api.weightDetection;

import ca.ulaval.glo4003.ws.api.weightDetection.dto.WeightDetectionDto;
import ca.ulaval.glo4003.ws.domain.weightDetection.WeightDetectionService;

public class WeightDetectionResourceImpl implements WeightDetectionResource {

    private WeightDetectionService weightDetectionService;

    public WeightDetectionResourceImpl(WeightDetectionService weightDetectionService) {
        this.weightDetectionService = weightDetectionService;
    }

    @Override
    public WeightDetectionDto detectWeight() {
        return weightDetectionService.detectWeight();
    }
}
