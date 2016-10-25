package ca.ulaval.glo4003.air.domain.weightdetection;

import ca.ulaval.glo4003.air.api.weightdetection.dto.WeightDetectionDto;
import ca.ulaval.glo4003.air.transfer.weightdetection.WeightDetectionAssembler;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class WeightDetectionAssemblerTest {

    private static final double A_WEIGHT = 40.6;

    private WeightDetectionAssembler weightDetectionAssembler;

    @Before
    public void setup() {
        weightDetectionAssembler = new WeightDetectionAssembler();
    }

    @Test
    public void givenAWeight_whenCreatingAWeightDetectionDto_thenItHasAllTheCorrectWeight() {
        WeightDetectionDto weightDetectionDto = weightDetectionAssembler.create(A_WEIGHT);

        assertEquals(A_WEIGHT, weightDetectionDto.weight, 0.01);
    }
}