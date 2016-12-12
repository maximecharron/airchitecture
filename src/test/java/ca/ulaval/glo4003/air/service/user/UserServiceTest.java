package ca.ulaval.glo4003.air.service.user;

import ca.ulaval.glo4003.air.domain.user.*;
import ca.ulaval.glo4003.air.domain.user.encoding.TokenDecoder;
import ca.ulaval.glo4003.air.transfer.user.UserAssembler;
import ca.ulaval.glo4003.air.transfer.user.dto.UserDto;
import ca.ulaval.glo4003.air.transfer.user.dto.UserSettingsDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.naming.AuthenticationException;
import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    private final static String EMAIL = "test@test.com";
    private final static String ANOTHER_EMAIL = "test_patate@test.com";
    private final static String PASSWORD = "ABC";
    private final static boolean IS_NOT_ADMIN = false;
    private final static String A_TOKEN = "rock.darius.mama.rucker";
    private static final boolean HAS_SEARCHED_FOR_ECONOMY_CLASS = true;
    private static final boolean HAS_SEARCHED_FOR_REGULAR_CLASS = true;
    private static final boolean HAS_SEARCHED_FOR_BUSINESS_CLASS = true;
    private static final boolean HAS_SEARCHED_FOR_AIR_VIVANT = true;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TokenDecoder tokenDecoder;

    @Mock
    private UserFactory userFactory;

    @Mock
    private User user;

    private UserAssembler userAssembler;
    private UserService userService;

    @Before
    public void setup() {
        userAssembler = new UserAssembler();
        userService = new UserService(userRepository, userFactory, tokenDecoder, userAssembler);
    }

    @Test
    public void givenPersistedUser_whenAuthenticatingUser_thenATokenIsGenerated() throws AuthenticationException {
        given(user.isPasswordValid(anyString())).willReturn(true);
        given(userRepository.findUserByEmail(EMAIL)).willReturn(Optional.of(user));

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
        given(userFactory.createUser(ANOTHER_EMAIL, PASSWORD, IS_NOT_ADMIN)).willReturn(user);

        userService.createUser(ANOTHER_EMAIL, PASSWORD, IS_NOT_ADMIN);

        verify(userRepository).persist(user);
    }

    @Test(expected = UserAlreadyExistException.class)
    public void givenPersistedUser_whenCreatingUserWithSameEmail_thenThrow() throws UserAlreadyExistException {
        given(userFactory.createUser(anyString(), anyString(), anyBoolean())).willReturn(user);
        doThrow(UserAlreadyExistException.class).when(userRepository).persist(any(User.class));

        userService.createUser(ANOTHER_EMAIL, PASSWORD, IS_NOT_ADMIN);
    }

    @Test
    public void givenAValidToken_whenUpdatingAuthenticatedUser_thenFindUserWithDecodedToken() throws InvalidTokenException {
        given(tokenDecoder.decode(A_TOKEN)).willReturn(EMAIL);
        given(userRepository.findUserByEmail(EMAIL)).willReturn(Optional.of(user));

        userService.updateAuthenticatedUser(A_TOKEN, new UserSettingsDto());

        verify(userRepository).findUserByEmail(EMAIL);
    }

    @Test(expected = InvalidTokenException.class)
    public void givenAnInvalidToken_whenUpdatingAuthenticatedUser_thenShouldThrowException() throws InvalidTokenException {
        given(tokenDecoder.decode(A_TOKEN)).willThrow(new InvalidTokenException());

        userService.updateAuthenticatedUser(A_TOKEN, new UserSettingsDto());
    }

    @Test(expected = InvalidTokenException.class)
    public void givenATokenWithInvalidEmail_whenUpdatingAuthenticatedUser_thenShouldThrowException() throws InvalidTokenException {
        given(tokenDecoder.decode(A_TOKEN)).willReturn(EMAIL);
        given(userRepository.findUserByEmail(EMAIL)).willReturn(Optional.empty());

        userService.updateAuthenticatedUser(A_TOKEN, new UserSettingsDto());
    }

    @Test
    public void givenAUser_whenUpdatingAuthenticatedUserWithTrueHidingFilteredAlert_thenShouldStopShowingFilteredAlert() throws InvalidTokenException {
        givenAValidTokenAndUser();
        UserSettingsDto userPreferences = new UserSettingsDto();
        userPreferences.hideWeightFilteredAlert = true;

        userService.updateAuthenticatedUser(A_TOKEN, userPreferences);

        verify(user).stopShowingFilteredAlert();
    }

    @Test
    public void givenAUser_whenUpdatingAuthenticatedUser_thenShouldUpdateTheUser() throws InvalidTokenException {
        givenAValidTokenAndUser();

        userService.updateAuthenticatedUser(A_TOKEN, new UserSettingsDto());

        verify(userRepository).update(user);
    }

    @Test
    public void givenAValidTokenAndUser_whenIncrementingAuthenticatedUserSearchPreferences_thenIncrementOnTheRelatedUser() throws InvalidTokenException {
        givenAValidTokenAndUser();

        userService.incrementAuthenticatedUserSearchPreferences(A_TOKEN, HAS_SEARCHED_FOR_AIR_VIVANT, HAS_SEARCHED_FOR_ECONOMY_CLASS, HAS_SEARCHED_FOR_REGULAR_CLASS, HAS_SEARCHED_FOR_BUSINESS_CLASS);

        verify(user).incrementSearchPreferences(HAS_SEARCHED_FOR_AIR_VIVANT, HAS_SEARCHED_FOR_ECONOMY_CLASS, HAS_SEARCHED_FOR_REGULAR_CLASS, HAS_SEARCHED_FOR_BUSINESS_CLASS);
    }

    @Test
    public void givenAValidTokenAndUser_whenIncrementingAuthenticatedUserSearchPreferences_thenUpdatesTheUser() throws InvalidTokenException {
        givenAValidTokenAndUser();

        userService.incrementAuthenticatedUserSearchPreferences(A_TOKEN, HAS_SEARCHED_FOR_AIR_VIVANT, HAS_SEARCHED_FOR_ECONOMY_CLASS, HAS_SEARCHED_FOR_REGULAR_CLASS, HAS_SEARCHED_FOR_BUSINESS_CLASS);

        verify(userRepository).update(user);
    }

    private void givenAValidTokenAndUser() throws InvalidTokenException {
        given(tokenDecoder.decode(A_TOKEN)).willReturn(EMAIL);
        given(userRepository.findUserByEmail(EMAIL)).willReturn(Optional.of(user));
    }
}
