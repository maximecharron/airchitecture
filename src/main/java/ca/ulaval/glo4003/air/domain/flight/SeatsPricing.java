package ca.ulaval.glo4003.air.domain.flight;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SeatsPricing that = (SeatsPricing) o;
        return Double.compare(that.economicSeatsPrice, economicSeatsPrice) == 0 &&
            Double.compare(that.regularSeatsPrice, regularSeatsPrice) == 0 &&
            Double.compare(that.businessSeatsPrice, businessSeatsPrice) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(economicSeatsPrice, regularSeatsPrice, businessSeatsPrice);
    }
}
