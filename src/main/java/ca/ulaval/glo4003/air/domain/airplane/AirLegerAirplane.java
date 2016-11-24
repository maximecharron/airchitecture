package ca.ulaval.glo4003.air.domain.airplane;

public class AirLegerAirplane extends Airplane {

    public static final double MAXIMUM_WEIGHT = 23.5;

    public AirLegerAirplane(int availableSeats, boolean isAirVivant, String serialNumber) {
        super(new SeatMap(0, availableSeats, 0), MAXIMUM_WEIGHT, isAirVivant, serialNumber);
    }

    @Override
    public boolean hasAdditionalWeightOption() {
        return false;
    }

    public boolean acceptsAdditionalWeight(double weight) {
        return false;
    }
}
