package ca.ulaval.glo4003.air.domain.weightdetection;

public class WeightDetectionService {

    private final WeightDetector weightDetector;

    public WeightDetectionService(WeightDetector weightDetector) {
        this.weightDetector = weightDetector;
    }

    public double detectWeight() {
        return weightDetector.detect();
    }
}
