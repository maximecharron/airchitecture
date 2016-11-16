package ca.ulaval.glo4003.air.service.weightdetection;

import ca.ulaval.glo4003.air.api.weightdetection.dto.WeightDetectionDto;
import ca.ulaval.glo4003.air.domain.weightdetection.WeightDetector;
import ca.ulaval.glo4003.air.service.weightdetection.WeightDetectionService;
import ca.ulaval.glo4003.air.transfer.weightdetection.WeightDetectionAssembler;
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

    private WeightDetectionAssembler weightDetectionAssembler;
    private WeightDetectionService weightDetectionService;

    @Before
    public void setup() {
        weightDetectionAssembler = new WeightDetectionAssembler();
        weightDetectionService = new WeightDetectionService(weightDetector, weightDetectionAssembler);
    }

    @Test
    public void givenAWeightToDetect_whenDetectingWeight_thenTheWeightIsReturned() {
        given(weightDetector.detect()).willReturn(A_WEIGHT);

        WeightDetectionDto weightDetected = weightDetectionService.detectWeight();

        assertEquals(weightDetected.weight, A_WEIGHT, 0);
    }
}
