package ca.ulaval.glo4003.air.domain.user;

import ca.ulaval.glo4003.air.api.user.dto.UserDto;
import ca.ulaval.glo4003.air.api.user.dto.UserUpdateDto;
import ca.ulaval.glo4003.air.transfer.user.UserAssembler;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.naming.AuthenticationException;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {
    private final static String EMAIL = "test@test.com";
    private final static String ANOTHER_EMAIL = "test_patate@test.com";
    private final static String PASSWORD = "ABC";

    private final static String A_TOKEN = "rock.darius.mama.rucker";

    @Mock
    private UserRepository userRepository;
    @Mock
    private TokenDecoder tokenDecoder;
    @Mock
    private UserFactory userFactory;

    @Mock
    private UserAssembler userAssembler;

    @Mock
    private User user;

    @Mock
    private UserDto userDto;

    private UserService userService;

    @Before
    public void setup() {
        userService = new UserService(userRepository, userAssembler, userFactory, tokenDecoder);
    }

    @Test
    public void givenPersistedUser_whenAuthenticatingUser_thenReturnDto() throws AuthenticationException {
        given(user.isPasswordValid(anyString())).willReturn(true);
        given(userRepository.findUserByEmail(EMAIL)).willReturn(Optional.of(user));
        given(userAssembler.create(user)).willReturn(userDto);

        UserDto user = userService.authenticateUser(EMAIL, "");

        assertNotNull(user);
        verify(this.user).generateToken();
    }


    @Test(expected = AuthenticationException.class)
    public void givenPersistedUser_whenAuthenticatingUserWithInvalidPassword_thenThrow() throws AuthenticationException {
        given(user.isPasswordValid(anyString())).willReturn(false);
        given(userRepository.findUserByEmail(EMAIL)).willReturn(Optional.of(user));

        userService.authenticateUser(EMAIL, "");

    }

    @Test(expected = AuthenticationException.class)
    public void givenPersistedUser_whenAuthenticatingUserWithInvalidInvalidEmail_thenThrow() throws AuthenticationException {
        given(user.isPasswordValid(anyString())).willReturn(false);
        given(userRepository.findUserByEmail("")).willReturn(Optional.empty());

        userService.authenticateUser("", "");
    }

    @Test
    public void givenPersistedUser_whenCreatingUserWithAnotherEmail_thenUserIsCreated() throws UserAlreadyExistException {
        given(userFactory.createUser(ANOTHER_EMAIL, PASSWORD)).willReturn(user);

        userService.createUser(ANOTHER_EMAIL, PASSWORD);

        verify(userRepository).persist(user);
    }

    @Test(expected = UserAlreadyExistException.class)
    public void givenPersistedUser_whenCreatingUserWithSameEmail_thenThrow() throws UserAlreadyExistException {
        given(userFactory.createUser(anyString(), anyString())).willReturn(user);
        doThrow(UserAlreadyExistException.class).when(userRepository).persist(any(User.class));

        userService.createUser(ANOTHER_EMAIL, PASSWORD);
    }

    @Test
    public void givenAValidToken_whenUpdatingAuthenticatedUser_thenFindUserWithDecodedToken() throws InvalidTokenException {
        given(tokenDecoder.decode(A_TOKEN)).willReturn(EMAIL);
        given(userRepository.findUserByEmail(EMAIL)).willReturn(Optional.of(user));

        userService.updateAuthenticatedUser(A_TOKEN, new UserUpdateDto());

        verify(userRepository).findUserByEmail(EMAIL);
    }

    @Test(expected = InvalidTokenException.class)
    public void givenAnInvalidToken_whenUpdatingAuthenticatedUser_shouldThrowException() throws InvalidTokenException {
        given(tokenDecoder.decode(A_TOKEN)).willThrow(new InvalidTokenException());

        userService.updateAuthenticatedUser(A_TOKEN, new UserUpdateDto());
    }

    @Test(expected = InvalidTokenException.class)
    public void givenATokenWithInvalidEmail_whenUpdatingAuthenticatedUser_shouldThrowException() throws InvalidTokenException {
        given(tokenDecoder.decode(A_TOKEN)).willReturn(EMAIL);
        given(userRepository.findUserByEmail(EMAIL)).willReturn(Optional.empty());

        userService.updateAuthenticatedUser(A_TOKEN, new UserUpdateDto());
    }

    @Test
    public void givenAUser_whenUpdatingAuthenticatedUserWithFalseShowingFilteredAlert_shouldStopShowingFilteredAlert() throws InvalidTokenException {
        given(tokenDecoder.decode(A_TOKEN)).willReturn(EMAIL);
        given(userRepository.findUserByEmail(EMAIL)).willReturn(Optional.of(user));
        final UserUpdateDto userUpdateDto = new UserUpdateDto();
        userUpdateDto.showWeightFilteredAlert = false;

        userService.updateAuthenticatedUser(A_TOKEN, userUpdateDto);

        verify(user).stopShowingFilteredAlert();
    }

    @Test
    public void givenAUser_whenUpdatingAuthenticatedUser_shouldUpdateTheUser() throws InvalidTokenException {
        given(tokenDecoder.decode(A_TOKEN)).willReturn(EMAIL);
        given(userRepository.findUserByEmail(EMAIL)).willReturn(Optional.of(user));

        userService.updateAuthenticatedUser(A_TOKEN, new UserUpdateDto());

        verify(userRepository).update(user);
    }

    @Test
    public void givenAUser_whenUpdatingAuthenticatedUser_shouldReturnDto() throws InvalidTokenException {
        given(tokenDecoder.decode(A_TOKEN)).willReturn(EMAIL);
        given(userRepository.findUserByEmail(EMAIL)).willReturn(Optional.of(user));
        given(userAssembler.create(user)).willReturn(userDto);

        UserDto result = userService.updateAuthenticatedUser(A_TOKEN, new UserUpdateDto());

        assertEquals(result, userDto);
    }
}