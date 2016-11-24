package ca.ulaval.glo4003.air.domain.airplane;

import ca.ulaval.glo4003.air.api.airplane.dto.AirplaneDto;
import ca.ulaval.glo4003.air.api.airplane.dto.AirplaneSearchResultDto;
import ca.ulaval.glo4003.air.transfer.airplane.AirplaneAssembler;
import ca.ulaval.glo4003.air.transfer.airplane.SeatMapAssembler;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class AirplaneAssemblerTest {

    private static final String SERIAL_NUMBER = "ZacBrownBand#1";
    private static final int SEATS = 42;
    private static final double ACCEPTED_ADDITIONNAL_WEIGHT = 100;
    private static final boolean IS_AIR_VIVANT = true;
    private static final SeatMap SEAT_MAP = new SeatMap(100, 60, 20);

    private AirplaneAssembler airplaneAssembler;

    @Mock
    private SeatMapAssembler seatMapAssembler;

    @Before
    public void setup() {
        airplaneAssembler = new AirplaneAssembler(seatMapAssembler);
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
        return new AirLourdAirplane(SEAT_MAP, ACCEPTED_ADDITIONNAL_WEIGHT, IS_AIR_VIVANT, SERIAL_NUMBER);
    }

    private void assertHasAllTheRelevantProperties(AirLourdAirplane airplane, AirplaneDto airplaneDto) {
        verify(seatMapAssembler).create(airplane.getSeatMap());
        assertEquals(airplane.getMaximumWeight(), airplaneDto.maximumWeight, 0.01);
        assertEquals(airplane.getSerialNumber(), airplaneDto.serialNumber);
        assertEquals(airplane.getAcceptedAdditionalWeight(), airplaneDto.acceptedAdditionalWeight, 0.01);
        assertEquals(airplane.isAirLourd(), airplaneDto.isAirLourd);
    }
}
