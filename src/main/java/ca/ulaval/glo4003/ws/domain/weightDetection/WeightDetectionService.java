package ca.ulaval.glo4003.ws.domain.weightDetection;

import ca.ulaval.glo4003.ws.api.weightDetection.dto.WeightDetectionDto;

public class WeightDetectionService {
    private WeightDetector weightDetector;
    private WeightDetectionAssembler weightDetectionAssembler;

    public WeightDetectionService(WeightDetector weightDetector, WeightDetectionAssembler weightDetectionAssembler){
        this.weightDetector = weightDetector;
        this.weightDetectionAssembler = weightDetectionAssembler;
    }

    public WeightDetectionDto detectWeight() {
        return weightDetectionAssembler.create(this.weightDetector.detect());
    }
}
