package ca.ulaval.glo4003.air.domain.flight.airplane;

public class AirMoyenAirplane extends Airplane {

    public static final double MAXIMUM_WEIGHT = 42;

    public AirMoyenAirplane(int availableSeats) {
        super(availableSeats, MAXIMUM_WEIGHT);
    }

    @Override
    public boolean hasAdditionalWeightOption() {
        return false;
    }

    public boolean acceptsAdditionalWeight(double weight) {
        return false;
    }
}
