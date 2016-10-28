package ca.ulaval.glo4003.air.domain.user;

import ca.ulaval.glo4003.air.domain.user.encoding.TokenEncoder;
import ca.ulaval.glo4003.air.domain.user.hashing.HashingStrategy;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class UserTest {
    private static final String EMAIL = "test@test.com";
    private static final String PASSWORD = "DEF";
    private static final String A_TOKEN = "A_TOKEN";
    private static final String A_HASHED_PASSWORD = "asdn89023e4nads982";
    private static final boolean IS_NOT_ADMIN = false;
    private User user;

    @Mock
    private TokenEncoder tokenEncoder;

    @Mock
    private HashingStrategy hashingStrategy;

    @Before
    public void setup() {
        given(hashingStrategy.hashPassword(anyString())).willReturn(A_HASHED_PASSWORD);
        user = new User(EMAIL, PASSWORD, tokenEncoder, hashingStrategy, IS_NOT_ADMIN);
    }

    @Test
    public void whenConstructingNewUser_thenPasswordIsHash() {

        User newUser = new User(EMAIL, PASSWORD, tokenEncoder, hashingStrategy,IS_NOT_ADMIN);

        assertEquals(A_HASHED_PASSWORD, newUser.getPassword());
    }

    @Test
    public void givenAUser_whenValidatePasswordWithRightPassword_thenHashingStrategyIsCalled(){
        given(hashingStrategy.validatePassword(anyString(), anyString())).willReturn(true);

        boolean validPassword = user.isPasswordValid(PASSWORD);

        assertTrue(validPassword);
        verify(hashingStrategy).validatePassword(anyString(), anyString());
    }

    @Test
    public void givenAUser_whenGeneratingToken_thenTokenIsGenerated() {
        given(tokenEncoder.encode(anyString())).willReturn(A_TOKEN);

        user.generateToken();

        assertNotNull(user.getToken());
    }

    @Test
    public void givenAUser_whenStopShowingFilteredAlert_thenShowingFilteredAlertIsFalse() {
        user.stopShowingFilteredAlert();

        assertFalse(user.showsWeightFilteredAlert());
    }
}
