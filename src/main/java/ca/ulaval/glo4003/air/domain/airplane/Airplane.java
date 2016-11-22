package ca.ulaval.glo4003.air.domain.airplane;

public abstract class Airplane {

    private final String serialNumber;
    private final boolean isAirVivant;
    private final int availableSeats;
    private final double maximumWeight;

    public Airplane(int availableSeats, double maximumWeight, boolean isAirVivant, String serialNumber) {
        this.availableSeats = availableSeats;
        this.maximumWeight = maximumWeight;
        this.serialNumber = serialNumber;
        this.isAirVivant = isAirVivant;
    }

    public boolean isAirVivant() {
        return isAirVivant;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public boolean acceptsWeight(double weight) {
        return weight <= maximumWeight;
    }

    public double getMaximumWeight() {
        return maximumWeight;
    }

    public boolean isAirLourd() {
        return false;
    }

    public boolean hasSerialNumber(String serialNumber) {
        return this.serialNumber.equals(serialNumber);
    }

    public abstract boolean hasAdditionalWeightOption();

    public abstract boolean acceptsAdditionalWeight(double weight);

    public String getSerialNumber() {
        return serialNumber;
    }
}
