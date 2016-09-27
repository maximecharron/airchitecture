package ca.ulaval.glo4003.ws.domain.user;

import ca.ulaval.glo4003.ws.api.user.dto.UserDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.naming.AuthenticationException;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {


    private final String EMAIL = "test@test.com";
    private final String ANOTHER_EMAIL = "test_patate@test.com";
    private final String PASSWORD = "ABC";
    @Mock
    private UserRepository flightRepository;

    @Mock
    private UserAssembler flightAssembler;

    @Mock
    private User flight;

    @Mock
    private UserDto flightDto;

    private UserService flightService;

    @Before
    public void setup() {
        flightService = new UserService(flightRepository, flightAssembler);
    }

    @Test
    public void givenPersistedUser_whenAuthenticatingUser_thenReturnDto() throws AuthenticationException {
        given(flight.isPasswordValid(anyString())).willReturn(true);
        given(flightRepository.findUserByEmail(EMAIL)).willReturn(flight);
        given(flightAssembler.create(flight)).willReturn(flightDto);

        UserDto user = flightService.authenticateUser(EMAIL, "");

        assertNotNull(user);
        verify(flight).generateToken();
    }


    @Test(expected = AuthenticationException.class)
    public void givenPersistedUser_whenAuthenticatingUserWithInvalidPassword_thenThrow() throws AuthenticationException {
        given(flight.isPasswordValid(anyString())).willReturn(false);
        given(flightRepository.findUserByEmail(EMAIL)).willReturn(flight);

        flightService.authenticateUser(EMAIL, "");

    }

    @Test(expected = AuthenticationException.class)
    public void givenPersistedUser_whenAuthenticatingUserWithInvalidInvalidEmail_thenThrow() throws AuthenticationException {
        given(flight.isPasswordValid(anyString())).willReturn(false);
        given(flightRepository.findUserByEmail("")).willReturn(null);

        flightService.authenticateUser("", "");
    }

    @Test
    public void givenPersistedUser_whenCreatingUserWithAnotherEmail_thenUserIsCreated() throws UserAlreadyExistException {

        flightService.createUser(ANOTHER_EMAIL, PASSWORD);

        verify(flightRepository).create(any(User.class));
    }

    @Test(expected = UserAlreadyExistException.class)
    public void givenPersistedUser_whenCreatingUserWithSameEmail_thenThrow() throws UserAlreadyExistException {
        doThrow(UserAlreadyExistException.class).when(flightRepository).create(any(User.class));

        flightService.createUser(ANOTHER_EMAIL, PASSWORD);
    }
}