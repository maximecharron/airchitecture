package ca.ulaval.glo4003.air.domain.flight.airplane;

public abstract class Airplane {
    private final int availableSeats;
    private final double maximumWeight;

    public Airplane(int availableSeats, double maximumWeight) {
        this.availableSeats = availableSeats;
        this.maximumWeight = maximumWeight;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public boolean acceptsWeight(double weight) {
        return weight <= maximumWeight;
    }

    public abstract boolean hasAdditionalWeightOption();

    public abstract boolean acceptsAdditionalWeight(double weight);
}
