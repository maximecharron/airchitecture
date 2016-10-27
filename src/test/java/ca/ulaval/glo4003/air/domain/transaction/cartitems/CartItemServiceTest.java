package ca.ulaval.glo4003.air.domain.transaction.cartitems;

import ca.ulaval.glo4003.air.api.transaction.dto.CartItemDto;
import ca.ulaval.glo4003.air.domain.flight.FlightService;
import ca.ulaval.glo4003.air.domain.flight.NoSuchFlightException;
import ca.ulaval.glo4003.air.domain.transaction.CartItem;
import ca.ulaval.glo4003.air.domain.transaction.CartItemFactory;
import ca.ulaval.glo4003.air.domain.transaction.CartItemService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDateTime;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CartItemServiceTest {
    private final static LocalDateTime A_DATE = LocalDateTime.now();
    private final static String A_FLIGHT_NUMBER = "A345E";
    private final static int A_TICKETS_QUANTITY = 3;

    @Mock
    private CartItem cartItem;

    @Mock
    private FlightService flightService;

    @Mock
    private CartItemFactory cartItemFactory;

    @Mock
    private CartItemDto cartItemDto;

    private CartItemService cartItemService;

    @Before
    public void setup() {
        cartItemService = new CartItemService(flightService, cartItemFactory);

        willReturn(cartItem).given(cartItemFactory).create(cartItemDto);
        willReturn(A_DATE).given(cartItem).getDepartureDate();
        willReturn(A_FLIGHT_NUMBER).given(cartItem).getFlightNumber();
        willReturn(A_TICKETS_QUANTITY).given(cartItem).getTicketsQuantity();
    }

    @Test
    public void givenACartItem_whenReservingTickets_thenReservePlacesInFlight() throws NoSuchFlightException {
        cartItemService.reserveTickets(cartItemDto);

        verify(flightService).reservePlacesInFlight(A_FLIGHT_NUMBER, A_DATE, A_TICKETS_QUANTITY);
    }

    @Test(expected = NoSuchFlightException.class)
    public void givenACartItem_whenReservingTicketsForANonExistentFlight_thenThrowException() throws NoSuchFlightException {
        willThrow(new NoSuchFlightException("")).given(flightService).reservePlacesInFlight(A_FLIGHT_NUMBER, A_DATE, A_TICKETS_QUANTITY);
        cartItemService.reserveTickets(cartItemDto);
    }

    @Test
    public void givenACartItem_whenReleasingTickets_thenReleasePlacesInFlight() throws NoSuchFlightException {
        cartItemService.releaseTickets(cartItemDto);

        verify(flightService).releasePlacesInFlight(A_FLIGHT_NUMBER, A_DATE, A_TICKETS_QUANTITY);
    }

    @Test(expected = NoSuchFlightException.class)
    public void givenACartItem_whenReleasingTicketsForANonExistentFlight_thenThrowException() throws NoSuchFlightException {
        willThrow(new NoSuchFlightException("")).given(flightService).releasePlacesInFlight(A_FLIGHT_NUMBER, A_DATE, A_TICKETS_QUANTITY);
        cartItemService.releaseTickets(cartItemDto);
    }
}
