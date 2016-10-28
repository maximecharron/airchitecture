package ca.ulaval.glo4003.air.persistence.flight;

import ca.ulaval.glo4003.air.domain.airplane.AirLegerAirplane;
import ca.ulaval.glo4003.air.domain.airplane.AirLourdAirplane;
import ca.ulaval.glo4003.air.domain.airplane.AirMoyenAirplane;
import ca.ulaval.glo4003.air.domain.airplane.Airplane;
import ca.ulaval.glo4003.air.domain.flight.Flight;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FlightDevDataFactory {

    private static final String AIRLINE_COMPANY = "AirFrenette";

    public List<Flight> createMockData() {
        List<Flight> flights = new ArrayList<>();

        Airplane airplane1 = new AirLegerAirplane(100);
        Flight quebecToDublin = new Flight( "YQB", "DUB", LocalDateTime.of(2017, 4, 23, 20, 15), AIRLINE_COMPANY, airplane1, 127);

        Airplane airplane2 = new AirMoyenAirplane(2);
        Flight torontoToLondon = new Flight("YXU", "YYZ", LocalDateTime.of(2016, 10, 20, 9, 45), AIRLINE_COMPANY, airplane2, 134.00f);

        Airplane airplane3 = new AirLegerAirplane(42);
        Flight montrealToOslo = new Flight("OSL", "YUL", LocalDateTime.of(2018, 6, 14, 21, 0), AIRLINE_COMPANY, airplane3, 54.98f);

        Airplane airplane4 = new AirLourdAirplane(17, 1000);
        Flight montrealToOslo2 = new Flight("YUL", "OSL", LocalDateTime.of(2018, 8, 15, 21, 0), AIRLINE_COMPANY, airplane4, 245.45f);

        Airplane airplane5 = new AirLourdAirplane(13, 0);
        Flight osloToMontreal = new Flight("YUL", "OSL", LocalDateTime.of(2018, 8, 16, 21, 2), AIRLINE_COMPANY, airplane5, 890.65f);

        flights.addAll(Arrays.asList(quebecToDublin, torontoToLondon, montrealToOslo, montrealToOslo2, osloToMontreal));
        return flights;
    }
}
