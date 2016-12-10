package ca.ulaval.glo4003.air.domain.flight;


import ca.ulaval.glo4003.air.domain.airplane.Airplane;

import java.time.LocalDateTime;

public class AirCargoFlight extends Flight {

    private double price;
    private double totalWeight = 0;

    public static class AirCargoFlightBuilder {

        private String departureAirport;
        private String arrivalAirport;
        private LocalDateTime departureDate;
        private String airlineCompany;
        private Airplane airplane;
        private double price;

        public AirCargoFlightBuilder departureAirport(String departureAirport) {
            this.departureAirport = departureAirport;
            return this;
        }

        public AirCargoFlightBuilder arrivalAirport(String arrivalAirport) {
            this.arrivalAirport = arrivalAirport;
            return this;
        }

        public AirCargoFlightBuilder departureDate(LocalDateTime departureDate) {
            this.departureDate = departureDate;
            return this;
        }

        public AirCargoFlightBuilder airlineCompany(String airlineCompany) {
            this.airlineCompany = airlineCompany;
            return this;
        }

        public AirCargoFlightBuilder airplane(Airplane airplane) {
            this.airplane = airplane;
            return this;
        }

        public AirCargoFlightBuilder price(double price) {
            this.price = price;
            return this;
        }

        public AirCargoFlight build() {
            return new AirCargoFlight(this);
        }
    }

    private AirCargoFlight(AirCargoFlightBuilder builder) {
        super(builder.departureAirport, builder.arrivalAirport, builder.departureDate, builder.airlineCompany, builder.airplane);
        this.price = builder.price;
    }

    @Override
    public boolean isAirCargo() {
        return true;
    }

    @Override
    public boolean isPassengerFlight() {
        return false;
    }

    public double getPrice() {
        return price;
    }

    public void reserveSpace(double luggageWeight) {
        totalWeight += luggageWeight;
    }

    public void releaseSpace(double luggageWeight) {
        totalWeight -= luggageWeight;
    }

}
