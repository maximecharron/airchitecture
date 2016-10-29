package ca.ulaval.glo4003.air.domain.transaction.cart;

import ca.ulaval.glo4003.air.api.transaction.dto.CartItemDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class CartItemFactory {

    public CartItem create(CartItemDto cartItemDto) {
        return new CartItem(cartItemDto.ticketsQuantity, cartItemDto.arrivalAirport, cartItemDto.airlineCompany, LocalDateTime.parse(cartItemDto.departureDate));
    }

    public List<CartItem> create(List<CartItemDto> cartItemDtos) {
        return cartItemDtos.stream().map(this::create).collect(Collectors.toList());
    }
}