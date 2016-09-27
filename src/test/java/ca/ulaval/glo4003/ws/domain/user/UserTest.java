package ca.ulaval.glo4003.ws.domain.user;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserTest {
    private static final String EMAIL = "test@test.com";
    private static final String PASSWORD = "DEF";
    private static final String WRONG_PASSWORD = "ABC";
    private User user;

    @Before
    public void setup() {
        user = new User(EMAIL, PASSWORD);
    }

    @Test
    public void whenConstructingNewUser_thenPasswordIsHash() {

        User newUser = new User(EMAIL, PASSWORD);

        assertNotNull(newUser.getPassword());
        assertNotEquals(PASSWORD, newUser.getPassword());
    }

    @Test
    public void givenAUser_whenValidatePasswordWithRightPassword_thenPassowrdIsValid(){

        boolean validPassword = user.isPasswordValid(PASSWORD);

        assertTrue(validPassword);
    }

    @Test
    public void givenAUser_whenValidatePasswordWithWrongPassword_thenPassowrdIsValid(){

        boolean validPassword = user.isPasswordValid(WRONG_PASSWORD);

        assertFalse(validPassword);
    }


    @Test
    public void givenAUser_whenGeneratingToken_thenTokenIsGenerated() {

        user.generateToken();

        assertNotNull(user.getToken());
    }

    @Test
    public void givenTwoUserWithSamePassword_thenPasswordHashIsNotEqual() {

        User newUser = new User(EMAIL, PASSWORD);

        assertNotEquals(user.getPassword(), newUser.getPassword());
    }

}
