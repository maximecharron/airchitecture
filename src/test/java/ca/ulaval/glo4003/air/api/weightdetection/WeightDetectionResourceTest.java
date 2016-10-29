package ca.ulaval.glo4003.air.api.weightdetection;

import ca.ulaval.glo4003.air.api.weightdetection.dto.WeightDetectionDto;
import ca.ulaval.glo4003.air.domain.weightdetection.WeightDetectionService;
import ca.ulaval.glo4003.air.transfer.weightdetection.WeightDetectionAssembler;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class WeightDetectionResourceTest {

    private static final double A_WEIGHT = 35.5;
    private static final WeightDetectionDto WEIGHT_DETECTION_DTO = new WeightDetectionDto();

    @Mock
    private WeightDetectionService weightDetectionService;

    @Mock
    private WeightDetectionAssembler weightDetectionAssembler;

    private WeightDetectionResource weightDetectionResourceImpl;

    @Before
    public void setup() {
        weightDetectionResourceImpl = new WeightDetectionResource(weightDetectionService, weightDetectionAssembler);
    }

    @Test
    public void givenAWeightToDetect_whenDetectingWeight_thenItsDelegatedToTheService() {
        given(weightDetectionService.detectWeight()).willReturn(A_WEIGHT);
        WEIGHT_DETECTION_DTO.weight = A_WEIGHT;
        given(weightDetectionAssembler.create(A_WEIGHT)).willReturn(WEIGHT_DETECTION_DTO);

        WeightDetectionDto result = weightDetectionResourceImpl.detectWeight();

        assertEquals(result.weight, A_WEIGHT, 0);
    }
}
