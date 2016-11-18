package ca.ulaval.glo4003.air.domain.airplane;

import ca.ulaval.glo4003.air.api.airplane.dto.AirplaneDto;
import ca.ulaval.glo4003.air.api.airplane.dto.AirplaneSearchResultDto;
import ca.ulaval.glo4003.air.api.flight.dto.FlightDto;
import ca.ulaval.glo4003.air.api.flight.dto.FlightSearchResultDto;
import ca.ulaval.glo4003.air.domain.airplane.Airplane;
import ca.ulaval.glo4003.air.transfer.airplane.AirplaneAssembler;
import ca.ulaval.glo4003.air.transfer.flight.FlightAssembler;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyDouble;

@RunWith(MockitoJUnitRunner.class)
public class AirplaneAssemblerTest {

    private static final String SERIAL_NUMBER = "ZacBrownBand#1";
    private static final int SEATS = 42;
    private static final double WEIGHT = 50;
    private static final double ACCEPTED_ADDITIONNAL_WEIGHT = 100;

    private AirplaneAssembler airplaneAssembler;

    @Before
    public void setup() {
        airplaneAssembler = new AirplaneAssembler();
    }

    @Test
    public void givenAnAirplane_whenCreatingAnAirplaneDto_thenItHasAllTheRelevantProperties() {
        AirLourdAirplane airplane = givenAnAirplane();

        AirplaneDto airplaneDto = airplaneAssembler.createAirplane(airplane);

        assertHasAllTheRelevantProperties(airplane, airplaneDto);
    }

    @Test
    public void givenAirplanes_whenCreatingAnAirplaneSearchDto_thenAirplanesAreMappedToTheirEquivalentDto() {
        AirLourdAirplane airplane = givenAnAirplane();
        List<Airplane> airplanes = Stream.of(airplane).collect(Collectors.toList());
        AirplaneSearchResult airplaneSearchResult = new AirplaneSearchResult(airplanes);

        AirplaneSearchResultDto airplaneSearchResultDto = airplaneAssembler.createAirplaneSearchResult(airplaneSearchResult);

        assertHasAllTheRelevantProperties(airplane, airplaneSearchResultDto.airplanes.get(0));
    }

    private AirLourdAirplane givenAnAirplane() {
        return new AirLourdAirplane(SEATS, ACCEPTED_ADDITIONNAL_WEIGHT, SERIAL_NUMBER);
    }

    private void assertHasAllTheRelevantProperties(AirLourdAirplane airplane, AirplaneDto airplaneDto) {
        assertEquals(airplane.getAvailableSeats(), airplaneDto.availableSeats);
        assertEquals(airplane.getMaximumWeight(), airplaneDto.maximumWeight, 0.01);
        assertEquals(airplane.getAcceptedAdditionalWeight(), airplaneDto.acceptedAdditionalWeight, 0.01);
        assertEquals(airplane.isAirLourd(), airplaneDto.isAirLourd);
    }
}
