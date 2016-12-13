package ca.ulaval.glo4003.air.domain.flight;


import ca.ulaval.glo4003.air.domain.airplane.Airplane;
import ca.ulaval.glo4003.air.domain.airplane.SeatMap;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDateTime;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class AirCargoFlightTest {

    private static final double A_WEIGHT = 40.5;
    private static final String AIRPORT_A = "YQB";
    private static final String AIRPORT_B = "DUB";
    private static final String AN_AIRLINE_COMPANY = "AirDariusRuckerWagonWheel";
    private static final LocalDateTime A_DEPARTURE_DATE = LocalDateTime.of(2016, 10, 10, 9, 55);
    private static final LocalDateTime ANOTHER_DEPARTURE_DATE = LocalDateTime.of(2018, 8, 10, 9, 55);
    private static final double A_PRICE = 2.3;


    @Mock
    private Airplane airplane;

    @Mock
    private SeatsPricing seatsPricing;

    @Mock
    private AvailableSeatsFactory availableSeatsFactory;

    @Mock
    private AvailableSeats availableSeats;

    @Test
    public void givenAAirCargoFlight_whenCheckingIfItsLeavingAfterOrOnADateFollowingItsDepartureDate_thenItsNot() {
        AirCargoFlight airCargoFlight = new AirCargoFlight(AIRPORT_A, AIRPORT_B, A_DEPARTURE_DATE, AN_AIRLINE_COMPANY, airplane, A_PRICE);

        boolean result = airCargoFlight.isLeavingAfterOrOn(A_DEPARTURE_DATE.plusDays(1));

        assertFalse(result);
    }

    @Test
    public void givenAAirCargoFlight_whenCheckingIfItsLeavingAfterOrOnADatePriorToItsDepartureDate_thenItIs() {
        AirCargoFlight airCargoFlight = new AirCargoFlight(AIRPORT_A, AIRPORT_B, A_DEPARTURE_DATE, AN_AIRLINE_COMPANY, airplane, A_PRICE);

        boolean result = airCargoFlight.isLeavingAfterOrOn(A_DEPARTURE_DATE.minusDays(1));

        assertTrue(result);
    }

    @Test
    public void givenAAirCargoFlight_whenCheckingIfItsLeavingAfterOrOnItsDepartureDate_thenItIs() {
        AirCargoFlight airCargoFlight = new AirCargoFlight(AIRPORT_A, AIRPORT_B, A_DEPARTURE_DATE, AN_AIRLINE_COMPANY, airplane, A_PRICE);

        boolean result = airCargoFlight.isLeavingAfterOrOn(A_DEPARTURE_DATE);

        assertTrue(result);
    }

    @Test
    public void givenAAirCargoFlightDepartingFromA_whenCheckingIfItsLeavingFromA_thenItIs() {
        AirCargoFlight airCargoFlight = new AirCargoFlight(AIRPORT_A, AIRPORT_B, A_DEPARTURE_DATE, AN_AIRLINE_COMPANY, airplane, A_PRICE);

        assertTrue(airCargoFlight.isDepartingFrom(AIRPORT_A));
    }

    @Test
    public void givenAAirCargoFlightLeavingToB_whenCheckingIfItsGoingToB_thenItIs() {
        AirCargoFlight airCargoFlight = new AirCargoFlight(AIRPORT_A, AIRPORT_B, A_DEPARTURE_DATE, AN_AIRLINE_COMPANY, airplane, A_PRICE);

        assertTrue(airCargoFlight.isGoingTo(AIRPORT_B));
    }

    @Test
    public void givenAAirCargoFlightDepartingFromA_whenCheckingIfItsLeavingFromB_thenItIsNot() {
        AirCargoFlight airCargoFlight = new AirCargoFlight(AIRPORT_A, AIRPORT_B, A_DEPARTURE_DATE, AN_AIRLINE_COMPANY, airplane, A_PRICE);

        assertFalse(airCargoFlight.isDepartingFrom(AIRPORT_B));
    }

    @Test
    public void givenAAirCargoFlightLeavingToB_whenCheckingIfItsGoingToA_thenItIsNot() {
        AirCargoFlight airCargoFlight = new AirCargoFlight(AIRPORT_A, AIRPORT_B, A_DEPARTURE_DATE, AN_AIRLINE_COMPANY, airplane, A_PRICE);

        assertFalse(airCargoFlight.isGoingTo(AIRPORT_A));
    }

    @Test
    public void givenAAirCargoFlightLeavingOnDateA_whenCheckingIfItsLeavingOnDateB_thenItsNot() {
        AirCargoFlight airCargoFlight = new AirCargoFlight(AIRPORT_A, AIRPORT_B, A_DEPARTURE_DATE, AN_AIRLINE_COMPANY, airplane, A_PRICE);

        assertFalse(airCargoFlight.isLeavingAfterOrOn(ANOTHER_DEPARTURE_DATE));
    }

    @Test
    public void givenAAirCargoFlight_whenCheckingIfItAcceptsAWeight_thenItsDelegatedToTheAirplane() {
        AirCargoFlight airCargoFlight = givenAAirCargoFlight();

        airCargoFlight.acceptsWeight(A_WEIGHT);

        verify(airplane).acceptsWeight(A_WEIGHT);
    }
    
    @Test
    public void givenAAirCargoFlight_whenCheckingIfItIsAirVivant_thenItsDelegatedToTheAirplane() {
        AirCargoFlight airCargoFlight = givenAAirCargoFlight();

        airCargoFlight.isAirVivant();

        verify(airplane).isAirVivant();
    }

    @Test
    public void givenAAirCargoFlight_whenReservingSpace_thenAvailableSpaceDecreases() {
        AirCargoFlight airCargoFlight = givenAAirCargoFlight();

        airCargoFlight.reserveSpace(A_WEIGHT);
    }

    @Test
    public void givenAAirCargoFlight_whenReleasingSpace_thenAvailableSeatsIncreases() {
        AirCargoFlight airCargoFlight = givenAAirCargoFlight();

        airCargoFlight.releaseSpace(A_WEIGHT);
    }

    private AirCargoFlight givenAAirCargoFlight() {
        return new AirCargoFlight(AIRPORT_A, AIRPORT_B, A_DEPARTURE_DATE, AN_AIRLINE_COMPANY, airplane, A_PRICE);
    }
}
