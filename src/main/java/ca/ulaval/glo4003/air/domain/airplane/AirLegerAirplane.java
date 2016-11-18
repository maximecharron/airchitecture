package ca.ulaval.glo4003.air.domain.airplane;

public class AirLegerAirplane extends Airplane {

    public static final double MAXIMUM_WEIGHT = 23.5;

    public AirLegerAirplane(int availableSeats, String serialNumber) {
        super(availableSeats, MAXIMUM_WEIGHT, serialNumber);
    }

    @Override
    public boolean hasAdditionalWeightOption() {
        return false;
    }

    public boolean acceptsAdditionalWeight(double weight) {
        return false;
    }
}
