package ca.ulaval.glo4003.air.service.airplane;

import ca.ulaval.glo4003.air.api.airplane.dto.AirplaneDto;
import ca.ulaval.glo4003.air.api.airplane.dto.AirplaneSearchResultDto;
import ca.ulaval.glo4003.air.api.airplane.dto.AirplaneUpdateDto;
import ca.ulaval.glo4003.air.domain.airplane.*;
import ca.ulaval.glo4003.air.domain.user.InvalidTokenException;
import ca.ulaval.glo4003.air.domain.user.UnauthorizedException;
import ca.ulaval.glo4003.air.domain.user.User;
import ca.ulaval.glo4003.air.service.user.UserService;
import ca.ulaval.glo4003.air.transfer.airplane.AirplaneAssembler;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class AirplaneServiceTest {

    private static final boolean NEEDS_TO_BE_AIR_LOURD = true;
    private static final boolean DOES_NOT_NEED_TO_BE_AIR_LOURD = false;
    private static final String AN_INVALID_TOKEN = "white christmas";
    private static final String A_SERIAL_NUMBER = "is that you santa claus";
    private static final String A_TOKEN = "tristram - diablo II";
    private static final String AN_INVALID_SERIAL_NUMBER = "feliz navidad";
    private static final double AN_ACCEPTED_MAXIMUM_WEIGHT = 2;
    @Mock
    private AirplaneRepository airplaneRepository;

    @Mock
    private Airplane airplane;

    @Mock
    private AirLourdAirplane airLourdAirplane;

    @Mock
    private List<Airplane> airplanes;

    @Mock
    private List<Airplane> airplanesFilteredByWeight;

    @Mock
    private AirplaneAssembler airplaneAssembler;

    @Mock
    private AirplaneSearchResultDto airplaneSearchResultDto;

    @Mock
    private AirplaneDto airplaneDto;

    @Mock
    private UserService userService;

    @Mock
    private User user;

    private AirplaneUpdateDto airplaneUpdateDTO;

    private AirplaneService airplaneService;

    @Before
    public void setup() throws Exception {
        given(user.isAdmin()).willReturn(true);
        given(userService.authenticateUser(A_TOKEN)).willReturn(user);
        given(airplaneRepository.find(A_SERIAL_NUMBER)).willReturn(Optional.of(airplane));
        given(airplaneRepository.findAll()).willReturn(airplanes);
        given(airLourdAirplane.isAirLourd()).willReturn(true);

        airplaneUpdateDTO = new AirplaneUpdateDto();
        airplaneUpdateDTO.acceptedMaximumWeight = AN_ACCEPTED_MAXIMUM_WEIGHT;
        airplaneService = new AirplaneService(airplaneRepository, airplaneAssembler, userService);
    }

    @Test
    public void givenSearchFiltersWithNeedsToBeAirLourd_whenFindingAllMatchingAirplanes_thenTheRepositoryFindsCorrespondingAirplanes() {
        airplaneService.findAllWithFilters(NEEDS_TO_BE_AIR_LOURD);

        verify(airplaneRepository).findAllAirLourd();
    }

    @Test
    public void givenSearchFiltersWithNoFilters_whenFindingAllMatchingAirplanes_thenTheRepositoryFindsAllAirplanes() {
        airplaneService.findAllWithFilters(DOES_NOT_NEED_TO_BE_AIR_LOURD);

        verify(airplaneRepository).findAll();
    }

    @Test
    public void givenPersistedAirplanes_whenFindingAllAirplanesWithFilters_thenReturnAirplaneSearchResult() {
        given(airplaneAssembler.createAirplaneSearchResult(any(AirplaneSearchResult.class))).willReturn(airplaneSearchResultDto);

        AirplaneSearchResultDto result = airplaneService.findAllWithFilters(NEEDS_TO_BE_AIR_LOURD);

        assertEquals(result, airplaneSearchResultDto);
    }

    @Test(expected = InvalidTokenException.class)
    public void givenAnInvalidToken_whenUpdatingAnAirplane_thenThrowsInvalidTokenException() throws Exception {
        given(userService.authenticateUser(AN_INVALID_TOKEN)).willThrow(InvalidTokenException.class);

        airplaneService.updateAirplane(AN_INVALID_TOKEN, A_SERIAL_NUMBER, airplaneUpdateDTO);
    }

    @Test(expected = UnauthorizedException.class)
    public void givenANonAdminUser_whenUpdatingAnAirplane_thenThrowsUnauthorizedException() throws Exception {
        given(user.isAdmin()).willReturn(false);
        given(userService.authenticateUser(A_TOKEN)).willReturn(user);

        airplaneService.updateAirplane(A_TOKEN, A_SERIAL_NUMBER, airplaneUpdateDTO);
    }

    @Test(expected = AirplaneNotFoundException.class)
    public void givenANonExistentAirplane_whenUpdatingAnAirplane_thenThrowsAirplaneNotFoundException() throws Exception {
        given(airplaneRepository.find(AN_INVALID_SERIAL_NUMBER)).willReturn(Optional.empty());

        airplaneService.updateAirplane(A_TOKEN, AN_INVALID_SERIAL_NUMBER, airplaneUpdateDTO);
    }

    @Test
    public void givenAnAirLourdAirplane_whenUpdatingAnAirplane_thenUpdateTheAcceptedAdditionalWeight() throws Exception {
        given(airplaneRepository.find(A_SERIAL_NUMBER)).willReturn(Optional.of(airLourdAirplane));

        airplaneService.updateAirplane(A_TOKEN, A_SERIAL_NUMBER, airplaneUpdateDTO);

        verify(airLourdAirplane).setAcceptedAdditionalWeight(airplaneUpdateDTO.acceptedMaximumWeight);
    }

    public void whenUpdatingAnAirplane_thenReturnTheAirplaneDto() throws Exception {
        given(airplaneAssembler.createAirplane(airplane)).willReturn(airplaneDto);

        final AirplaneDto updateResult = airplaneService.updateAirplane(A_TOKEN, A_SERIAL_NUMBER, airplaneUpdateDTO);

        assertEquals(airplaneDto, updateResult);
    }
}