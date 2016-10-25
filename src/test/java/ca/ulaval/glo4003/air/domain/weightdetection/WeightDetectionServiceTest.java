package ca.ulaval.glo4003.air.domain.weightdetection;

import ca.ulaval.glo4003.air.api.weightdetection.dto.WeightDetectionDto;
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
    private static final WeightDetectionDto A_WEIGHT_DTO = new WeightDetectionDto();

    @Mock
    private WeightDetector weightDetector;

    @Mock
    private WeightDetectionAssembler weightDetectionAssembler;

    private WeightDetectionService weightDetectionService;

    @Before
    public void setup() {
        weightDetectionService = new WeightDetectionService(weightDetector, weightDetectionAssembler);
    }

    @Test
    public void givenAWeightToDetect_whenDetectingWeight_thenReturnDto() {
        given(weightDetector.detect()).willReturn(A_WEIGHT);
        given(weightDetectionAssembler.create(A_WEIGHT)).willReturn(A_WEIGHT_DTO);

        WeightDetectionDto result = weightDetectionService.detectWeight();

        assertEquals(result, A_WEIGHT_DTO);
    }
}