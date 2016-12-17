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
public class PassengerFlightTest {

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
    public void givenAPassengerFlight_whenCheckingIfItsLeavingAfterOrOnADateFollowingItsDepartureDate_thenItsNot() {
        PassengerFlight passengerFlight = givenAPassengerFlight();

        boolean result = passengerFlight.isLeavingAfterOrOn(A_DEPARTURE_DATE.plusDays(1));

        assertFalse(result);
    }

    @Test
    public void givenAPassengerFlight_whenCheckingIfItsLeavingAfterOrOnADatePriorToItsDepartureDate_thenItIs() {
        PassengerFlight passengerFlight = givenAPassengerFlight();

        boolean result = passengerFlight.isLeavingAfterOrOn(A_DEPARTURE_DATE.minusDays(1));

        assertTrue(result);
    }

    @Test
    public void givenAPassengerFlight_whenCheckingIfItsLeavingAfterOrOnItsDepartureDate_thenItIs() {
        PassengerFlight passengerFlight = givenAPassengerFlight();

        boolean result = passengerFlight.isLeavingAfterOrOn(A_DEPARTURE_DATE);

        assertTrue(result);
    }

    @Test
    public void givenAPassengerFlightDepartingFromA_whenCheckingIfItsLeavingFromA_thenItIs() {
        PassengerFlight passengerFlight = givenAPassengerFlight();

        assertTrue(passengerFlight.isDepartingFrom(AIRPORT_A));
    }

    @Test
    public void givenAPassengerFlightLeavingToB_whenCheckingIfItsGoingToB_thenItIs() {
        PassengerFlight passengerFlight = givenAPassengerFlight();

        assertTrue(passengerFlight.isGoingTo(AIRPORT_B));
    }

    @Test
    public void givenAPassengerFlightDepartingFromA_whenCheckingIfItsLeavingFromB_thenItIsNot() {
        PassengerFlight passengerFlight = givenAPassengerFlight();

        assertFalse(passengerFlight.isDepartingFrom(AIRPORT_B));
    }

    @Test
    public void givenAPassengerFlightLeavingToB_whenCheckingIfItsGoingToA_thenItIsNot() {
        PassengerFlight passengerFlight = givenAPassengerFlight();

        assertFalse(passengerFlight.isGoingTo(AIRPORT_A));
    }

    @Test
    public void givenAPassengerFlightLeavingOnDateA_whenCheckingIfItsLeavingOnDateB_thenItsNot() {
        PassengerFlight passengerFlight = givenAPassengerFlight();

        assertFalse(passengerFlight.isLeavingAfterOrOn(ANOTHER_DEPARTURE_DATE));
    }

    @Test
    public void givenAPassengerFlight_whenCheckingIfItAcceptsAWeight_thenItsDelegatedToTheAirplane() {
        PassengerFlight passengerFlight = givenAPassengerFlight();

        passengerFlight.acceptsWeight(A_WEIGHT);

        verify(airplane).acceptsWeight(A_WEIGHT);
    }

    @Test
    public void givenAPassengerFlight_whenCheckingIfAcceptingAdditionalWeight_thenItsDelegatedToTheAirplane() {
        PassengerFlight passengerFlight = givenAPassengerFlight();

        passengerFlight.acceptsAdditionalWeight(A_WEIGHT);

        verify(airplane).acceptsAdditionalWeight(A_WEIGHT);
    }

    @Test
    public void givenAPassengerFlight_whenCheckingIfItHasAdditionalWeightOption_thenItsDelegatedToTheAirplane() {
        PassengerFlight passengerFlight = givenAPassengerFlight();

        passengerFlight.hasAdditionalWeightOption();

        verify(airplane).hasAdditionalWeightOption();
    }

    @Test
    public void givenAPassengerFlight_whenCheckingIfItIsAirVivant_thenItsDelegatedToTheAirplane() {
        PassengerFlight passengerFlight = givenAPassengerFlight();

        passengerFlight.isAirVivant();

        verify(airplane).isAirVivant();
    }

    @Test
    public void givenAPassengerFlight_whenReservingPlaces_thenAvailableSeatsDecreases() {
        PassengerFlight passengerFlight = givenAPassengerFlight();

        passengerFlight.reserveSeats(A_SEAT_MAP);

        verify(availableSeats).reserve(A_SEAT_MAP);
    }

    @Test
    public void givenAPassengerFlight_whenReleasingPlaces_thenAvailableSeatsIncreases() {
        PassengerFlight passengerFlight = givenAPassengerFlight();

        passengerFlight.releaseSeats(A_SEAT_MAP);

        verify(availableSeats).release(A_SEAT_MAP);
    }

    private PassengerFlight givenAPassengerFlight() {
        return new PassengerFlight.PassengerFlightBuilder()
            .departureAirport(AIRPORT_A)
            .arrivalAirport(AIRPORT_B)
            .departureDate(A_DEPARTURE_DATE)
            .airlineCompany(AN_AIRLINE_COMPANY)
            .airplane(airplane)
            .seatsPricing(seatsPricing)
            .availableSeatsFactory(availableSeatsFactory)
            .build();
    }
}
