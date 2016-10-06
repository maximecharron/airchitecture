package ca.ulaval.glo4003.ws.domain.weightDetection;

import ca.ulaval.glo4003.ws.api.weightDetection.dto.WeightDetectionDto;
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