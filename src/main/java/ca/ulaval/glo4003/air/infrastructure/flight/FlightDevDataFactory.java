package ca.ulaval.glo4003.air.infrastructure.flight;

import ca.ulaval.glo4003.air.domain.flight.Flight;
import ca.ulaval.glo4003.air.domain.airplane.AirLegerAirplane;
import ca.ulaval.glo4003.air.domain.airplane.AirLourdAirplane;
import ca.ulaval.glo4003.air.domain.airplane.AirMoyenAirplane;
import ca.ulaval.glo4003.air.domain.airplane.Airplane;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FlightDevDataFactory {

    private static final String AIR_LEGER = "AirLeger";
    private static final String AIR_LOURD = "AirLourd";
    private static final String AIR_MOYEN = "AirMoyen";

    public List<Flight> createMockData(List<Airplane> airplanes) {
        List<Flight> flights = new ArrayList<>();

        Flight quebecToDublin = new Flight("YQB", "DUB", LocalDateTime.of(2017, 4, 23, 20, 15), AIR_LEGER, airplanes.get(0), 127);
        Flight torontoToLondon = new Flight("YXU", "YYZ", LocalDateTime.of(2016, 10, 20, 9, 45), AIR_MOYEN, airplanes.get(1), 134.00f);
        Flight osloToMontreal = new Flight("OSL", "YUL", LocalDateTime.of(2018, 6, 14, 21, 0), AIR_LEGER, airplanes.get(2), 54.98f);
        Flight montrealToOslo2 = new Flight("YUL", "OSL", LocalDateTime.of(2018, 8, 15, 21, 0), AIR_LOURD, airplanes.get(3), 245.45f);
        Flight montrealToOslo = new Flight("YUL", "OSL", LocalDateTime.of(2018, 8, 16, 21, 2), AIR_LOURD, airplanes.get(4), 890.65f);

        flights.addAll(Arrays.asList(quebecToDublin, torontoToLondon, montrealToOslo, montrealToOslo2, osloToMontreal));
        return flights;
    }
}
