package ca.ulaval.glo4003.air.domain.flight.airplane;

public abstract class Airplane {

    private final int availableSeats;
    private final double maximumWeight;
    private final boolean isAirVivant;

    public Airplane(int availableSeats, double maximumWeight, boolean isAirVivant) {
        this.availableSeats = availableSeats;
        this.maximumWeight = maximumWeight;
        this.isAirVivant = isAirVivant;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public boolean acceptsWeight(double weight) {
        return weight <= maximumWeight;
    }

    public boolean isAirVivant() {
        return isAirVivant;
    }

    public abstract boolean hasAdditionalWeightOption();

    public abstract boolean acceptsAdditionalWeight(double weight);

}
