package ca.ulaval.glo4003.air.api.transaction;

import ca.ulaval.glo4003.air.transfer.transaction.dto.CartItemDto;
import ca.ulaval.glo4003.air.domain.flight.FlightNotFoundException;
import ca.ulaval.glo4003.air.service.transaction.cart.CartItemService;
import org.eclipse.jetty.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.ws.rs.WebApplicationException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CartItemResourceTest {

    @Mock
    private CartItemService cartItemService;

    private CartItemDto cartItemDto;

    private CartItemResource cartItemResource;

    @Before
    public void setup() {
        cartItemDto = new CartItemDto();
        cartItemResource = new CartItemResource(cartItemService);
    }

    @Test
    public void givenACartItemResource_whenReservingTickets_thenItsDelegatedToTheService() throws FlightNotFoundException {
        cartItemResource.reserveTickets(cartItemDto);

        verify(cartItemService).reserveTickets(cartItemDto, null);
    }

    @Test
    public void givenACartItemResource_whenReservingTicketsForANonExistentFlight_then404IsThrown() throws FlightNotFoundException {
        doThrow(FlightNotFoundException.class).when(cartItemService).reserveTickets(cartItemDto, null);
        try {
            cartItemResource.reserveTickets(cartItemDto);
            fail("Exception not thrown");
        } catch (WebApplicationException e) {
            assertThat(e.getResponse().getStatus(), is(equalTo(HttpStatus.NOT_FOUND_404)));
        }
    }

    @Test
    public void givenATransactionResource_whenReleasingTickets_thenItsDelegatedToTheService() throws FlightNotFoundException {
        cartItemResource.releaseTickets(cartItemDto);

        verify(cartItemService).releaseTickets(cartItemDto, null);
    }

    @Test
    public void givenACartItemResource_whenReleasingTicketsForANonExistentFlight_then404IsThrown() throws FlightNotFoundException {
        doThrow(FlightNotFoundException.class).when(cartItemService).releaseTickets(cartItemDto, null);
        try {
            cartItemResource.releaseTickets(cartItemDto);
            fail("Exception not thrown");
        } catch (WebApplicationException e) {
            assertThat(e.getResponse().getStatus(), is(equalTo(HttpStatus.NOT_FOUND_404)));
        }
    }
}
