package ca.ulaval.glo4003.air.domain.flight;

import ca.ulaval.glo4003.air.domain.flight.airplane.Airplane;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDateTime;

import static org.mockito.Mockito.verify;
import static org.junit.Assert.*;
import static org.mockito.BDDMockito.willReturn;

@RunWith(MockitoJUnitRunner.class)
public class FlightTest {

    private static final int A_TICKETS_QUANTITY = 2;
    private static final int A_NUMBER_OF_AVAILABLE_SEATS = 42;
    private static final double A_WEIGHT = 40.5;
    private static final float A_PRICE = 124f;
    private static final String AIRPORT_A = "YQB";
    private static final String AIRPORT_B = "DUB";
    private static final String AN_AIRLINE_COMPANY = "AirDariusRuckerWagonWheel";
    private static final LocalDateTime A_DEPARTURE_DATE = LocalDateTime.of(2016, 10, 10, 9, 55);
    private static final LocalDateTime ANOTHER_DEPARTURE_DATE = LocalDateTime.of(2018, 8, 10, 9, 55);

    @Mock
    private Airplane airplane;

    @Test
    public void givenAFlight_whenCheckingIfItsLeavingAfterADateFollowingItsDepartureDate_thenItsNot() {
        Flight flight = new Flight(AIRPORT_A, AIRPORT_B, A_DEPARTURE_DATE, AN_AIRLINE_COMPANY, airplane, A_PRICE);

        boolean result = flight.isLeavingAfter(A_DEPARTURE_DATE.plusDays(1));

        assertFalse(result);
    }

    @Test
    public void givenAFlight_whenCheckingIfItsLeavingAfterADatePriorToItsDepartureDate_thenItIs() {
        Flight flight = new Flight(AIRPORT_A, AIRPORT_B, A_DEPARTURE_DATE, AN_AIRLINE_COMPANY, airplane, A_PRICE);

        boolean result = flight.isLeavingAfter(A_DEPARTURE_DATE.minusDays(1));

        assertTrue(result);
    }

    @Test
    public void givenAFlight_whenCheckingIfItsLeavingAfterItsDepartureDate_thenItsNot() {
        Flight flight = new Flight(AIRPORT_A, AIRPORT_B, A_DEPARTURE_DATE, AN_AIRLINE_COMPANY, airplane, A_PRICE);

        boolean result = flight.isLeavingAfter(A_DEPARTURE_DATE);

        assertFalse(result);
    }

    @Test
    public void givenAFlightDepartingFromA_whenCheckingIfItsLeavingFromA_thenItIs() {
        Flight flight = new Flight(AIRPORT_A, AIRPORT_B, A_DEPARTURE_DATE, AN_AIRLINE_COMPANY, airplane, A_PRICE);

        assertTrue(flight.isDepartingFrom(AIRPORT_A));
    }

    @Test
    public void givenAFlightLeavingToB_whenCheckingIfItsGoingToB_thenItIs() {
        Flight flight = new Flight(AIRPORT_A, AIRPORT_B, A_DEPARTURE_DATE, AN_AIRLINE_COMPANY, airplane, A_PRICE);

        assertTrue(flight.isGoingTo(AIRPORT_B));
    }

    @Test
    public void givenAFlightDepartingFromA_whenCheckingIfItsLeavingFromB_thenItIsNot() {
        Flight flight = new Flight(AIRPORT_A, AIRPORT_B, A_DEPARTURE_DATE, AN_AIRLINE_COMPANY, airplane, A_PRICE);

        assertFalse(flight.isDepartingFrom(AIRPORT_B));
    }

    @Test
    public void givenAFlightLeavingToB_whenCheckingIfItsGoingToA_thenItIsNot() {
        Flight flight = new Flight(AIRPORT_A, AIRPORT_B, A_DEPARTURE_DATE, AN_AIRLINE_COMPANY, airplane, A_PRICE);

        assertFalse(flight.isGoingTo(AIRPORT_A));
    }

    @Test
    public void givenAFlightLeavingOnADate_whenCheckingIfItsLeavingOnThisDate_thenItIs() {
        Flight flight = new Flight(AIRPORT_A, AIRPORT_B, A_DEPARTURE_DATE, AN_AIRLINE_COMPANY, airplane, A_PRICE);

        assertTrue(flight.isLeavingOn(A_DEPARTURE_DATE));
    }

    @Test
    public void givenAFlightLeavingOnDateA_whenCheckingIfItsLeavingOnDateB_thenItsNot() {
        Flight flight = new Flight(AIRPORT_A, AIRPORT_B, A_DEPARTURE_DATE, AN_AIRLINE_COMPANY, airplane, A_PRICE);

        assertFalse(flight.isLeavingOn(ANOTHER_DEPARTURE_DATE));
    }

    @Test
    public void givenAFlight_whenCheckingIfItAcceptsAWeight_thenItsDelegatedToTheAirplane() {
        Flight flight = givenAFlight();

        flight.acceptsWeight(A_WEIGHT);

        verify(airplane).acceptsWeight(A_WEIGHT);
    }

    @Test
    public void givenAFlight_whenCheckingIfAcceptingAdditionalWeight_thenItsDelegatedToTheAirplane() {
        Flight flight = givenAFlight();

        flight.acceptsAdditionalWeight(A_WEIGHT);

        verify(airplane).acceptsAdditionalWeight(A_WEIGHT);
    }

    @Test
    public void givenAFlight_whenCheckingIfItHasAdditionalWeightOption_thenItsDelegatedToTheAirplane() {
        Flight flight = givenAFlight();

        flight.hasAdditionalWeightOption();

        verify(airplane).hasAdditionalWeightOption();
    }

    @Test
    public void givenAFlight_whenCheckingIfItIsAirVivant_thenItsDelegatedToTheAirplane() {
        Flight flight = givenAFlight();

        flight.isAirVivant();

        verify(airplane).isAirVivant();
    }

    @Test
    public void givenAFlight_whenReservingPlaces_thenAvailableSeatsDecreases() {
        willReturn(A_NUMBER_OF_AVAILABLE_SEATS).given(airplane).getAvailableSeats();
        Flight flight = givenAFlight();

        flight.reservePlaces(A_TICKETS_QUANTITY);

        int availableSeatsLeft = A_NUMBER_OF_AVAILABLE_SEATS - A_TICKETS_QUANTITY;
        assertEquals(flight.getAvailableSeats(), availableSeatsLeft);
    }

    @Test
    public void givenAFlight_whenReleasingPlaces_thenAvailableSeatsIncreases() {
        willReturn(A_NUMBER_OF_AVAILABLE_SEATS).given(airplane).getAvailableSeats();
        Flight flight = givenAFlight();

        flight.releasePlaces(A_TICKETS_QUANTITY);

        int availableSeatsLeft = A_NUMBER_OF_AVAILABLE_SEATS + A_TICKETS_QUANTITY;
        assertEquals(flight.getAvailableSeats(), availableSeatsLeft);
    }

    private Flight givenAFlight() {
        return new Flight(AIRPORT_A, AIRPORT_B, A_DEPARTURE_DATE, AN_AIRLINE_COMPANY, airplane, A_PRICE);
    }
}
