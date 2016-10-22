package ca.ulaval.glo4003.air.domain.airplane;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class AirMoyenAirplaneTest {
    private static final int A_NUMBER_OF_AVAILABLE_SEATS = 50;
    private static final double A_WEIGHT = 30;
    private static final double A_LOWER_WEIGHT = AirMoyenAirplane.MAXIMUM_WEIGHT - 1;
    private static final double AN_HIGHER_WEIGHT = AirMoyenAirplane.MAXIMUM_WEIGHT + 1;

    private AirMoyenAirplane airplane;

    @Before
    public void setUp() throws Exception {
        airplane = new AirMoyenAirplane(A_NUMBER_OF_AVAILABLE_SEATS);
    }

    @Test
    public void givenALowerWeight_whenCheckingIfAcceptingWeight_thenItAccepts() {
        boolean result = airplane.acceptsWeight(A_LOWER_WEIGHT);

        assertTrue(result);
    }

    @Test
    public void givenAnHigherWeight_whenCheckingIfAcceptingWeight_thenItDoesNotAccept() {
        boolean result = airplane.acceptsWeight(AN_HIGHER_WEIGHT);

        assertFalse(result);
    }

    @Test
    public void whenCheckingIfCanAcceptAdditionalWeight_thenItDoesNot() {
        boolean result = airplane.canAcceptAdditionalWeight();

        assertFalse(result);
    }

    @Test
    public void givenAnyWeight_whenCheckingIfAcceptsAdditionalWeight_thenItDoesNot() {
        boolean result = airplane.acceptsAdditionalWeight(A_WEIGHT);

        assertFalse(result);
    }
}
