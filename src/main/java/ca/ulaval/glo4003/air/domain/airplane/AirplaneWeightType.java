package ca.ulaval.glo4003.air.domain.airplane;

public enum AirplaneWeightType {
    AirLeger(23.5),
    AirMoyen(42),
    AirLourd(65) {
        @Override
        public boolean acceptsWeight(double weight) {
            return true;
        }
    };

    private final double maximumWeight;

    AirplaneWeightType(double maximumWeight) {
        this.maximumWeight = maximumWeight;
    }

    public boolean acceptsWeight(double weight) {
        return weight <= maximumWeight;
    }

    public double getMaximumWeight() {
        return maximumWeight;
    }
}
