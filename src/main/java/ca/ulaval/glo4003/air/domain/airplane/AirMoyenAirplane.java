package ca.ulaval.glo4003.air.domain.airplane;

public class AirMoyenAirplane extends Airplane {

    public static final double MAXIMUM_WEIGHT = 42;

    public AirMoyenAirplane(SeatMap seatMap, boolean isAirVivant, String serialNumber, double totalMaximumWeight) {
        super(seatMap, MAXIMUM_WEIGHT, isAirVivant, serialNumber, totalMaximumWeight);
    }

    @Override
    public boolean hasAdditionalWeightOption() {
        return false;
    }

    public boolean acceptsAdditionalWeight(double weight) {
        return false;
    }
}
