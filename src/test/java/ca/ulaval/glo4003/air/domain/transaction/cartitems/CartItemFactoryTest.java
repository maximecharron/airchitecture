package ca.ulaval.glo4003.air.domain.transaction.cartitems;

import ca.ulaval.glo4003.air.api.transaction.dto.CartItemDto;
import ca.ulaval.glo4003.air.domain.transaction.CartItem;
import ca.ulaval.glo4003.air.domain.transaction.CartItemFactory;
import ca.ulaval.glo4003.air.transfer.transaction.CartItemAssembler;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class CartItemFactoryTest {

    private static final String DEPARTURE_DATE = LocalDateTime.now().toString();
    private static final String FLIGHT_NUMBER = "AL0400";
    private static final int NUMBER_OF_TICKETS = 2;

    private CartItemFactory cartItemFactory;

    @Before
    public void setup() {
        cartItemFactory = new CartItemFactory();
    }

    @Test
    public void givenACartItemDto_whenAssemblingACartItem_thenItsWellAssembled() {
        CartItemDto cartItemDto = givenACartItemDto();

        CartItem cartItem = cartItemFactory.create(cartItemDto);

        assertCartItemIsWellAssembled(cartItem);
    }

    @Test
    public void givenMultipleCartDtos_whenAssemblingCartItems_thenTheyAreWellAssembled() {
        CartItemDto cartItemDto = givenACartItemDto();

        List<CartItem> cartItems = cartItemFactory.create(Collections.singletonList(cartItemDto));

        assertCartItemIsWellAssembled(cartItems.get(0));
    }

    private CartItemDto givenACartItemDto() {
        CartItemDto cartItemDto = new CartItemDto();
        cartItemDto.departureDate = DEPARTURE_DATE;
        cartItemDto.flightNumber = FLIGHT_NUMBER;
        cartItemDto.ticketsQuantity = NUMBER_OF_TICKETS;
        return cartItemDto;
    }

    private void assertCartItemIsWellAssembled(CartItem cartItem) {
        assertThat(cartItem.getDepartureDate(), is(equalTo(LocalDateTime.parse(DEPARTURE_DATE))));
        assertThat(cartItem.getFlightNumber(), is(equalTo(FLIGHT_NUMBER)));
        assertThat(cartItem.getTicketsQuantity(), is(equalTo(NUMBER_OF_TICKETS)));
    }
}
