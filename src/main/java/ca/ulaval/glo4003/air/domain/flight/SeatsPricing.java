package ca.ulaval.glo4003.air.domain.flight;

public class SeatsPricing {

    private final double economicSeatsPrice;
    private final double regularSeatsPrice;
    private final double businessSeatsPrice;

    public SeatsPricing(double economicSeatsPrice, double regularSeatsPrice, double businessSeatsPrice) {
        this.economicSeatsPrice = economicSeatsPrice;
        this.regularSeatsPrice = regularSeatsPrice;
        this.businessSeatsPrice = businessSeatsPrice;
    }

    public double getEconomicSeatsPrice() {
        return economicSeatsPrice;
    }

    public double getRegularSeatsPrice() {
        return regularSeatsPrice;
    }

    public double getBusinessSeatsPrice() {
        return businessSeatsPrice;
    }
}
