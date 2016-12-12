package ca.ulaval.glo4003.air.infrastructure.flight;

import ca.ulaval.glo4003.air.domain.airplane.Airplane;
import ca.ulaval.glo4003.air.domain.flight.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FlightDevDataFactory {

    private static final String AIR_NORMAL = "AirNormal";
    private static final String AIR_CARGO = "AirCargo";

    public List<Flight> createMockData(List<Airplane> airplanes, AvailableSeatsFactory availableSeatsFactory) {
        List<Flight> flights = new ArrayList<>();

        Flight quebecToDublin = new PassengerFlight.PassengerFlightBuilder()
            .departureAirport("YQB")
            .arrivalAirport("DUB")
            .departureDate(LocalDateTime.of(2017, 4, 23, 20, 15))
            .airlineCompany(AIR_NORMAL)
            .airplane(airplanes.get(0))
            .seatsPricing(new SeatsPricing(80, 90, 100))
            .availableSeatsFactory(availableSeatsFactory)
            .build();

        Flight quebecToDublinAirCargo = new AirCargoFlight("YQB", "DUB", LocalDateTime.of(2017, 4, 24, 20, 15), AIR_CARGO, airplanes.get(0), 30);

        Flight osloToMontreal = new PassengerFlight.PassengerFlightBuilder()
            .departureAirport("OSL")
            .arrivalAirport("YUL")
            .departureDate(LocalDateTime.of(2018, 6, 14, 21, 0))
            .airlineCompany(AIR_NORMAL)
            .airplane(airplanes.get(2))
            .seatsPricing(new SeatsPricing(300.12, 450.99, 829.29))
            .availableSeatsFactory(availableSeatsFactory)
            .build();

        SeatsPricing pricing3 = new SeatsPricing(300.12, 300.12, 300.12);
        Flight osloToMontreal = new PassengerFlight("OSL", "YUL", LocalDateTime.of(2018, 6, 14, 21, 0), AIR_NORMAL, airplanes.get(2), pricing3, availableSeatsFactory);

        Flight montrealToOslo = new PassengerFlight.PassengerFlightBuilder()
            .departureAirport("YUL")
            .arrivalAirport("OSL")
            .departureDate(LocalDateTime.of(2018, 8, 16, 21, 2))
            .airlineCompany(AIR_NORMAL)
            .airplane(airplanes.get(4))
            .seatsPricing(new SeatsPricing(250.99, 350.99, 450.99))
            .availableSeatsFactory(availableSeatsFactory)
            .build();

        SeatsPricing pricing5 = new SeatsPricing(250.99, 350.99, 450.99);
        Flight montrealToOslo = new PassengerFlight("YUL", "OSL", LocalDateTime.of(2018, 8, 16, 19, 0), AIR_NORMAL, airplanes.get(4), pricing5, availableSeatsFactory);

        SeatsPricing pricing6 = new SeatsPricing(250.99, 350.99, 450.99);
        Flight montrealToOslo3 = new PassengerFlight("YUL", "OSL", LocalDateTime.of(2018, 10, 22, 10, 0), AIR_NORMAL, airplanes.get(5), pricing6, availableSeatsFactory);

        SeatsPricing pricing7 = new SeatsPricing(400, 350.99, 900);
        Flight montrealToOslo4 = new PassengerFlight("YUL", "OSL", LocalDateTime.of(2018, 11, 4, 0, 0), AIR_NORMAL, airplanes.get(6), pricing7, availableSeatsFactory);

        SeatsPricing pricing8 = new SeatsPricing(400, 350.99, 1200);
        Flight montrealToOslo5 = new PassengerFlight("YUL", "OSL", LocalDateTime.of(2018, 12, 5, 0, 0), AIR_NORMAL, airplanes.get(7), pricing8, availableSeatsFactory);

        flights.addAll(Arrays.asList(quebecToDublin, quebecToDublinAirCargo, torontoToLondon, montrealToOslo, montrealToOslo2, osloToMontreal, montrealToOslo3, montrealToOslo4, montrealToOslo5));
        return flights;
    }
}
