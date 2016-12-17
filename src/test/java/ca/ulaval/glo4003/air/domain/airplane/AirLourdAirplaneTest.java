package ca.ulaval.glo4003.air.domain.airplane;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class AirLourdAirplaneTest {

    private static final SeatMap A_SEAT_MAP = new SeatMap(100, 90, 20);
    private static final boolean A_IS_AIRVIVANT_VALUE = true;
    private static final double AN_ACCEPTED_ADDITIONAL_WEIGHT = 10;
    private static final double A_WEIGHT = 30;
    private static final double A_LOWER_WEIGHT = AirLourdAirplane.MAXIMUM_WEIGHT + AN_ACCEPTED_ADDITIONAL_WEIGHT - 1;
    private static final double AN_HIGHER_WEIGHT = AirLourdAirplane.MAXIMUM_WEIGHT + AN_ACCEPTED_ADDITIONAL_WEIGHT + 1;
    private static final String A_SERIAL_NUMBER = "silent night";
    private static final double A_TOTAL_MAXIMUM_WEIGHT = 10000;

    private AirLourdAirplane airplane;

    @Before
    public void setUp() throws Exception {
        airplane = new AirLourdAirplane(A_SEAT_MAP, AN_ACCEPTED_ADDITIONAL_WEIGHT, A_IS_AIRVIVANT_VALUE, A_SERIAL_NUMBER, A_TOTAL_MAXIMUM_WEIGHT);
    }

    @Test
    public void givenAnyWeight_whenCheckingIfAcceptingWeight_thenItAccepts() {
        boolean result = airplane.acceptsWeight(A_WEIGHT);

        assertTrue(result);
    }

    @Test
    public void whenCheckingIfHasAdditionalWeightOption_thenItDoes() {
        boolean result = airplane.hasAdditionalWeightOption();

        assertTrue(result);
    }

    @Test
    public void givenALowerWeight_whenCheckingIfAcceptsAdditionalWeight_thenItDoes() {
        boolean result = airplane.acceptsAdditionalWeight(A_LOWER_WEIGHT);

        assertTrue(result);
    }

    @Test
    public void givenAnHigherWeight_whenCheckingIfAcceptsAdditionalWeight_thenItDoesNot() {
        boolean result = airplane.acceptsAdditionalWeight(AN_HIGHER_WEIGHT);

        assertFalse(result);
    }

    @Test
    public void whenCheckingIfIsAirLourd_thenItIs() {
        boolean result = airplane.isAirLourd();

        assertTrue(result);
    }
}
