package ca.ulaval.glo4003.air.api.transaction;

import ca.ulaval.glo4003.air.api.transaction.dto.CartItemDto;
import ca.ulaval.glo4003.air.domain.flight.FlightNotFoundException;
import ca.ulaval.glo4003.air.domain.transaction.cart.CartItem;
import ca.ulaval.glo4003.air.service.transaction.cart.CartItemService;
import ca.ulaval.glo4003.air.transfer.transaction.CartItemAssembler;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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
            this.cartItemService.reserveTickets(cartItemDto);
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
            this.cartItemService.releaseTickets(cartItemDto);
        } catch (FlightNotFoundException e) {
            logger.info("Reservation cancellation failed because: " + e.getMessage());
            throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND)
                                                      .entity("No such flight.")
                                                      .build());
        }
    }
}
