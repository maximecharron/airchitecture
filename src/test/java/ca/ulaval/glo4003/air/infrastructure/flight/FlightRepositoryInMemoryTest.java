package ca.ulaval.glo4003.air.infrastructure.flight;

import ca.ulaval.glo4003.air.domain.flight.AirCargoFlight;
import ca.ulaval.glo4003.air.domain.flight.FlightRepository;
import ca.ulaval.glo4003.air.domain.flight.PassengerFlight;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class FlightRepositoryInMemoryTest {
    private static final String AIRLINE_COMPANY = "AirFrenette";
    private static final String ARRIVAL_AIRPORT = "ABC";
    private static final LocalDateTime DATE = LocalDateTime.of(2020, 10, 2, 6, 30);

    private FlightRepository flightRepository;

    @Mock
    private PassengerFlight passengerFlight;
    @Mock
    private AirCargoFlight airCargoFlight;

    @Before
    public void setup() {
        flightRepository = new FlightRepositoryInMemory();

        given(passengerFlight.getAirlineCompany()).willReturn(AIRLINE_COMPANY);
        given(passengerFlight.getArrivalAirport()).willReturn(ARRIVAL_AIRPORT);
        given(passengerFlight.getDepartureDate()).willReturn(DATE);
        given(passengerFlight.isPassengerFlight()).willReturn(true);
        given(airCargoFlight.getAirlineCompany()).willReturn(AIRLINE_COMPANY);
        given(airCargoFlight.getArrivalAirport()).willReturn(ARRIVAL_AIRPORT);
        given(airCargoFlight.getDepartureDate()).willReturn(DATE);
        given(airCargoFlight.isAirCargo()).willReturn(true);
    }

    @Test
    public void givenAPassengerFlight_whenPersistingTheFlight_theFlightIsPersisted() {
        flightRepository.save(passengerFlight);
        final Optional<PassengerFlight> result = flightRepository.query().getOnePassengerFlight();

        assertTrue(result.isPresent());
    }

    @Test
    public void givenAnAirCargoFlight_whenPersistingTheFlight_theFlightIsPersisted() {
        flightRepository.save(airCargoFlight);
        final Optional<AirCargoFlight> result = flightRepository.query().getOneAirCargoFlight();

        assertTrue(result.isPresent());
    }
}
