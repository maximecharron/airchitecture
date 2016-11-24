package ca.ulaval.glo4003.air.transfer.transaction;

import ca.ulaval.glo4003.air.api.transaction.dto.CartItemDto;
import ca.ulaval.glo4003.air.domain.airplane.SeatMap;
import ca.ulaval.glo4003.air.domain.flight.AvailableSeats;
import ca.ulaval.glo4003.air.domain.transaction.cart.CartItem;
import ca.ulaval.glo4003.air.transfer.airplane.SeatMapAssembler;
import ca.ulaval.glo4003.air.transfer.flight.AvailableSeatsAssembler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class CartItemAssembler {

    private final SeatMapAssembler seatMapAssembler;

    public CartItemAssembler(SeatMapAssembler seatMapAssembler) {
        this.seatMapAssembler = seatMapAssembler;
    }

    public CartItemDto create(CartItem cartItem) {
        CartItemDto cartItemDto = new CartItemDto();
        cartItemDto.airlineCompany = cartItem.getAirlineCompany();
        cartItemDto.arrivalAirport = cartItem.getArrivalAirport();
        cartItemDto.departureDate = cartItem.getDepartureDate().toString();
        cartItemDto.seatMapDto = seatMapAssembler.create(cartItem.getSeatMap());
        cartItemDto.luggageWeight = cartItem.getLuggageWeight();
        return cartItemDto;
    }

    public CartItem create(CartItemDto cartItemDto) {
        SeatMap seatMap = seatMapAssembler.create(cartItemDto.seatMapDto);
        LocalDateTime departureDate = LocalDateTime.parse(cartItemDto.departureDate);
        return new CartItem(seatMap, cartItemDto.arrivalAirport, cartItemDto.airlineCompany, departureDate, cartItemDto.luggageWeight);
    }

    public List<CartItem> create(List<CartItemDto> cartItemDtos) {
        return cartItemDtos.stream().map(this::create).collect(Collectors.toList());
    }
}
