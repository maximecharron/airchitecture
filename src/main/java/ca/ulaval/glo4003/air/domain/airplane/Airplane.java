package ca.ulaval.glo4003.air.domain.airplane;

public class Airplane {

    private final String airplaneNumber;
    private final int availableSeats;
    private final AirplaneWeightType airplaneWeightType;
    private final double maximumWeight;

    public Airplane(String airplaneNumber, int availableSeats, AirplaneWeightType airplaneWeightType) {
        this(airplaneNumber, availableSeats, airplaneWeightType, 0);
    }

    public Airplane(String airplaneNumber, int availableSeats, AirplaneWeightType airplaneWeightType, double maximumAdditionalWeight) {
        this.airplaneNumber = airplaneNumber;
        this.availableSeats = availableSeats;
        this.airplaneWeightType = airplaneWeightType;
        this.maximumWeight = this.airplaneWeightType.getMaximumWeight() + maximumAdditionalWeight;
    }

    public String getAirplaneNumber() {
        return airplaneNumber;
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
