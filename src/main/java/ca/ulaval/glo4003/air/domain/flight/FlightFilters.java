package ca.ulaval.glo4003.air.domain.flight;

import java.time.LocalDateTime;
import java.util.Optional;

public class FlightFilters {

    private String departureAirport;
    private String arrivalAirport;
    private LocalDateTime departureDate;

    public FlightFilters(String departureAirport, String arrivalAirport, LocalDateTime departureDate) {
        this.departureAirport = departureAirport;
        this.arrivalAirport = arrivalAirport;
        this.departureDate = departureDate;
    }

    public Optional<String> getDepartureAirport() {
        return Optional.ofNullable(departureAirport);
    }

    public Optional<String> getArrivalAirport() {
        return Optional.ofNullable(arrivalAirport);
    }

    public Optional<LocalDateTime> getDepartureDate() {
        return Optional.ofNullable(departureDate);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FlightFilters that = (FlightFilters) o;

        if (departureAirport != null ? !departureAirport.equals(that.departureAirport) : that.departureAirport != null) return false;
        if (arrivalAirport != null ? !arrivalAirport.equals(that.arrivalAirport) : that.arrivalAirport != null) return false;
        return departureDate != null ? departureDate.equals(that.departureDate) : that.departureDate == null;

    }

    @Override
    public int hashCode() {
        int result = departureAirport != null ? departureAirport.hashCode() : 0;
        result = 31 * result + (arrivalAirport != null ? arrivalAirport.hashCode() : 0);
        result = 31 * result + (departureDate != null ? departureDate.hashCode() : 0);
        return result;
    }
}
