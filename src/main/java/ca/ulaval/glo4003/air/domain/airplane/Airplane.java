package ca.ulaval.glo4003.air.domain.airplane;

public abstract class Airplane {

    private final String serialNumber;
    private final boolean isAirVivant;
    private final SeatMap seatMap;
    private final double maximumWeight;
    private final double totalMaximumWeight;

    public Airplane(SeatMap seatMap, double maximumWeight, boolean isAirVivant, String serialNumber, double totalMaximumWeight) {
        this.seatMap = seatMap;
        this.maximumWeight = maximumWeight;
        this.serialNumber = serialNumber;
        this.isAirVivant = isAirVivant;
        this.totalMaximumWeight = totalMaximumWeight;
    }

    public boolean isAirVivant() {
        return isAirVivant;
    }

    public SeatMap getSeatMap() {
        return seatMap;
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

    public boolean acceptsTotalWeight(double weight) {
        return weight <= totalMaximumWeight;
    }
}
