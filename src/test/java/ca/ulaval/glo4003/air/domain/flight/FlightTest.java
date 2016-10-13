package ca.ulaval.glo4003.air.domain.flight;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class FlightTest {

    private static final String YQB = "YQB";
    private static final String DUB = "DUB";
    private static final LocalDateTime DEPARTURE_DATE = LocalDateTime.of(2000, 10, 10, 9, 55);
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
    public void givenAFlighDepartingFromA_whenCheckingIfItsLeavingFromA_thenItIs() {
        flight.setDepartureAirport(YQB);

        assertTrue(flight.isDepartingFrom(YQB));
    }

    @Test
    public void givenAFlightLeavingToB_whenCheckingIfItsGoingToB_thenItIs() {
        flight.setArrivalAirport(DUB);

        assertTrue(flight.isGoingTo(DUB));
    }

    @Test
    public void givenAFlighDepartingFromA_whenCheckingIfItsLeavingFromB_thenItIsNot() {
        flight.setDepartureAirport(YQB);

        assertFalse(flight.isDepartingFrom(DUB));
    }

    @Test
    public void givenAFlightLeavingToB_whenCheckingIfItsGoingToA_thenItIsNot() {
        flight.setArrivalAirport(DUB);

        assertFalse(flight.isGoingTo(YQB));
    }
}
