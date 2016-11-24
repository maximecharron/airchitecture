package ca.ulaval.glo4003.air.domain.airplane;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class AirMoyenAirplaneTest {

    private static final boolean A_IS_AIRVIVANT_VALUE = true;
    private static final double A_WEIGHT = 30;
    private static final double A_LOWER_WEIGHT = AirMoyenAirplane.MAXIMUM_WEIGHT - 1;
    private static final double AN_HIGHER_WEIGHT = AirMoyenAirplane.MAXIMUM_WEIGHT + 1;
    private static final String A_SERIAL_NUMBER = "rudolph the red nosed reindeer";
    private static final int ECONOMIC_SEATS = 20;
    private static final int REGULAR_SEATS = 10;
    private static final int BUSINESS_SEATS = 5;

    private AirMoyenAirplane airplane;

    @Before
    public void setUp() throws Exception {
        SeatMap seatMap = new SeatMap(ECONOMIC_SEATS, REGULAR_SEATS, BUSINESS_SEATS);
        airplane = new AirMoyenAirplane(seatMap, A_IS_AIRVIVANT_VALUE, A_SERIAL_NUMBER);
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
    public void whenCheckingIfHasAdditionalWeightOption_thenItDoesNot() {
        boolean result = airplane.hasAdditionalWeightOption();

        assertFalse(result);
    }

    @Test
    public void givenAnyWeight_whenCheckingIfAcceptsAdditionalWeight_thenItDoesNot() {
        boolean result = airplane.acceptsAdditionalWeight(A_WEIGHT);

        assertFalse(result);
    }

    @Test
    public void whenCheckingIfIsAirLourd_thenItIsNot() {
        boolean result = airplane.isAirLourd();

        assertFalse(result);
    }
}
