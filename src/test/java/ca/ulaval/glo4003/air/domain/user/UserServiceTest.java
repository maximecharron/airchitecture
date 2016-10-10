package ca.ulaval.glo4003.air.domain.user;

import ca.ulaval.glo4003.air.api.user.dto.UserDto;
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


    private final String EMAIL = "test@test.com";
    private final String ANOTHER_EMAIL = "test_patate@test.com";
    private final String PASSWORD = "ABC";
    @Mock
    private UserRepository userRepository;

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
        userService = new UserService(userRepository, userAssembler, userFactory);
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
}