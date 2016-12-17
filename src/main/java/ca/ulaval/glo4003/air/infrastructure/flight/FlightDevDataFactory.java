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

        Flight quebecToDublinAirCargo = new AirCargoFlight.AirCargoFlightBuilder()
            .departureAirport("YQB")
            .arrivalAirport("DUB")
            .departureDate(LocalDateTime.of(2017, 4, 24, 20, 15))
            .airlineCompany(AIR_CARGO)
            .airplane(airplanes.get(0))
            .price(30)
            .build();

        Flight torontoToLondon = new PassengerFlight.PassengerFlightBuilder()
            .departureAirport("YXU")
            .arrivalAirport("YYZ")
            .departureDate(LocalDateTime.of(2016, 10, 20, 9, 45))
            .airlineCompany(AIR_NORMAL)
            .airplane(airplanes.get(1))
            .seatsPricing(new SeatsPricing(12.70, 44.67, 98.11))
            .availableSeatsFactory(availableSeatsFactory)
            .build();

        Flight osloToMontreal = new PassengerFlight.PassengerFlightBuilder()
            .departureAirport("OSL")
            .arrivalAirport("YUL")
            .departureDate(LocalDateTime.of(2018, 6, 14, 21, 0))
            .airlineCompany(AIR_NORMAL)
            .airplane(airplanes.get(2))
            .seatsPricing(new SeatsPricing(300.12, 300.12, 300.12))
            .availableSeatsFactory(availableSeatsFactory)
            .build();

        Flight montrealToOslo2 = new PassengerFlight.PassengerFlightBuilder()
            .departureAirport("YUL")
            .arrivalAirport("OSL")
            .departureDate(LocalDateTime.of(2018, 8, 15, 21, 0))
            .airlineCompany(AIR_NORMAL)
            .airplane(airplanes.get(3))
            .seatsPricing(new SeatsPricing(301.50, 402.12, 503.05))
            .availableSeatsFactory(availableSeatsFactory)
            .build();

        Flight montrealToOslo = new PassengerFlight.PassengerFlightBuilder()
            .departureAirport("YUL")
            .arrivalAirport("OSL")
            .departureDate(LocalDateTime.of(2018, 8, 16, 19, 0))
            .airlineCompany(AIR_NORMAL)
            .airplane(airplanes.get(4))
            .seatsPricing(new SeatsPricing(250.99, 350.99, 450.99))
            .availableSeatsFactory(availableSeatsFactory)
            .build();

        Flight montrealToOslo3 = new PassengerFlight.PassengerFlightBuilder()
            .departureAirport("YUL")
            .arrivalAirport("OSL")
            .departureDate(LocalDateTime.of(2018, 10, 22, 10, 0))
            .airlineCompany(AIR_NORMAL)
            .airplane(airplanes.get(5))
            .seatsPricing(new SeatsPricing(250.99, 350.99, 450.99))
            .availableSeatsFactory(availableSeatsFactory)
            .build();

        Flight montrealToOslo4 = new PassengerFlight.PassengerFlightBuilder()
            .departureAirport("YUL")
            .arrivalAirport("OSL")
            .departureDate(LocalDateTime.of(2018, 10, 4, 0, 0))
            .airlineCompany(AIR_NORMAL)
            .airplane(airplanes.get(6))
            .seatsPricing(new SeatsPricing(400, 350.99, 900))
            .availableSeatsFactory(availableSeatsFactory)
            .build();

        Flight montrealToOslo5 = new PassengerFlight.PassengerFlightBuilder()
            .departureAirport("YUL")
            .arrivalAirport("OSL")
            .departureDate(LocalDateTime.of(2018, 10, 5, 0, 0))
            .airlineCompany(AIR_NORMAL)
            .airplane(airplanes.get(7))
            .seatsPricing(new SeatsPricing(400, 350.99, 1200))
            .availableSeatsFactory(availableSeatsFactory)
            .build();

        flights.addAll(Arrays.asList(quebecToDublin, quebecToDublinAirCargo, torontoToLondon, montrealToOslo, montrealToOslo2, osloToMontreal, montrealToOslo3, montrealToOslo4, montrealToOslo5));
        return flights;
    }
}
