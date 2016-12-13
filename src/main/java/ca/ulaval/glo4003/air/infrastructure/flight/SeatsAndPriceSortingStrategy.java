package ca.ulaval.glo4003.air.infrastructure.flight;

import ca.ulaval.glo4003.air.domain.flight.FlightSortingStrategy;
import ca.ulaval.glo4003.air.domain.flight.PassengerFlight;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class SeatsAndPriceSortingStrategy implements FlightSortingStrategy {

    private static final int TWENTY_FOUR_HOURS = 24;
    private final Comparator<PassengerFlight> bySeatsLeft = Comparator.comparingInt(PassengerFlight::totalSeatsLeft);
    private final Comparator<PassengerFlight> byEconomicPrice = Comparator.comparingDouble(PassengerFlight::getEconomicSeatsPrice);
    private final Comparator<PassengerFlight> byRegularPrice = Comparator.comparingDouble(PassengerFlight::getRegularSeatsPrice);
    private final Comparator<PassengerFlight> byBusinessPrice = Comparator.comparingDouble(PassengerFlight::getBusinessSeatsPrice);

    @Override
    public List<PassengerFlight> sort(List<PassengerFlight> passengerFlights) {
        if (passengerFlights.size() <= 1) {
            return passengerFlights;
        } else {
            LocalDateTime earliestDepartureDate = findEarliestDeparture(passengerFlights);
            LocalDateTime latestDepartureDateIn24hSlice = earliestDepartureDate.plusHours(TWENTY_FOUR_HOURS);

            List<PassengerFlight> flightsSortedBySeatsLeft = filterFlightsDepartingBeforeDate(passengerFlights, latestDepartureDateIn24hSlice);
            flightsSortedBySeatsLeft.sort(bySeatsLeft);

            List<PassengerFlight> flightsSortedByPrice = filterFlightsDepartingAfterDate(passengerFlights, latestDepartureDateIn24hSlice);
            flightsSortedByPrice.sort(byEconomicPrice.thenComparing(byRegularPrice).thenComparing(byBusinessPrice));

            ArrayList<PassengerFlight> sortedFlights = new ArrayList<>(flightsSortedBySeatsLeft);
            sortedFlights.addAll(flightsSortedByPrice);

            return sortedFlights;
        }
    }

    private LocalDateTime findEarliestDeparture(List<PassengerFlight> passengerFlights) {
        return passengerFlights.stream()
                               .map(PassengerFlight::getDepartureDate)
                               .min(LocalDateTime::compareTo)
                               .get();
    }

    private List<PassengerFlight> filterFlightsDepartingBeforeDate(List<PassengerFlight> passengerFlights, LocalDateTime departureDate) {
        return passengerFlights.stream()
                               .filter(flight -> !flight.isLeavingAfterOrOn(departureDate))
                               .collect(Collectors.toList());
    }

    private List<PassengerFlight> filterFlightsDepartingAfterDate(List<PassengerFlight> passengerFlights, LocalDateTime departureDate) {
        return passengerFlights.stream()
                               .filter(flight -> flight.isLeavingAfterOrOn(departureDate))
                               .collect(Collectors.toList());
    }
}
