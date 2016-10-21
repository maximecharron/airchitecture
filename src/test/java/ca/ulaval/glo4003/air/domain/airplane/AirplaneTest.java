package ca.ulaval.glo4003.air.domain.airplane;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class AirplaneTest {

    private static final String AN_AIRPLANE_NUMBER = "rockmemamma";
    private static final double A_WEIGHT = 40.5;
    private static final boolean AN_ACCEPTING_WEIGHT_RESULT = true;
    private static final int A_NUMBER_OF_AVAILABLE_SEATS = 50;
    private static final double A_MAXIMUM_ADDITIONAL_WEIGHT = 10;

    @Mock
    private AirplaneWeightType airplaneWeightType;

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void givenAnAirplane_whenCheckingIfAcceptingWeight_thenItsDelegatedToTheWeightType() {
        Airplane airplane = givenAnAirplane();
        given(airplaneWeightType.acceptsWeight(A_WEIGHT)).willReturn(AN_ACCEPTING_WEIGHT_RESULT);

        boolean result = airplane.acceptsWeight(A_WEIGHT);

        assertEquals(result, AN_ACCEPTING_WEIGHT_RESULT);
    }


    @Test
    public void givenAnAirplaneWhichIsNotAirLourd_whenCheckingIfAcceptingAdditionalWeight_thenItReturnsFalse() {
        Airplane airplane = givenANotAirLourdAirplane();

        boolean result = airplane.acceptsAdditionalWeight(A_WEIGHT);

        assertFalse(result);
    }

    @Test
    public void givenAnAirplaneWhichIsAirLourd_whenCheckingIfAcceptingAdditionalWeightWithWeightLowerThanTotalMaximumWeight_thenItReturnsTrue() {
        Airplane airplane = givenAnAirLourdAirplane();
        double totalMaximumWeight = AirplaneWeightType.AirLourd.getMaximumWeight() + A_MAXIMUM_ADDITIONAL_WEIGHT;

        boolean result = airplane.acceptsAdditionalWeight(totalMaximumWeight - 1);

        assertTrue(result);
    }

    @Test
    public void givenAnAirplaneWhichIsAirLourd_whenCheckingIfAcceptingAdditionalWeightWithWeightHigherThanTotalMaximumWeight_thenItReturnsFalse() {
        Airplane airplane = givenAnAirLourdAirplane();
        double totalMaximumWeight = AirplaneWeightType.AirLourd.getMaximumWeight() + A_MAXIMUM_ADDITIONAL_WEIGHT;

        boolean result = airplane.acceptsAdditionalWeight(totalMaximumWeight + 1);

        assertFalse(result);
    }

    private Airplane givenAnAirplane() {
        return new Airplane(A_NUMBER_OF_AVAILABLE_SEATS, airplaneWeightType);
    }

    private Airplane givenANotAirLourdAirplane() {
        return new Airplane(A_NUMBER_OF_AVAILABLE_SEATS, AirplaneWeightType.AirLeger);
    }

    private Airplane givenAnAirLourdAirplane() {
        return new Airplane(A_NUMBER_OF_AVAILABLE_SEATS, AirplaneWeightType.AirLourd, A_MAXIMUM_ADDITIONAL_WEIGHT);
    }
}
