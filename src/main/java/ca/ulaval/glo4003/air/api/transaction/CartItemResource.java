package ca.ulaval.glo4003.air.api.transaction;

import ca.ulaval.glo4003.air.api.transaction.dto.CartItemDto;
import ca.ulaval.glo4003.air.domain.flight.NoSuchFlightException;
import ca.ulaval.glo4003.air.domain.transaction.CartItemService;

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
    @Produces(MediaType.APPLICATION_JSON)
    public CartItemDto reserveTickets(CartItemDto cartItemDto) {
        try {
            return this.cartItemService.reserveTickets(cartItemDto);
        } catch (NoSuchFlightException e) {
            logger.info("Reservation failed because: " + e.getMessage());
            throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND)
                    .entity("No such flight.")
                    .build());
        }
    }

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public CartItemDto releaseTickets(CartItemDto cartItemDto) {
        try {
            return this.cartItemService.releaseTickets(cartItemDto);
        } catch (NoSuchFlightException e) {
            logger.info("Reservation cancellation failed because: " + e.getMessage());
            throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND)
                    .entity("No such flight.")
                    .build());
        }
    }
}
