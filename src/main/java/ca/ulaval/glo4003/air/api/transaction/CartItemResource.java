package ca.ulaval.glo4003.air.api.transaction;

import ca.ulaval.glo4003.air.domain.flight.FlightNotFoundException;
import ca.ulaval.glo4003.air.service.transaction.cart.CartItemService;
import ca.ulaval.glo4003.air.transfer.transaction.dto.CartItemDto;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.logging.Logger;

@Path("/cartItems")
public class CartItemResource {

    private Logger logger = Logger.getLogger(CartItemResource.class.getName());
    private final CartItemService cartItemService;

    public CartItemResource(CartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void reserveTickets(CartItemDto cartItemDto) {
        try {
            LocalDateTime airCargoDepartureDate = null;
            if (cartItemDto.airCargoDepartureDate != null) {
                airCargoDepartureDate = parseDate(cartItemDto.airCargoDepartureDate);
            }
            this.cartItemService.reserveTickets(cartItemDto, airCargoDepartureDate);
        } catch (FlightNotFoundException e) {
            logger.info("Reservation failed because: " + e.getMessage());
            throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND)
                                                      .entity("No such flight.")
                                                      .build());
        }
    }

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    public void releaseTickets(CartItemDto cartItemDto) {
        try {
            LocalDateTime airCargoDepartureDate = null;
            if (cartItemDto.airCargoDepartureDate != null) {
                airCargoDepartureDate = parseDate(cartItemDto.airCargoDepartureDate);
            }
            this.cartItemService.releaseTickets(cartItemDto, airCargoDepartureDate);
        } catch (FlightNotFoundException e) {
            logger.info("Reservation cancellation failed because: " + e.getMessage());
            throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND)
                                                      .entity("No such flight.")
                                                      .build());
        }
    }

    private LocalDateTime parseDate(String date) {
        try {
            return LocalDateTime.parse(date, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        } catch (DateTimeParseException e) {
            throw new WebApplicationException(Response.status(Response.Status.BAD_REQUEST)
                                                      .entity("Invalid datetime format. " + e.getMessage())
                                                      .build());
        }
    }
}
