package ca.ulaval.glo4003.air.domain.flight;

import java.time.LocalDateTime;
import java.util.stream.Stream;

public class WeightFilterVerifier {
    private FlightCounter flightCounter;

    public WeightFilterVerifier(FlightCounter flightCounter) {
        this.flightCounter = flightCounter;
    }

    public boolean verifyFlightsFilteredByWeightWithFilters(Stream<Flight> flights, String departureAirport, String arrivalAirport, LocalDateTime departureDate) {
        long numberOfFlightsFilteredWithoutWeight = this.flightCounter.countWithFilters(departureAirport, arrivalAirport, departureDate);
        long numberOfFlightsFilteredWithWeight = flights.count();
        return numberOfFlightsFilteredWithWeight < numberOfFlightsFilteredWithoutWeight;
    }
}
