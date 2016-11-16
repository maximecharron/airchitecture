package ca.ulaval.glo4003.air.service.transaction.cart;

import ca.ulaval.glo4003.air.api.transaction.dto.CartItemDto;
import ca.ulaval.glo4003.air.domain.flight.FlightNotFoundException;
import ca.ulaval.glo4003.air.service.flight.FlightService;
import ca.ulaval.glo4003.air.transfer.transaction.CartItemAssembler;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDateTime;

import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CartItemServiceTest {

    private final static String A_DATE = LocalDateTime.now().toString();
    private final static String A_FLIGHT_NUMBER = "A345E";
    private final static String ARRIVAL_AIRPORT = "YQB";
    private final static int A_TICKETS_QUANTITY = 3;

    @Mock
    private CartItemDto cartItem;

    @Mock
    private FlightService flightService;

    private CartItemAssembler cartItemAssembler;
    private CartItemService cartItemService;

    @Before
    public void setup() {
        cartItemAssembler = new CartItemAssembler();
        cartItemService = new CartItemService(flightService, cartItemAssembler);

        cartItem.departureDate = A_DATE;
        cartItem.airlineCompany = A_FLIGHT_NUMBER;
        cartItem.arrivalAirport = ARRIVAL_AIRPORT;
        cartItem.ticketsQuantity = A_TICKETS_QUANTITY;
    }

    @Test
    public void givenACartItem_whenReservingTickets_thenReservePlacesInFlight() throws FlightNotFoundException {
        cartItemService.reserveTickets(cartItem);

        verify(flightService).reservePlacesInFlight(eq(A_FLIGHT_NUMBER), eq(ARRIVAL_AIRPORT),  any(LocalDateTime.class), eq(A_TICKETS_QUANTITY));
    }

    @Test(expected = FlightNotFoundException.class)
    public void givenACartItem_whenReservingTicketsForANonExistentFlight_thenThrowException() throws FlightNotFoundException {
        willThrow(new FlightNotFoundException("")).given(flightService).reservePlacesInFlight(eq(A_FLIGHT_NUMBER), eq(ARRIVAL_AIRPORT),  any(LocalDateTime.class), eq(A_TICKETS_QUANTITY));
        cartItemService.reserveTickets(cartItem);
    }

    @Test
    public void givenACartItem_whenReleasingTickets_thenReleasePlacesInFlight() throws FlightNotFoundException {
        cartItemService.releaseTickets(cartItem);

        verify(flightService).releasePlacesInFlight(eq(A_FLIGHT_NUMBER), eq(ARRIVAL_AIRPORT), any(LocalDateTime.class), eq(A_TICKETS_QUANTITY));
    }

    @Test(expected = FlightNotFoundException.class)
    public void givenACartItem_whenReleasingTicketsForANonExistentFlight_thenThrowException() throws FlightNotFoundException {
        willThrow(new FlightNotFoundException("")).given(flightService).releasePlacesInFlight(eq(A_FLIGHT_NUMBER), eq(ARRIVAL_AIRPORT),  any(LocalDateTime.class), eq(A_TICKETS_QUANTITY));
        cartItemService.releaseTickets(cartItem);
    }
}
