package ca.ulaval.glo4003.air.domain.transaction;

import ca.ulaval.glo4003.air.api.transaction.dto.CartItemDto;
import ca.ulaval.glo4003.air.domain.flight.FlightService;
import ca.ulaval.glo4003.air.domain.flight.NoSuchFlightException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

public class CartItemService {

    private Logger logger = Logger.getLogger(CartItemService.class.getName());
    private final FlightService flightService;
    private final CartItemFactory cartItemFactory;

    public CartItemService(FlightService flightService, CartItemFactory cartItemFactory) {
        this.flightService = flightService;
        this.cartItemFactory = cartItemFactory;
    }

    public CartItemDto reserveTickets(CartItemDto cartItemDto) throws NoSuchFlightException {
        CartItem cartItem = cartItemFactory.create(cartItemDto);
        try {
            this.flightService.reservePlacesInFlight(cartItem.getFlightNumber(), cartItem.getDepartureDate(), cartItem.getTicketsQuantity());
        }
        catch (NoSuchFlightException e) {
            logger.info("Unable to reserve tickets for flight " + cartItemDto.flightNumber + " because it doesn't exist");
            throw e;
        }
        return cartItemDto;
    }

    public CartItemDto releaseTickets(CartItemDto cartItemDto) throws NoSuchFlightException {
        CartItem cartItem = cartItemFactory.create(cartItemDto);
        try {
            this.flightService.releasePlacesInFlight(cartItem.getFlightNumber(), cartItem.getDepartureDate(), cartItem.getTicketsQuantity());
        }
        catch (NoSuchFlightException e) {
            logger.info("Unable to release tickets for flight " + cartItemDto.flightNumber + " because it doesn't exist");
            throw e;
        }
        return cartItemDto;
    }

    private LocalDateTime parseDate(String date) {
        return LocalDateTime.parse(date, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }
}
