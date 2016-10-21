package ca.ulaval.glo4003.air.domain.airplane;

import ca.ulaval.glo4003.air.domain.flight.Flight;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDateTime;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class AirplaneWeightTypeTest {

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void givenAnAirLegerWeightType_whenCheckingIfAcceptingWeightThatIsLowerThanMax_thenItAccepts() {
        AirplaneWeightType airLegerWeightType = AirplaneWeightType.AirLeger;
        double weightLowerThanMax = airLegerWeightType.getMaximumWeight() - 1;

        assertTrue(airLegerWeightType.acceptsWeight(weightLowerThanMax));
    }

    @Test
    public void givenAnAirMoyenWeightType_whenCheckingIfAcceptingWeightThatIsLowerThanMax_thenItAccepts() {
        AirplaneWeightType airMoyenWeightType = AirplaneWeightType.AirMoyen;
        double weightLowerThanMax = airMoyenWeightType.getMaximumWeight() - 1;

        assertTrue(airMoyenWeightType.acceptsWeight(weightLowerThanMax));
    }

    @Test
    public void givenAnAirLourdWeightType_whenCheckingIfAcceptingWeightThatIsLowerThanMax_thenItAccepts() {
        AirplaneWeightType airLourdWeightType = AirplaneWeightType.AirLourd;
        double weightLowerThanMax = airLourdWeightType.getMaximumWeight() - 1;

        assertTrue(airLourdWeightType.acceptsWeight(weightLowerThanMax));
    }

    @Test
    public void givenAnAirLegerWeightType_whenCheckingIfAcceptingWeightThatIsHigherThanMax_thenItDoesntAccept() {
        AirplaneWeightType airLegerWeightType = AirplaneWeightType.AirLeger;
        double weightHigherThanMax = airLegerWeightType.getMaximumWeight() + 1;

        assertFalse(airLegerWeightType.acceptsWeight(weightHigherThanMax));
    }

    @Test
    public void givenAnAirMoyenWeightType_whenCheckingIfAcceptingWeightThatIsHigherThanMax_thenItDoesntAccept() {
        AirplaneWeightType airMoyenWeightType = AirplaneWeightType.AirMoyen;
        double weightHigherThanMax = airMoyenWeightType.getMaximumWeight() + 1;

        assertFalse(airMoyenWeightType.acceptsWeight(weightHigherThanMax));
    }

    @Test
    public void givenAnAirLourdWeightType_whenCheckingIfAcceptingWeightThatIsHigherThanMax_thenItAccepts() {
        AirplaneWeightType airLourdWeightType = AirplaneWeightType.AirLourd;
        double weightHigherThanMax = airLourdWeightType.getMaximumWeight() + 1;

        assertTrue(airLourdWeightType.acceptsWeight(weightHigherThanMax));
    }

    @Test
    public void givenAnAirLegerWeightType_whenCheckingIfAcceptingWeightThatIsEqualToMax_thenItAccepts() {
        AirplaneWeightType airLegerWeightType = AirplaneWeightType.AirLeger;
        double weightEqualToMax = airLegerWeightType.getMaximumWeight();

        assertTrue(airLegerWeightType.acceptsWeight(weightEqualToMax));
    }

    @Test
    public void givenAnAirMoyenWeightType_whenCheckingIfAcceptingWeightThatIsEqualToMax_thenItAccepts() {
        AirplaneWeightType airMoyenWeightType = AirplaneWeightType.AirMoyen;
        double weightEqualToMax = airMoyenWeightType.getMaximumWeight();

        assertTrue(airMoyenWeightType.acceptsWeight(weightEqualToMax));
    }

    @Test
    public void givenAnAirLourdWeightType_whenCheckingIfAcceptingWeightThatIsEqualToMax_thenItAccepts() {
        AirplaneWeightType airLourdWeightType = AirplaneWeightType.AirLourd;
        double weightEqualToMax = airLourdWeightType.getMaximumWeight();

        assertTrue(airLourdWeightType.acceptsWeight(weightEqualToMax));
    }
}
