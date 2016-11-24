package ca.ulaval.glo4003.air.service.transaction.cart;

import ca.ulaval.glo4003.air.api.transaction.dto.CartItemDto;
import ca.ulaval.glo4003.air.domain.airplane.SeatMap;
import ca.ulaval.glo4003.air.domain.flight.FlightNotFoundException;
import ca.ulaval.glo4003.air.domain.transaction.cart.CartItem;
import ca.ulaval.glo4003.air.service.flight.FlightService;
import ca.ulaval.glo4003.air.transfer.transaction.CartItemAssembler;

import java.util.logging.Logger;

public class CartItemService {

    private final Logger logger = Logger.getLogger(CartItemService.class.getName());
    private final FlightService flightService;
    private final CartItemAssembler cartItemAssembler;

    public CartItemService(FlightService flightService, CartItemAssembler cartItemAssembler) {
        this.flightService = flightService;
        this.cartItemAssembler = cartItemAssembler;
    }

    public void reserveTickets(CartItemDto cartItemDto) throws FlightNotFoundException {
        CartItem cartItem = cartItemAssembler.create(cartItemDto);
        try {
            SeatMap seatMap = cartItem.getSeatMap();
            this.flightService.reservePlacesInFlight(cartItem.getAirlineCompany(), cartItem.getArrivalAirport(), cartItem.getDepartureDate(), seatMap);
            if (cartItemDto.airCargoFlight != null) {
                this.flightService.reserveSpaceInAirCargoFlight(cartItemDto.airCargoFlight, cartItem.getLuggageWeight());
            }
        } catch (FlightNotFoundException e) {
            logger.info("Unable to reserve tickets for flight " + cartItem.getAirlineCompany() + " " + cartItem.getArrivalAirport() + " because it doesn't exist");
            throw e;
        }
    }

    public void releaseTickets(CartItemDto cartItemDto) throws FlightNotFoundException {
        CartItem cartItem = cartItemAssembler.create(cartItemDto);
        try {
            SeatMap seatMap = cartItem.getSeatMap();
            this.flightService.releasePlacesInFlight(cartItem.getAirlineCompany(), cartItem.getArrivalAirport(), cartItem.getDepartureDate(), seatMap);
            if (cartItemDto.airCargoFlight != null) {
                this.flightService.releaseSpaceInAirCargoFlight(cartItemDto.airCargoFlight, cartItem.getLuggageWeight());
            }
        } catch (FlightNotFoundException e) {
            logger.info("Unable to release tickets for flight " + cartItem.getAirlineCompany() + " " + cartItem.getArrivalAirport() + " because it doesn't exist");
            throw e;
        }
    }
}
