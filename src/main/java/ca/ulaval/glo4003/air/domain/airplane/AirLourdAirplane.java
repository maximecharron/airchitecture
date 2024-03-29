package ca.ulaval.glo4003.air.domain.airplane;

public class AirLourdAirplane extends Airplane {

    public static final double MAXIMUM_WEIGHT = 65;
    private double acceptedAdditionalWeight;
    private double maximumAdditionalWeight;

    public AirLourdAirplane(SeatMap seatMap, double acceptedAdditionalWeight, boolean isAirVivant, String serialNumber, double totalMaximumWeight) {
        super(seatMap, MAXIMUM_WEIGHT, isAirVivant, serialNumber, totalMaximumWeight);
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

    @Override
    public boolean isAirLourd() {
        return true;
    }

    public double getAcceptedAdditionalWeight() {
        return acceptedAdditionalWeight;
    }

    public boolean acceptsAdditionalWeight(double weight) {
        return weight <= maximumAdditionalWeight;
    }

    public void setAcceptedAdditionalWeight(double acceptedAdditionalWeight) {
        this.acceptedAdditionalWeight = acceptedAdditionalWeight;
    }
}
