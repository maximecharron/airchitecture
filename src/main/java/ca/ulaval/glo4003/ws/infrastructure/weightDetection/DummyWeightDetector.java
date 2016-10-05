package ca.ulaval.glo4003.ws.infrastructure.weightDetection;

import ca.ulaval.glo4003.ws.domain.weightDetection.WeightDetector;


public class DummyWeightDetector implements WeightDetector {

    private static final double[] POSSIBLE_WEIGHTS = {45.5, 40.8, 56.4};
    private int currentIndex = 0;

    @Override
    public double detect() {
        return POSSIBLE_WEIGHTS[getNextIndex()];
    }

    private int getNextIndex() {
        if (currentIndex == 3) {
            currentIndex = 0;
        }
        return currentIndex++;
    }
}
