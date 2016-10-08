package ca.ulaval.glo4003.air.persistence.user;

import ca.ulaval.glo4003.air.domain.user.User;
import ca.ulaval.glo4003.air.domain.user.UserRepository;
import ca.ulaval.glo4003.air.domain.user.UserAlreadyExistException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Optional;

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
    private User aUser;

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

        Optional<User> user = userRepository.findUserByEmail(EMAIL);

        assertEquals(EMAIL, user.get().getEmail());
    }

    @Test
    public void givenPersistedUsers_whenFindingNonExistingUser_thenUserIsNull() {
        givenPersistedUsers();

        Optional<User> user = userRepository.findUserByEmail(NON_EXISTING_EMAIL);

        assertFalse(user.isPresent());
    }

    @Test
    public void givenPersistedUsers_whenCreateNonExistingUser_thenUserIsCreated() throws UserAlreadyExistException{
        givenPersistedUsers();
        given(aUser.getEmail()).willReturn(NON_EXISTING_EMAIL);

        userRepository.persist(aUser);

        Optional<User> user = userRepository.findUserByEmail(NON_EXISTING_EMAIL);
        assertTrue(user.isPresent());
    }

    @Test(expected = UserAlreadyExistException.class)
    public void givenPersistedUsers_whenCreateUserWithExistingEmail_thenThrows() throws UserAlreadyExistException{
        givenPersistedUsers();
        given(aUser.getEmail()).willReturn(EMAIL);

        userRepository.persist(aUser);
    }

    private void givenPersistedUsers() {

        userRepository.update(matchingUser);
        userRepository.update(notMatchingUser);
    }
}
