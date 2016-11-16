package ca.ulaval.glo4003.air.transfer.transaction;

import ca.ulaval.glo4003.air.api.transaction.dto.CartItemDto;
import ca.ulaval.glo4003.air.domain.transaction.cart.CartItem;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class CartItemAssembler {

    public CartItemDto create(CartItem cartItem) {
        CartItemDto cartItemDto = new CartItemDto();
        cartItemDto.airlineCompany = cartItem.getAirlineCompany();
        cartItemDto.arrivalAirport = cartItem.getArrivalAirport();
        cartItemDto.departureDate = cartItem.getDepartureDate().toString();
        cartItemDto.ticketsQuantity = cartItem.getTicketsQuantity();
        cartItemDto.luggageWeight = cartItem.getLuggageWeight();
        cartItemDto.ticketsPrice = cartItem.getTicketsPrice();
        return cartItemDto;
    }

    public CartItem create(CartItemDto cartItemDto) {
        return new CartItem(cartItemDto.ticketsQuantity, cartItemDto.arrivalAirport, cartItemDto.airlineCompany, LocalDateTime.parse(cartItemDto.departureDate), cartItemDto.luggageWeight, cartItemDto.ticketsPrice);
    }

    public List<CartItem> create(List<CartItemDto> cartItemDtos) {
        return cartItemDtos.stream().map(this::create).collect(Collectors.toList());
    }
}
