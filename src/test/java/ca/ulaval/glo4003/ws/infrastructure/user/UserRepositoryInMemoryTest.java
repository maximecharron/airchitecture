package ca.ulaval.glo4003.ws.infrastructure.user;

import ca.ulaval.glo4003.ws.domain.user.User;
import ca.ulaval.glo4003.ws.domain.user.UserRepository;
import ca.ulaval.glo4003.ws.domain.user.exception.UserAlreadyExistException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;

import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class UserRepositoryInMemoryTest {

    private static final String EMAIL = "bob@test.com";
    private static final String ANOTHER_EMAIL = "jeanne@test.com";
    private static final String NON_EXISTING_EMAIL = "jeanne23423423@test.com";

    @Mock
    private User matchingUser;

    @Mock
    private User notMatchingUser;

    private UserRepository userRepository;

    @Before
    public void setup() {
        userRepository = new UserRepositoryInMemory();
        given(matchingUser.getEmail()).willReturn(EMAIL);
        given(notMatchingUser.getEmail()).willReturn(ANOTHER_EMAIL);
    }

    @Test
    public void givenPersistedUsers_whenFindingExistingUser_thenMatchingUserIsReturned() {
        givenPersistedUsers();

        User user = userRepository.findUserByEmail(EMAIL);

        assertEquals(EMAIL, user.getEmail());
    }

    @Test
    public void givenPersistedUsers_whenFindingNonExistingUser_thenUserIsNull() {
        givenPersistedUsers();

        User user = userRepository.findUserByEmail(NON_EXISTING_EMAIL);

        assertNull(user);
    }

    @Test
    public void givenPersistedUsers_whenCreateNonExistingUser_thenUserIsCreated() {
        givenPersistedUsers();

        userRepository.create(new User(NON_EXISTING_EMAIL, ""));

        User user = userRepository.findUserByEmail(NON_EXISTING_EMAIL);
        assertNotNull(user);
    }

    @Test(expected = UserAlreadyExistException.class)
    public void givenPersistedUsers_whenCreateUserWithExistingEmail_thenThrows() {
        givenPersistedUsers();

        userRepository.create(new User(EMAIL, ""));
    }

    private void givenPersistedUsers() {

        userRepository.save(matchingUser);
        userRepository.save(notMatchingUser);
    }
}
