package ca.ulaval.glo4003.air.domain.flight;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class WeightFilterVerifierTest {

    private final static int NOT_FILTERED_BY_WEIGHTS_SIZE = 12;

    @Mock
    private List<Flight> filteredByWeightsFlights;

    @Mock
    private List<Flight> notFilteredByWeightsFlights;

    private WeightFilterVerifier weightFilterVerifier;

    @Before
    public void setUp() throws Exception {
        given(notFilteredByWeightsFlights.size()).willReturn(NOT_FILTERED_BY_WEIGHTS_SIZE);
        weightFilterVerifier = new WeightFilterVerifier();
    }

    @Test
    public void givenALowerFilteredByWeightsList_whenVerifyingIfItWasFiltered_thenItIs() {
        given(filteredByWeightsFlights.size()).willReturn(NOT_FILTERED_BY_WEIGHTS_SIZE - 1);

        boolean result = weightFilterVerifier.verifyFlightsFilteredByWeightWithFilters(filteredByWeightsFlights, notFilteredByWeightsFlights);

        assertTrue(result);
    }

    @Test
    public void givenAnEqualFilteredByWeightsList_whenVerifyingIfItWasFiltered_thenItIsNot() {
        given(filteredByWeightsFlights.size()).willReturn(NOT_FILTERED_BY_WEIGHTS_SIZE);

        boolean result = weightFilterVerifier.verifyFlightsFilteredByWeightWithFilters(filteredByWeightsFlights, notFilteredByWeightsFlights);

        assertFalse(result);
    }

    @Test
    public void givenAnHigherFilteredByWeightsList_whenVerifyingIfItWasFiltered_thenItIsNot() {
        given(filteredByWeightsFlights.size()).willReturn(NOT_FILTERED_BY_WEIGHTS_SIZE + 1);

        boolean result = weightFilterVerifier.verifyFlightsFilteredByWeightWithFilters(filteredByWeightsFlights, notFilteredByWeightsFlights);

        assertFalse(result);
    }
}