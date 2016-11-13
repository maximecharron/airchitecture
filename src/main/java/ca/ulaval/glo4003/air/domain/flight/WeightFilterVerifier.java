package ca.ulaval.glo4003.air.domain.flight;

import java.util.List;

public class WeightFilterVerifier {

    public boolean verifyFlightsFilteredByWeightWithFilters(List<Flight> flightsPossiblyFilteredByWeights, List<Flight> flightsNotFilteredByWeights) {
        return flightsPossiblyFilteredByWeights.size() < flightsNotFilteredByWeights.size();
    }
}
