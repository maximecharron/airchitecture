package ca.ulaval.glo4003.air.infrastructure.airplane;

import ca.ulaval.glo4003.air.domain.airplane.Airplane;
import ca.ulaval.glo4003.air.domain.airplane.AirplaneRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class AirplaneRepositoryInMemoryTest {

    private static final String A_SERIAL_NUMBER = "DariusRucker666";
    private static final String ANOTHER_SERIAL_NUMBER = "DariusRucker667";

    @Mock
    private Airplane matchingAirplane;

    @Mock
    private Airplane notMatchingAirplane;

    private List<Airplane> airplanes = new ArrayList<>();

    private AirplaneRepository airplaneRepository;

    @Before
    public void setup() {
        airplaneRepository = new AirplaneRepositoryInMemory();

        given(matchingAirplane.getSerialNumber()).willReturn(A_SERIAL_NUMBER);
        given(notMatchingAirplane.getSerialNumber()).willReturn(ANOTHER_SERIAL_NUMBER);
        airplanes.add(matchingAirplane);
        airplanes.add(notMatchingAirplane);
        givenPersistedAirplanes();
    }

    @Test
    public void givenPersistedAirplanes_whenFindingAllAirplanes_thenAllAirplanesAreReturned() {
        List<Airplane> allAirplanes = airplaneRepository.findAll();

        assertEquals(allAirplanes.size(), airplanes.size());
    }

    @Test
    public void givenPersistedAirplanes_whenFindingAnAirplaneBySerialNumber_thenTheMatchingAirplaneIsReturned() {
        Optional<Airplane> airplane = airplaneRepository.find(A_SERIAL_NUMBER);

        assertEquals(matchingAirplane, airplane.get());
    }

    @Test
    public void givenPersistedAirplanes_whenFindingAllAirLourdAirplanes_thenTheMatchingAirplanesAreReturned() {
        List<Airplane> airLourdAirplanes = airplaneRepository.findAirLourdAirplanes();

        assertTrue(airLourdAirplanes.stream().allMatch(Airplane::isAirLourd));
    }

    private void givenPersistedAirplanes() {
        given(matchingAirplane.hasSerialNumber(A_SERIAL_NUMBER)).willReturn(true);
        given(matchingAirplane.isAirLourd()).willReturn(true);

        given(notMatchingAirplane.hasSerialNumber(A_SERIAL_NUMBER)).willReturn(false);
        given(notMatchingAirplane.isAirLourd()).willReturn(false);

        airplaneRepository.save(matchingAirplane);
        airplaneRepository.save(notMatchingAirplane);
    }
}