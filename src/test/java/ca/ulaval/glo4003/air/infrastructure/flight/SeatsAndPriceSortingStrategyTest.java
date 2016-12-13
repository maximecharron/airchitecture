package ca.ulaval.glo4003.air.infrastructure.flight;

import ca.ulaval.glo4003.air.domain.flight.FlightSortingStrategy;
import ca.ulaval.glo4003.air.domain.flight.PassengerFlight;
import ca.ulaval.glo4003.air.domain.flight.SeatsPricing;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.util.Collections.emptyList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.mockito.BDDMockito.willReturn;

@RunWith(MockitoJUnitRunner.class)
public class SeatsAndPriceSortingStrategyTest {

    private static final LocalDateTime FIRST_DEPARTURE = LocalDateTime.of(2018, 12, 24, 9, 45);
    private static final double CHEAP_PRICE = 300.65;
    private static final double NORMAL_PRICE = 900.99;
    private static final double EXPENSIVE_PRICE = 1800;
    private static final int ALMOST_NO_PLACES_LEFT = 1;
    private static final int SOME_PLACES_LEFT = 40;
    private static final int LOTS_OF_PLACES_LEFT = 150;

    @Mock
    private PassengerFlight earlyFlightWithLotsOfPlacesLeft;

    @Mock
    private PassengerFlight earlyFlightWithSomePlacesLeft;

    @Mock
    private PassengerFlight laterCheapFlight;

    @Mock
    private PassengerFlight laterNormalFlight;

    @Mock
    private PassengerFlight laterExpensiveFlight;

    private FlightSortingStrategy flightSortingStrategy;

    @Before
    public void setup() {
        flightSortingStrategy = new SeatsAndPriceSortingStrategy();

        mockFlight(earlyFlightWithSomePlacesLeft, false, FIRST_DEPARTURE.plusHours(1), SOME_PLACES_LEFT, new SeatsPricing(CHEAP_PRICE, NORMAL_PRICE, EXPENSIVE_PRICE));
        mockFlight(earlyFlightWithLotsOfPlacesLeft, false, FIRST_DEPARTURE, LOTS_OF_PLACES_LEFT, new SeatsPricing(CHEAP_PRICE, NORMAL_PRICE, EXPENSIVE_PRICE));
        mockFlight(laterCheapFlight, true, FIRST_DEPARTURE.plusHours(26), LOTS_OF_PLACES_LEFT, new SeatsPricing(CHEAP_PRICE, NORMAL_PRICE, EXPENSIVE_PRICE));
        mockFlight(laterNormalFlight, true, FIRST_DEPARTURE.plusHours(24), ALMOST_NO_PLACES_LEFT, new SeatsPricing(NORMAL_PRICE, NORMAL_PRICE, NORMAL_PRICE));
        mockFlight(laterExpensiveFlight, true, FIRST_DEPARTURE.plusHours(42), SOME_PLACES_LEFT, new SeatsPricing(NORMAL_PRICE, NORMAL_PRICE, EXPENSIVE_PRICE));
    }

    @Test
    public void givenNoFlights_whenSorting_thenAnEmptyArrayIsReturned() {
        List<PassengerFlight> flights = emptyList();

        List<PassengerFlight> sortedFlights = flightSortingStrategy.sort(flights);

        assertThat(sortedFlights, is(emptyList()));
    }

    @Test
    public void givenOnlyOneFlight_whenSorting_thenItsReturned() {
        List<PassengerFlight> flights = Collections.singletonList(laterNormalFlight);

        List<PassengerFlight> sortedFlights = flightSortingStrategy.sort(flights);

        assertThat(sortedFlights, contains(laterNormalFlight));
    }

    @Test
    public void givenFlightsInTheSame24hSlice_whenSortingThem_thenTheFlightsInTheSame24hSliceAreSortedBySeatsLeft() {
        List<PassengerFlight> flights = Arrays.asList(earlyFlightWithLotsOfPlacesLeft, earlyFlightWithSomePlacesLeft);

        List<PassengerFlight> sortedFlights = flightSortingStrategy.sort(flights);

        assertThat(sortedFlights, contains(earlyFlightWithSomePlacesLeft, earlyFlightWithLotsOfPlacesLeft));
    }

    @Test
    public void givenEarlyFlightsAndLaterFlights_whenSortingThem_thenTheFirstAreSortedBySeatsLeftAndTheOthersByPrice() {
        List<PassengerFlight> flights = Arrays.asList(earlyFlightWithLotsOfPlacesLeft, laterCheapFlight, earlyFlightWithSomePlacesLeft, laterExpensiveFlight, laterNormalFlight);

        List<PassengerFlight> sortedFlights = flightSortingStrategy.sort(flights);

        assertThat(sortedFlights, contains(earlyFlightWithSomePlacesLeft, earlyFlightWithLotsOfPlacesLeft, laterCheapFlight, laterNormalFlight, laterExpensiveFlight));
    }

    public void mockFlight(PassengerFlight mockedFlight, boolean isInTheSame24hSlice, LocalDateTime departureDate, int totalSeatsLeft, SeatsPricing seatsPricing) {
        willReturn(isInTheSame24hSlice).given(mockedFlight).isLeavingAfterOrOn(FIRST_DEPARTURE.plusHours(24));
        willReturn(departureDate).given(mockedFlight).getDepartureDate();
        willReturn(totalSeatsLeft).given(mockedFlight).totalSeatsLeft();
        willReturn(seatsPricing.getEconomicSeatsPrice()).given(mockedFlight).getEconomicSeatsPrice();
        willReturn(seatsPricing.getRegularSeatsPrice()).given(mockedFlight).getRegularSeatsPrice();
        willReturn(seatsPricing.getBusinessSeatsPrice()).given(mockedFlight).getBusinessSeatsPrice();
    }
}
