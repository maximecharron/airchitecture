package ca.ulaval.glo4003.air.domain.flight;


import java.time.LocalDateTime;

public interface FlightCounter {
    long countWithFilters(String departureAirport, String arrivalAirport, LocalDateTime departureDate);
}
