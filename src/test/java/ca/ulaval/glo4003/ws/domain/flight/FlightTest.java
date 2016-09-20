package ca.ulaval.glo4003.ws.domain.flight;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class FlightTest {

    public static final String YQB = "YQB";
    public static final String DUB = "DUB";
    public static final LocalDateTime DEPARTURE_DATE = LocalDateTime.of(2000, 10, 10, 9, 55);
    private Flight flight;

    @Before
    public void setup() {
        flight = new Flight();
    }

    @Test
    public void givenAnOldFlight_whenCheckingIfItsAFutureFlight_thenItsNot() {
        flight.setDepartureDate(DEPARTURE_DATE);

        boolean isFuture = flight.isFuture();

        assertFalse(isFuture);
    }

    @Test
    public void givenAFlightThatWillLeaveTomorrow_whenCheckingIfItsAFutureFlight_thenItIs() {
        flight.setDepartureDate(LocalDateTime.now().plusDays(1));

        boolean isFuture = flight.isFuture();

        assertTrue(isFuture);
    }

    @Test
    public void givenAFlightFromQuebecToDublin_whenCheckingIfItsLeavingFromQuebecToDublin_thenItIs() {
        flight.setDepartureAirport(YQB);
        flight.setArrivalAirport(DUB);

        assertTrue(flight.isGoingTo(DUB));
        assertTrue(flight.isDepartingFrom(YQB));
    }

    @Test
    public void givenAFlightFromQuebecToDublin_whenCheckingIfItsLeavingFromTorontoToLondon_thenItsNot() {
        flight.setDepartureAirport(YQB);
        flight.setArrivalAirport(DUB);

        assertFalse(flight.isGoingTo("YXU"));
        assertFalse(flight.isDepartingFrom("YYZ"));
    }
}
