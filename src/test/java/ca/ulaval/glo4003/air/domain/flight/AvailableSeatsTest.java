package ca.ulaval.glo4003.air.domain.flight;

import ca.ulaval.glo4003.air.domain.airplane.SeatMap;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AvailableSeatsTest {

    private static final int ECONOMIC_SEATS = 10;
    private static final int REGULAR_SEATS = 5;
    private static final int BUSINESS_SEATS = 3;

    private AvailableSeats availableSeats;

    @Before
    public void setup() {
        SeatMap seatMap = new SeatMap(ECONOMIC_SEATS, REGULAR_SEATS, BUSINESS_SEATS);
        availableSeats = new AvailableSeats(seatMap);
    }

    @Test
    public void givenAvailableSeats_whenReservingSomeSeats_thenTheTotalIsUpdated() {
        SeatMap seatsToReserve = new SeatMap(10, 4, 1);

        availableSeats.reserve(seatsToReserve);

        assertEquals(availableSeats.getEconomicSeats(), ECONOMIC_SEATS - 10);
        assertEquals(availableSeats.getRegularSeats(), REGULAR_SEATS - 4);
        assertEquals(availableSeats.getBusinessSeats(), BUSINESS_SEATS - 1);
    }

    @Test
    public void givenAvailableSeats_whenReleasingSomeSeats_thenTheTotalIsUpdated() {
        SeatMap seatsToReserve = new SeatMap(1, 2, 3);

        availableSeats.release(seatsToReserve);

        assertEquals(availableSeats.getEconomicSeats(), ECONOMIC_SEATS + 1);
        assertEquals(availableSeats.getRegularSeats(), REGULAR_SEATS + 2);
        assertEquals(availableSeats.getBusinessSeats(), BUSINESS_SEATS + 3);
    }

    @Test
    public void givenAvailableSeats_whenQueryingTotalSeatsLeft_thenItsTheSumOfAllSeatsCategory() {
        int totalSeatsLeft = availableSeats.totalSeatsLeft();

        assertEquals(ECONOMIC_SEATS + REGULAR_SEATS + BUSINESS_SEATS, totalSeatsLeft);
    }
}
