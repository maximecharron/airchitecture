package ca.ulaval.glo4003.ws.api.flight;

import ca.ulaval.glo4003.ws.api.flight.dto.FlightDto;
import ca.ulaval.glo4003.ws.domain.flight.FlightService;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class FlightResourceImpl implements FlightResource {

    private FlightService flightService;

    public FlightResourceImpl(FlightService flightService) {
        this.flightService = flightService;
    }

    @Override
    public List<FlightDto> findAllWithFilters(String departureAirport, String arrivalAirport, String departureDate) {
        LocalDateTime dateTime = null;

        if (departureDate != null) {
            try {
                dateTime = LocalDateTime.parse(departureDate, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            } catch (DateTimeParseException e) {
                throw new WebApplicationException(Response.status(Response.Status.BAD_REQUEST)
                                                          .entity("Invalid datetime format. " + e.getMessage())
                                                          .build());
            }
        }

        return flightService.findAllWithFilters(departureAirport, arrivalAirport, dateTime);
    }
}
