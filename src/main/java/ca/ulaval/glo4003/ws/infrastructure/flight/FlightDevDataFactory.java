package ca.ulaval.glo4003.ws.infrastructure.flight;

import ca.ulaval.glo4003.ws.domain.flight.Flight;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FlightDevDataFactory {

    private static final String AIRLINE_COMPANY = "AirFrenette";

    public List<Flight> createMockData() {
        List<Flight> flights = new ArrayList<>();

        Flight quebecToDublin = new Flight();
        quebecToDublin.setFlightNumber("AF0001");
        quebecToDublin.setAirlineCompany(AIRLINE_COMPANY);
        quebecToDublin.setDepartureDate(LocalDateTime.of(2017, 4, 23, 20, 15));
        quebecToDublin.setDepartureAirport("YQB");
        quebecToDublin.setArrivalAirport("DUB");
        quebecToDublin.setAvailableSeats(100);

        Flight torontoToLondon = new Flight();
        torontoToLondon.setFlightNumber("AF0002");
        torontoToLondon.setAirlineCompany(AIRLINE_COMPANY);
        torontoToLondon.setDepartureDate(LocalDateTime.of(2016, 10, 20, 9, 45));
        torontoToLondon.setDepartureAirport("YXU");
        torontoToLondon.setArrivalAirport("YYZ");
        torontoToLondon.setAvailableSeats(2);

        Flight montrealToOslo = new Flight();
        montrealToOslo.setFlightNumber("AF0003");
        montrealToOslo.setAirlineCompany(AIRLINE_COMPANY);
        montrealToOslo.setDepartureDate(LocalDateTime.of(2018, 6, 14, 21, 0));
        montrealToOslo.setDepartureAirport("YUL");
        montrealToOslo.setArrivalAirport("OSL");
        montrealToOslo.setAvailableSeats(42);

        Flight montrealToOslo2 = new Flight();
        montrealToOslo2.setFlightNumber("AF0004");
        montrealToOslo2.setAirlineCompany(AIRLINE_COMPANY);
        montrealToOslo2.setDepartureDate(LocalDateTime.of(2018, 8, 15, 21, 0));
        montrealToOslo2.setDepartureAirport("YUL");
        montrealToOslo2.setArrivalAirport("OSL");
        montrealToOslo2.setAvailableSeats(17);

        Flight osloToMontreal = new Flight();
        montrealToOslo2.setFlightNumber("AF0005");
        montrealToOslo2.setAirlineCompany(AIRLINE_COMPANY);
        montrealToOslo2.setDepartureDate(LocalDateTime.of(2018, 8, 16, 21, 2));
        montrealToOslo2.setDepartureAirport("OSL");
        montrealToOslo2.setArrivalAirport("YUL");
        montrealToOslo2.setAvailableSeats(13);

        flights.addAll(Arrays.asList(quebecToDublin, torontoToLondon, montrealToOslo, montrealToOslo2, osloToMontreal));
        return flights;
    }
}
