package ca.ulaval.glo4003.air.infrastructure.flight;

import ca.ulaval.glo4003.air.domain.airplane.Airplane;
import ca.ulaval.glo4003.air.domain.flight.Flight;
import ca.ulaval.glo4003.air.domain.flight.SeatsPricing;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FlightDevDataFactory {

    private static final String AIR_NORMAL = "AirNormal";

    public List<Flight> createMockData(List<Airplane> airplanes) {
        List<Flight> flights = new ArrayList<>();

        SeatsPricing pricing1 = new SeatsPricing(80, 90, 100);
        Flight quebecToDublin = new Flight("YQB", "DUB", LocalDateTime.of(2017, 4, 23, 20, 15), AIR_NORMAL, airplanes.get(0), pricing1);

        SeatsPricing pricing2 = new SeatsPricing(12.70, 44.67, 98.11);
        Flight torontoToLondon = new Flight("YXU", "YYZ", LocalDateTime.of(2016, 10, 20, 9, 45), AIR_NORMAL, airplanes.get(1), pricing2);

        SeatsPricing pricing3 = new SeatsPricing(300.12, 450.99, 829.29);
        Flight osloToMontreal = new Flight("OSL", "YUL", LocalDateTime.of(2018, 6, 14, 21, 0), AIR_NORMAL, airplanes.get(2), pricing3);

        SeatsPricing pricing4 = new SeatsPricing(301.50, 402.12, 503.05);
        Flight montrealToOslo2 = new Flight("YUL", "OSL", LocalDateTime.of(2018, 8, 15, 21, 0), AIR_NORMAL, airplanes.get(3), pricing4);

        SeatsPricing pricing5 = new SeatsPricing(250.99, 350.99, 450.99);
        Flight montrealToOslo = new Flight("YUL", "OSL", LocalDateTime.of(2018, 8, 16, 21, 2), AIR_NORMAL, airplanes.get(4), pricing5);

        flights.addAll(Arrays.asList(quebecToDublin, torontoToLondon, montrealToOslo, montrealToOslo2, osloToMontreal));
        return flights;
    }
}
