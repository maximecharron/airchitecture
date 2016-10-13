package ca.ulaval.glo4003.air.api.weightdetection;

import ca.ulaval.glo4003.air.api.weightdetection.dto.WeightDetectionDto;
import ca.ulaval.glo4003.air.domain.weightdetection.WeightDetectionService;
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
public class WeightDetectionResourceImplTest {
    private final static WeightDetectionDto A_WEIGHT_DTO = new WeightDetectionDto();

    @Mock
    private WeightDetectionService weightDetectionService;

    private WeightDetectionResource weightDetectionResourceImpl;

    @Before
    public void setup() {
        weightDetectionResourceImpl = new WeightDetectionResource(weightDetectionService);
    }

    @Test
    public void givenAWeightToDetect_whenDetectingWeight_thenItsDelegatedToTheService() {
        given(weightDetectionService.detectWeight()).willReturn(A_WEIGHT_DTO);

        WeightDetectionDto result = weightDetectionResourceImpl.detectWeight();

        assertEquals(result, A_WEIGHT_DTO);
    }
}