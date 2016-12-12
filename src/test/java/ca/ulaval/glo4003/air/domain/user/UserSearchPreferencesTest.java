package ca.ulaval.glo4003.air.domain.user;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class UserSearchPreferencesTest {

    private UserSearchPreferences userSearchPreferences;
    private static final boolean HAS_SEARCHED_FOR_ECONOMY_CLASS = true;
    private static final boolean HAS_SEARCHED_FOR_REGULAR_CLASS = true;
    private static final boolean HAS_SEARCHED_FOR_BUSINESS_CLASS = true;
    private static final boolean HAS_SEARCHED_FOR_AIR_VIVANT = true;

    private static final boolean HAS_NOT_SEARCHED_FOR_ECONOMY_CLASS = false;
    private static final boolean HAS_NOT_SEARCHED_FOR_REGULAR_CLASS = false;
    private static final boolean HAS_NOT_SEARCHED_FOR_BUSINESS_CLASS = false;
    private static final boolean HAS_NOT_SEARCHED_FOR_AIR_VIVANT = false;

    @Before
    public void setup() {
        userSearchPreferences = new UserSearchPreferences();
    }

    @Test
    public void givenAUserThatHasNotYetExecutedAnySearch_whenCheckingIfHeHasMostlySearchedForAirVivantFlights_thenHeHasNot() throws Exception {
        assertFalse(userSearchPreferences.hasMostlySearchedForAirVivantFlights());
    }

    @Test
    public void givenAUserThatHasNotYetExecutedAnySearch_whenCheckingIfHeHasMostlySearchedForEconomyClassFlights_thenHeHasNot() throws Exception {
        assertFalse(userSearchPreferences.hasMostlySearchedForEconomyClassFlights());
    }

    @Test
    public void givenAUserThatHasNotYetExecutedAnySearch_whenCheckingIfHeHasMostlySearchedForRegularClassFlights_thenHeHasNot() throws Exception {
        assertFalse(userSearchPreferences.hasMostlySearchedForRegularClassFlights());
    }

    @Test
    public void givenAUserThatHasNotYetExecutedAnySearch_whenCheckingIfHeHasMostlySearchedForBusinessClassFlights_thenHeHasNot() throws Exception {
        assertFalse(userSearchPreferences.hasMostlySearchedForBusinessClassFlights());
    }

    @Test
    public void givenAUserThatHasMostlySearchedForAirVivantFlights_whenCheckingIfHeHasMostlySearchedForAirVivantFlights_thenHeHas() throws Exception {
        userSearchPreferences.incrementSearchesPreferences(HAS_SEARCHED_FOR_AIR_VIVANT, HAS_NOT_SEARCHED_FOR_ECONOMY_CLASS, HAS_NOT_SEARCHED_FOR_REGULAR_CLASS, HAS_NOT_SEARCHED_FOR_BUSINESS_CLASS);

        assertTrue(userSearchPreferences.hasMostlySearchedForAirVivantFlights());
    }

    @Test
    public void givenAUserThatHasNotMostlySearchedForAirVivantFlights_whenCheckingIfHeHasMostlySearchedForAirVivantFlights_thenHeHasNot() throws Exception {
        userSearchPreferences.incrementSearchesPreferences(HAS_NOT_SEARCHED_FOR_AIR_VIVANT, HAS_NOT_SEARCHED_FOR_ECONOMY_CLASS, HAS_NOT_SEARCHED_FOR_REGULAR_CLASS, HAS_NOT_SEARCHED_FOR_BUSINESS_CLASS);

        assertFalse(userSearchPreferences.hasMostlySearchedForAirVivantFlights());
    }

    @Test
    public void givenAUserThatHasMostlySearchedForEconomyClassFlights_whenCheckingIfHeHasMostlySearchedForEconomyClassFlights_thenHeHas() throws Exception {
        userSearchPreferences.incrementSearchesPreferences(HAS_NOT_SEARCHED_FOR_AIR_VIVANT, HAS_SEARCHED_FOR_ECONOMY_CLASS, HAS_NOT_SEARCHED_FOR_REGULAR_CLASS, HAS_NOT_SEARCHED_FOR_BUSINESS_CLASS);

        assertTrue(userSearchPreferences.hasMostlySearchedForEconomyClassFlights());
    }

    @Test
    public void givenAUserThatHasNotMostlySearchedForEconomyClassFlights_whenCheckingIfHeHasMostlySearchedForEconomyClassFlights_thenHeHasNot() throws Exception {
        userSearchPreferences.incrementSearchesPreferences(HAS_NOT_SEARCHED_FOR_AIR_VIVANT, HAS_NOT_SEARCHED_FOR_ECONOMY_CLASS, HAS_NOT_SEARCHED_FOR_REGULAR_CLASS, HAS_NOT_SEARCHED_FOR_BUSINESS_CLASS);

        assertFalse(userSearchPreferences.hasMostlySearchedForEconomyClassFlights());
    }

    @Test
    public void givenAUserThatHasMostlySearchedForRegularClassFlights_whenCheckingIfHeHasMostlySearchedForRegularClassFlights_thenHeHas() throws Exception {
        userSearchPreferences.incrementSearchesPreferences(HAS_NOT_SEARCHED_FOR_AIR_VIVANT, HAS_NOT_SEARCHED_FOR_ECONOMY_CLASS, HAS_SEARCHED_FOR_REGULAR_CLASS, HAS_NOT_SEARCHED_FOR_BUSINESS_CLASS);

        assertTrue(userSearchPreferences.hasMostlySearchedForRegularClassFlights());
    }

    @Test
    public void givenAUserThatHasNotMostlySearchedForRegularClassFlights_whenCheckingIfHeHasMostlySearchedForRegularClassFlights_thenHeHasNot() throws Exception {
        userSearchPreferences.incrementSearchesPreferences(HAS_NOT_SEARCHED_FOR_AIR_VIVANT, HAS_NOT_SEARCHED_FOR_ECONOMY_CLASS, HAS_NOT_SEARCHED_FOR_REGULAR_CLASS, HAS_NOT_SEARCHED_FOR_BUSINESS_CLASS);

        assertFalse(userSearchPreferences.hasMostlySearchedForRegularClassFlights());
    }

    @Test
    public void givenAUserThatHasMostlySearchedForBusinessClassFlights_whenCheckingIfHeHasMostlySearchedForBusinessClassFlights_thenHeHas() throws Exception {
        userSearchPreferences.incrementSearchesPreferences(HAS_NOT_SEARCHED_FOR_AIR_VIVANT, HAS_NOT_SEARCHED_FOR_ECONOMY_CLASS, HAS_NOT_SEARCHED_FOR_REGULAR_CLASS, HAS_SEARCHED_FOR_BUSINESS_CLASS);

        assertTrue(userSearchPreferences.hasMostlySearchedForBusinessClassFlights());
    }

    @Test
    public void givenAUserThatHasNotMostlySearchedForBusinessClassFlights_whenCheckingIfHeHasMostlySearchedForBusinessClassFlights_thenHeHasNot() throws Exception {
        userSearchPreferences.incrementSearchesPreferences(HAS_NOT_SEARCHED_FOR_AIR_VIVANT, HAS_NOT_SEARCHED_FOR_ECONOMY_CLASS, HAS_NOT_SEARCHED_FOR_REGULAR_CLASS, HAS_NOT_SEARCHED_FOR_BUSINESS_CLASS);

        assertFalse(userSearchPreferences.hasMostlySearchedForBusinessClassFlights());
    }

}