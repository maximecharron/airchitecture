package ca.ulaval.glo4003.air.service.transaction.cart;

import ca.ulaval.glo4003.air.domain.airplane.SeatMap;
import ca.ulaval.glo4003.air.domain.flight.FlightNotFoundException;
import ca.ulaval.glo4003.air.service.flight.FlightService;
import ca.ulaval.glo4003.air.transfer.airplane.SeatMapAssembler;
import ca.ulaval.glo4003.air.transfer.airplane.dto.SeatMapDto;
import ca.ulaval.glo4003.air.transfer.transaction.CartItemAssembler;
import ca.ulaval.glo4003.air.transfer.transaction.dto.CartItemDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDateTime;

import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CartItemServiceTest {

    private final static String A_DATE = LocalDateTime.now().toString();
    private final static String A_FLIGHT_NUMBER = "A345E";
    private final static String ARRIVAL_AIRPORT = "YQB";
    private static final int ECONOMIC_SEATS = 150;
    private static final int REGULAR_SEATS = 210;
    private static final int BUSINESS_SEATS = 70;
    private final static SeatMapDto SEAT_MAP_DTO = new SeatMapDto(ECONOMIC_SEATS, REGULAR_SEATS, BUSINESS_SEATS);
    private static final SeatMap SEAT_MAP = new SeatMap(ECONOMIC_SEATS, REGULAR_SEATS, BUSINESS_SEATS);

    @Mock
    private CartItemDto cartItemDto;

    @Mock
    private FlightService flightService;

    private CartItemService cartItemService;

    @Before
    public void setup() {
        SeatMapAssembler seatMapAssembler = new SeatMapAssembler();
        CartItemAssembler cartItemAssembler = new CartItemAssembler(seatMapAssembler);
        cartItemService = new CartItemService(flightService, cartItemAssembler);

        cartItemDto.departureDate = A_DATE;
        cartItemDto.airlineCompany = A_FLIGHT_NUMBER;
        cartItemDto.arrivalAirport = ARRIVAL_AIRPORT;
        cartItemDto.seatMapDto = SEAT_MAP_DTO;
    }

    @Test
    public void givenACartItem_whenReservingTickets_thenReservePlacesInFlight() throws FlightNotFoundException {
        cartItemService.reserveTickets(cartItemDto, null);

        verify(flightService).reservePlacesInFlight(eq(A_FLIGHT_NUMBER), eq(ARRIVAL_AIRPORT), any(LocalDateTime.class), eq(SEAT_MAP));
    }

    @Test(expected = FlightNotFoundException.class)
    public void givenACartItem_whenReservingTicketsForANonExistentFlight_thenThrowException() throws FlightNotFoundException {
        willThrow(new FlightNotFoundException("")).given(flightService).reservePlacesInFlight(eq(A_FLIGHT_NUMBER), eq(ARRIVAL_AIRPORT), any(LocalDateTime.class), eq(SEAT_MAP));

        cartItemService.reserveTickets(cartItemDto, null);
    }

    @Test
    public void givenACartItem_whenReleasingTickets_thenReleasePlacesInFlight() throws FlightNotFoundException {
        cartItemService.releaseTickets(cartItemDto, null);

        verify(flightService).releasePlacesInFlight(eq(A_FLIGHT_NUMBER), eq(ARRIVAL_AIRPORT), any(LocalDateTime.class), eq(SEAT_MAP));
    }

    @Test(expected = FlightNotFoundException.class)
    public void givenACartItem_whenReleasingTicketsForANonExistentFlight_thenThrowException() throws FlightNotFoundException {
        willThrow(new FlightNotFoundException("")).given(flightService).releasePlacesInFlight(eq(A_FLIGHT_NUMBER), eq(ARRIVAL_AIRPORT), any(LocalDateTime.class), eq(SEAT_MAP));

        cartItemService.releaseTickets(cartItemDto, null);
    }
}
