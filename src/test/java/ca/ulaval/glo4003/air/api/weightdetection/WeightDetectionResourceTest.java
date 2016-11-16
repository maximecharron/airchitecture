package ca.ulaval.glo4003.air.api.weightdetection;

import ca.ulaval.glo4003.air.api.weightdetection.dto.WeightDetectionDto;
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
public class WeightDetectionResourceTest {

    private static final double A_WEIGHT = 35.5;
    private static final WeightDetectionDto WEIGHT_DETECTION_DTO = new WeightDetectionDto();

    @Mock
    private WeightDetectionService weightDetectionService;

    private WeightDetectionResource weightDetectionResourceImpl;

    @Before
    public void setup() {
        weightDetectionResourceImpl = new WeightDetectionResource(weightDetectionService);
    }

    @Test
    public void givenAWeightToDetect_whenDetectingWeight_thenItsDelegatedToTheService() {
        given(weightDetectionService.detectWeight()).willReturn(WEIGHT_DETECTION_DTO);
        WEIGHT_DETECTION_DTO.weight = A_WEIGHT;

        WeightDetectionDto result = weightDetectionResourceImpl.detectWeight();

        assertEquals(result.weight, A_WEIGHT, 0);
    }
}
