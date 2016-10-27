package ca.ulaval.glo4003.air.domain.transaction.cartitems;

import ca.ulaval.glo4003.air.api.transaction.dto.CartItemDto;

import java.util.List;
import java.util.stream.Collectors;

public class CartItemAssembler {

    public CartItem create(CartItemDto cartItemDto) {
        return new CartItem(cartItemDto.ticketsQuantity, cartItemDto.arrivalAirport, cartItemDto.airlineCompany, cartItemDto.departureDate);
    }

    public List<CartItem> create(List<CartItemDto> cartItemDtos) {
        return cartItemDtos.stream().map(this::create).collect(Collectors.toList());
    }
}
