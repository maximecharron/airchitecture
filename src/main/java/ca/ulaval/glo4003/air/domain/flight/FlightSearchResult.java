package ca.ulaval.glo4003.air.domain.flight;

import java.util.List;
import java.util.Map;

public class FlightSearchResult {

    private final List<PassengerFlight> flightsFilteredByWeight;
    private final double weight;
    private final boolean flightsWereFilteredByWeight;
    private final Map<PassengerFlight, AirCargoFlight> flightsWithAirCargo;

    public FlightSearchResult(List<PassengerFlight> flightsFilteredByWeight, double weight, boolean flightsWereFilteredByWeight, Map<PassengerFlight, AirCargoFlight> flightsWithAirCargo) {
        this.flightsFilteredByWeight = flightsFilteredByWeight;
        this.weight = weight;
        this.flightsWereFilteredByWeight = flightsWereFilteredByWeight;
        this.flightsWithAirCargo = flightsWithAirCargo;
    }

    public List<PassengerFlight> getFlightsFilteredByWeight() {
        return flightsFilteredByWeight;
    }

    public double getWeight() {
        return weight;
    }

    public boolean isFlightsWereFilteredByWeight() {
        return flightsWereFilteredByWeight;
    }

    public Map<PassengerFlight, AirCargoFlight> getFlightsWithAirCargo() {
        return flightsWithAirCargo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FlightSearchResult that = (FlightSearchResult) o;

        if (Double.compare(that.weight, weight) != 0) return false;
        if (flightsWereFilteredByWeight != that.flightsWereFilteredByWeight) return false;
        return flightsFilteredByWeight != null ? flightsFilteredByWeight.equals(that.flightsFilteredByWeight) : that.flightsFilteredByWeight == null;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = flightsFilteredByWeight != null ? flightsFilteredByWeight.hashCode() : 0;
        temp = Double.doubleToLongBits(weight);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (flightsWereFilteredByWeight ? 1 : 0);
        return result;
    }
}
