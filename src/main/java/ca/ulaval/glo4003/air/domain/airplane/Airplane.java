package ca.ulaval.glo4003.air.domain.airplane;

public class Airplane {

    private final int availableSeats;
    private final AirplaneWeightType airplaneWeightType;
    private final double maximumWeight;

    public Airplane(int availableSeats, AirplaneWeightType airplaneWeightType) {
        this(availableSeats, airplaneWeightType, 0);
    }

    public Airplane(int availableSeats, AirplaneWeightType airplaneWeightType, double maximumAdditionalWeight) {
        this.availableSeats = availableSeats;
        this.airplaneWeightType = airplaneWeightType;
        this.maximumWeight = this.airplaneWeightType.getMaximumWeight() + maximumAdditionalWeight;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public boolean acceptsWeight(double weight) {
        return this.airplaneWeightType.acceptsWeight(weight);
    }

    public boolean acceptsAdditionalWeight(double weight) {
        return airplaneWeightType == AirplaneWeightType.AirLourd && maximumWeight >= weight;
    }
}
