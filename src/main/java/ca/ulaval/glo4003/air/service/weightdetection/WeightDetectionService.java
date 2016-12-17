package ca.ulaval.glo4003.air.service.weightdetection;

import ca.ulaval.glo4003.air.domain.weightdetection.WeightDetector;
import ca.ulaval.glo4003.air.transfer.weightdetection.WeightDetectionAssembler;
import ca.ulaval.glo4003.air.transfer.weightdetection.dto.WeightDetectionDto;

public class WeightDetectionService {

    private final WeightDetector weightDetector;
    private final WeightDetectionAssembler weightDetectionAssembler;

    public WeightDetectionService(WeightDetector weightDetector, WeightDetectionAssembler weightDetectionAssembler) {
        this.weightDetector = weightDetector;
        this.weightDetectionAssembler = weightDetectionAssembler;
    }

    public WeightDetectionDto detectWeight() {
        double weight = weightDetector.detect();
        return weightDetectionAssembler.create(weight);
    }
}
