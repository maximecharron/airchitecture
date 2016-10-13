package ca.ulaval.glo4003.air.domain.weightdetection;

import ca.ulaval.glo4003.air.api.weightdetection.dto.WeightDetectionDto;

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
