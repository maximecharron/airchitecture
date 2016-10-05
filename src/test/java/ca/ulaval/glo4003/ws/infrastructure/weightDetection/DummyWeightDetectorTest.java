package ca.ulaval.glo4003.ws.infrastructure.weightDetection;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class DummyWeightDetectorTest {

    private DummyWeightDetector dummyWeightDetector;

    @Before
    public void setup() {
        dummyWeightDetector = new DummyWeightDetector();
    }

    @Test
    public void whenDetectingWeight_thenAValidWeightIsReturned() {
        double result = dummyWeightDetector.detect();

        assertThat(result, greaterThanOrEqualTo(0.0));
    }
}
