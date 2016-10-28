package ca.ulaval.glo4003.air.transfer.transaction;

import ca.ulaval.glo4003.air.api.transaction.dto.CartItemDto;
import ca.ulaval.glo4003.air.domain.transaction.cart.CartItem;

public class CartItemAssembler {

    public CartItemDto create(CartItem cartItem) {
        CartItemDto cartItemDto = new CartItemDto();
        cartItemDto.airlineCompany = cartItem.getAirlineCompany();
        cartItemDto.arrivalAirport = cartItem.getArrivalAirport();
        cartItemDto.departureDate = cartItem.getDepartureDate().toString();
        cartItemDto.ticketsQuantity = cartItem.getTicketsQuantity();
        return cartItemDto;
    }
}
