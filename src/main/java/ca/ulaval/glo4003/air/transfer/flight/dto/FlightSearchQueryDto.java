package ca.ulaval.glo4003.air.transfer.flight.dto;

import com.auth0.jwt.internal.org.apache.commons.lang3.builder.EqualsBuilder;
import com.auth0.jwt.internal.org.apache.commons.lang3.builder.HashCodeBuilder;

import java.time.LocalDateTime;

public class FlightSearchQueryDto {

    public String departureAirport;
    public String arrivalAirport;
    public LocalDateTime departureDate;
    public double weight;
    public boolean onlyAirVivant;
    public boolean acceptsAirCargo;
    public boolean hasEconomySeats;
    public boolean hasRegularSeats;
    public boolean hasBusinessSeats;

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof FlightSearchQueryDto)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        FlightSearchQueryDto rhs = (FlightSearchQueryDto) obj;

        return new EqualsBuilder()
            .append(departureAirport, rhs.departureAirport)
            .append(arrivalAirport, rhs.arrivalAirport)
            .append(departureDate, rhs.departureDate)
            .append(weight, rhs.weight)
            .append(onlyAirVivant, rhs.onlyAirVivant)
            .append(hasEconomySeats, rhs.hasEconomySeats)
            .append(hasRegularSeats, rhs.hasRegularSeats)
            .append(hasBusinessSeats, rhs.hasBusinessSeats)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(757, 839)
            .append(departureAirport)

            .toHashCode();
    }
}
