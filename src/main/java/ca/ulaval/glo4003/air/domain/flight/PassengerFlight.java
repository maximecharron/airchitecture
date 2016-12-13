package ca.ulaval.glo4003.air.domain.flight;

import ca.ulaval.glo4003.air.domain.airplane.Airplane;
import ca.ulaval.glo4003.air.domain.airplane.SeatMap;

import java.time.LocalDateTime;


public class PassengerFlight extends Flight {

    private final AvailableSeats availableSeats;
    private final SeatsPricing seatsPricing;

    public static class PassengerFlightBuilder {

        private String departureAirport;
        private String arrivalAirport;
        private LocalDateTime departureDate;
        private String airlineCompany;
        private Airplane airplane;
        private SeatsPricing seatsPricing;
        private AvailableSeatsFactory availableSeatsFactory;

        public PassengerFlightBuilder departureAirport(String departureAirport) {
            this.departureAirport = departureAirport;
            return this;
        }

        public PassengerFlightBuilder arrivalAirport(String arrivalAirport) {
            this.arrivalAirport = arrivalAirport;
            return this;
        }

        public PassengerFlightBuilder departureDate(LocalDateTime departureDate) {
            this.departureDate = departureDate;
            return this;
        }

        public PassengerFlightBuilder airlineCompany(String airlineCompany) {
            this.airlineCompany = airlineCompany;
            return this;
        }

        public PassengerFlightBuilder airplane(Airplane airplane) {
            this.airplane = airplane;
            return this;
        }

        public PassengerFlightBuilder seatsPricing(SeatsPricing seatsPricing) {
            this.seatsPricing = seatsPricing;
            return this;
        }

        public PassengerFlightBuilder availableSeatsFactory(AvailableSeatsFactory availableSeatsFactory) {
            this.availableSeatsFactory = availableSeatsFactory;
            return this;
        }

        public PassengerFlight build() {
            return new PassengerFlight(this);
        }
    }

    private PassengerFlight(PassengerFlightBuilder builder) {
        super(builder.departureAirport, builder.arrivalAirport, builder.departureDate, builder.airlineCompany, builder.airplane);
        this.seatsPricing = builder.seatsPricing;
        this.availableSeats = builder.availableSeatsFactory.createFromSeatMap(builder.airplane.getSeatMap());
    }

    public boolean hasAvailableEconomySeats() {
        return this.availableSeats.hasEconomicSeats();
    }

    public boolean hasAvailableRegularSeats() {
        return this.availableSeats.hasRegularSeats();
    }

    public boolean hasAvailableBusinessSeats() {
        return this.availableSeats.hasRegularSeats();
    }

    public AvailableSeats getAvailableSeats() {
        return this.availableSeats;
    }

    public int totalSeatsLeft() {
        return availableSeats.totalSeatsLeft();
    }

    public SeatsPricing getSeatsPricing() {
        return seatsPricing;
    }

    public double getEconomicSeatsPrice() {
        return seatsPricing.getEconomicSeatsPrice();
    }

    public double getRegularSeatsPrice() {
        return seatsPricing.getRegularSeatsPrice();
    }

    public double getBusinessSeatsPrice() {
        return seatsPricing.getBusinessSeatsPrice();
    }

    public boolean hasAdditionalWeightOption() {
        return this.airplane.hasAdditionalWeightOption();
    }

    public boolean acceptsAdditionalWeight(double weight) {
        return airplane.acceptsAdditionalWeight(weight);
    }



    public void reserveSeats(SeatMap seatMap) {
        availableSeats.reserve(seatMap);
    }

    public void releaseSeats(SeatMap seatMap) {
        availableSeats.release(seatMap);
    }

    @Override
    public boolean acceptsWeight(double weight) {
        return airplane.acceptsWeight(weight);
    }

    @Override
    public boolean isAirCargo() {
        return false;
    }

    @Override
    public boolean isPassengerFlight() {
        return true;
    }
}
