package ca.ulaval.glo4003.air.domain.transaction.cart;

import ca.ulaval.glo4003.air.domain.flight.FlightService;
import ca.ulaval.glo4003.air.domain.flight.NoSuchFlightException;

import java.util.logging.Logger;

public class CartItemService {

    private final Logger logger = Logger.getLogger(CartItemService.class.getName());
    private final FlightService flightService;

    public CartItemService(FlightService flightService) {
        this.flightService = flightService;
    }

    public void reserveTickets(CartItem cartItem) throws NoSuchFlightException {
        try {
            this.flightService.reservePlacesInFlight(cartItem.getAirlineCompany(), cartItem.getArrivalAirport(), cartItem.getDepartureDate(), cartItem.getTicketsQuantity());
        } catch (NoSuchFlightException e) {
            logger.info("Unable to reserve tickets for flight " + cartItem.getAirlineCompany() + " " + cartItem.getArrivalAirport() + " because it doesn't exist");
            throw e;
        }
    }

    public void releaseTickets(CartItem cartItem) throws NoSuchFlightException {
        try {
            this.flightService.releasePlacesInFlight(cartItem.getAirlineCompany(), cartItem.getArrivalAirport(), cartItem.getDepartureDate(), cartItem.getTicketsQuantity());
        } catch (NoSuchFlightException e) {
            logger.info("Unable to release tickets for flight " + cartItem.getAirlineCompany() + " " + cartItem.getArrivalAirport() + " because it doesn't exist");
            throw e;
        }
    }
}
