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
public class FlightTest {

    private static final double A_WEIGHT = 40.5;
    private static final String AIRPORT_A = "YQB";
    private static final String AIRPORT_B = "DUB";
    private static final String AN_AIRLINE_COMPANY = "AirDariusRuckerWagonWheel";
    private static final LocalDateTime A_DEPARTURE_DATE = LocalDateTime.of(2016, 10, 10, 9, 55);
    private static final LocalDateTime ANOTHER_DEPARTURE_DATE = LocalDateTime.of(2018, 8, 10, 9, 55);
    private static final int ECONOMIC_SEATS = 10;
    private static final int REGULAR_SEATS = 8;
    private static final int BUSINESS_SEATS = 3;
    private static final SeatMap A_SEAT_MAP = new SeatMap(ECONOMIC_SEATS, REGULAR_SEATS, BUSINESS_SEATS);


    @Mock
    private Airplane airplane;

    @Mock
    private SeatsPricing seatsPricing;

    @Mock
    private AvailableSeatsFactory availableSeatsFactory;

    @Mock
    private AvailableSeats availableSeats;

    @Before
    public void setup() {
        given(availableSeatsFactory.createFromSeatMap(A_SEAT_MAP)).willReturn(availableSeats);
        given(airplane.getSeatMap()).willReturn(A_SEAT_MAP);
    }

    @Test
    public void givenAFlight_whenCheckingIfItsLeavingAfterADateFollowingItsDepartureDate_thenItsNot() {
        Flight flight = new PassengerFlight(AIRPORT_A, AIRPORT_B, A_DEPARTURE_DATE, AN_AIRLINE_COMPANY, airplane, seatsPricing, availableSeatsFactory);

        boolean result = flight.isLeavingAfter(A_DEPARTURE_DATE.plusDays(1));

        assertFalse(result);
    }

    @Test
    public void givenAFlight_whenCheckingIfItsLeavingAfterADatePriorToItsDepartureDate_thenItIs() {
        Flight flight = new PassengerFlight(AIRPORT_A, AIRPORT_B, A_DEPARTURE_DATE, AN_AIRLINE_COMPANY, airplane, seatsPricing, availableSeatsFactory);

        boolean result = flight.isLeavingAfter(A_DEPARTURE_DATE.minusDays(1));

        assertTrue(result);
    }

    @Test
    public void givenAFlight_whenCheckingIfItsLeavingAfterItsDepartureDate_thenItsNot() {
        Flight flight = new PassengerFlight(AIRPORT_A, AIRPORT_B, A_DEPARTURE_DATE, AN_AIRLINE_COMPANY, airplane, seatsPricing, availableSeatsFactory);

        boolean result = flight.isLeavingAfter(A_DEPARTURE_DATE);

        assertFalse(result);
    }

    @Test
    public void givenAFlightDepartingFromA_whenCheckingIfItsLeavingFromA_thenItIs() {
        Flight flight = new PassengerFlight(AIRPORT_A, AIRPORT_B, A_DEPARTURE_DATE, AN_AIRLINE_COMPANY, airplane, seatsPricing, availableSeatsFactory);

        assertTrue(flight.isDepartingFrom(AIRPORT_A));
    }

    @Test
    public void givenAFlightLeavingToB_whenCheckingIfItsGoingToB_thenItIs() {
        Flight flight = new PassengerFlight(AIRPORT_A, AIRPORT_B, A_DEPARTURE_DATE, AN_AIRLINE_COMPANY, airplane, seatsPricing, availableSeatsFactory);

        assertTrue(flight.isGoingTo(AIRPORT_B));
    }

    @Test
    public void givenAFlightDepartingFromA_whenCheckingIfItsLeavingFromB_thenItIsNot() {
        Flight flight = new PassengerFlight(AIRPORT_A, AIRPORT_B, A_DEPARTURE_DATE, AN_AIRLINE_COMPANY, airplane, seatsPricing, availableSeatsFactory);

        assertFalse(flight.isDepartingFrom(AIRPORT_B));
    }

    @Test
    public void givenAFlightLeavingToB_whenCheckingIfItsGoingToA_thenItIsNot() {
        Flight flight = new PassengerFlight(AIRPORT_A, AIRPORT_B, A_DEPARTURE_DATE, AN_AIRLINE_COMPANY, airplane, seatsPricing, availableSeatsFactory);

        assertFalse(flight.isGoingTo(AIRPORT_A));
    }

    @Test
    public void givenAFlightLeavingOnADate_whenCheckingIfItsLeavingOnThisDate_thenItIs() {
        Flight flight = new PassengerFlight(AIRPORT_A, AIRPORT_B, A_DEPARTURE_DATE, AN_AIRLINE_COMPANY, airplane, seatsPricing, availableSeatsFactory);

        assertTrue(flight.isLeavingOn(A_DEPARTURE_DATE));
    }

    @Test
    public void givenAFlightLeavingOnDateA_whenCheckingIfItsLeavingOnDateB_thenItsNot() {
        Flight flight = new PassengerFlight(AIRPORT_A, AIRPORT_B, A_DEPARTURE_DATE, AN_AIRLINE_COMPANY, airplane, seatsPricing, availableSeatsFactory);

        assertFalse(flight.isLeavingOn(ANOTHER_DEPARTURE_DATE));
    }

    @Test
    public void givenAFlight_whenCheckingIfItAcceptsAWeight_thenItsDelegatedToTheAirplane() {
        Flight flight = givenAPassengerFlight();

        flight.acceptsWeight(A_WEIGHT);

        verify(airplane).acceptsWeight(A_WEIGHT);
    }

    @Test
    public void givenAFlight_whenCheckingIfAcceptingAdditionalWeight_thenItsDelegatedToTheAirplane() {
        Flight flight = givenAPassengerFlight();

        flight.acceptsAdditionalWeight(A_WEIGHT);

        verify(airplane).acceptsAdditionalWeight(A_WEIGHT);
    }

    @Test
    public void givenAFlight_whenCheckingIfItHasAdditionalWeightOption_thenItsDelegatedToTheAirplane() {
        Flight flight = givenAPassengerFlight();

        flight.hasAdditionalWeightOption();

        verify(airplane).hasAdditionalWeightOption();
    }

    @Test
    public void givenAFlight_whenCheckingIfItIsAirVivant_thenItsDelegatedToTheAirplane() {
        Flight flight = givenAPassengerFlight();

        flight.isAirVivant();

        verify(airplane).isAirVivant();
    }

    @Test
    public void givenAFlight_whenReservingPlaces_thenAvailableSeatsDecreases() {
        PassengerFlight flight = givenAPassengerFlight();

        flight.reserveSeats(A_SEAT_MAP);

        verify(availableSeats).reserve(A_SEAT_MAP);
    }

    @Test
    public void givenAFlight_whenReleasingPlaces_thenAvailableSeatsIncreases() {
        PassengerFlight flight = givenAPassengerFlight();

        flight.releaseSeats(A_SEAT_MAP);

        verify(availableSeats).release(A_SEAT_MAP);
    }

    private PassengerFlight givenAPassengerFlight() {
        return new PassengerFlight(AIRPORT_A, AIRPORT_B, A_DEPARTURE_DATE, AN_AIRLINE_COMPANY, airplane, seatsPricing, availableSeatsFactory);
    }
}
