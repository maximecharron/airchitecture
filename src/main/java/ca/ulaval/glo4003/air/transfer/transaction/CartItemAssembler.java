package ca.ulaval.glo4003.air.transfer.transaction;

import ca.ulaval.glo4003.air.api.transaction.dto.CartItemDto;
import ca.ulaval.glo4003.air.domain.transaction.CartItem;

import java.util.List;
import java.util.stream.Collectors;

public class CartItemAssembler {

    public CartItemDto create(CartItem cartItem) {
        CartItemDto cartItemDto = new CartItemDto();
        cartItemDto.airlineCompany = cartItem.getAirlineCompany();
        cartItemDto.arrivalAirport = cartItem.getArrivalAirport();
        cartItemDto.departureDate = cartItem.getDepartureDate();
        cartItemDto.ticketsQuantity = cartItem.getTicketsQuantity();
        return cartItemDto;
    }
}
