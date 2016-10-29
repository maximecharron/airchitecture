package ca.ulaval.glo4003.air.domain.weightdetection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class WeightDetectionServiceTest {

    private static final double A_WEIGHT = 45.5;

    @Mock
    private WeightDetector weightDetector;

    private WeightDetectionService weightDetectionService;

    @Before
    public void setup() {
        weightDetectionService = new WeightDetectionService(weightDetector);
    }

    @Test
    public void givenAWeightToDetect_whenDetectingWeight_thenTheWeightIsReturned() {
        given(weightDetector.detect()).willReturn(A_WEIGHT);

        double weightDetected = weightDetectionService.detectWeight();

        assertEquals(weightDetected, A_WEIGHT, 0);
    }
}
