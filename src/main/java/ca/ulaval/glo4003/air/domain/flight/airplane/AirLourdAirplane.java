package ca.ulaval.glo4003.air.domain.flight.airplane;

public class AirLourdAirplane extends Airplane {

    public static final double MAXIMUM_WEIGHT = 65;
    private double maximumAdditionalWeight;

    public AirLourdAirplane(int availableSeats, double acceptedAdditionalWeight) {
        super(availableSeats, MAXIMUM_WEIGHT);
        this.maximumAdditionalWeight = MAXIMUM_WEIGHT + acceptedAdditionalWeight;
    }

    @Override
    public boolean acceptsWeight(final double weight) {
        return true;
    }

    @Override
    public boolean hasAdditionalWeightOption() {
        return true;
    }

    public boolean acceptsAdditionalWeight(double weight) {
        return weight <= maximumAdditionalWeight;
    }
}
