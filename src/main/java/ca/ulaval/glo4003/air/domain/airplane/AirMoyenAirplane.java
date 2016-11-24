package ca.ulaval.glo4003.air.domain.airplane;

public class AirMoyenAirplane extends Airplane {

    public static final double MAXIMUM_WEIGHT = 42;

    public AirMoyenAirplane(SeatMap seatMap, boolean isAirVivant, String serialNumber) {
        super(seatMap, MAXIMUM_WEIGHT, isAirVivant, serialNumber);
    }

    @Override
    public boolean hasAdditionalWeightOption() {
        return false;
    }

    public boolean acceptsAdditionalWeight(double weight) {
        return false;
    }
}
