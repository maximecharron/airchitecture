package ca.ulaval.glo4003.air.transfer.flight;

import ca.ulaval.glo4003.air.transfer.flight.dto.FlightSearchQueryDto;

import java.time.LocalDateTime;

public class FlightSearchQueryAssembler {

    public FlightSearchQueryDto create(String departureAirport, String arrivalAirport, LocalDateTime departureDate,
                                       double weight, boolean onlyAirVivant, boolean acceptsAirCargo,
                                       boolean hasEconomySeats, boolean hasRegularSeats, boolean hasBusinessSeats) {
        FlightSearchQueryDto flightSearchQueryDto = new FlightSearchQueryDto();

        flightSearchQueryDto.departureAirport = departureAirport;
        flightSearchQueryDto.arrivalAirport = arrivalAirport;
        flightSearchQueryDto.departureDate = departureDate;
        flightSearchQueryDto.weight = weight;
        flightSearchQueryDto.onlyAirVivant = onlyAirVivant;
        flightSearchQueryDto.acceptsAirCargo = acceptsAirCargo;
        flightSearchQueryDto.hasEconomySeats = hasEconomySeats;
        flightSearchQueryDto.hasRegularSeats = hasRegularSeats;
        flightSearchQueryDto.hasBusinessSeats = hasBusinessSeats;

        return flightSearchQueryDto;
    }
}
