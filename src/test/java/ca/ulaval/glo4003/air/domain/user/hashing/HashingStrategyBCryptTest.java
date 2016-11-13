package ca.ulaval.glo4003.air.domain.user.hashing;

import ca.ulaval.glo4003.air.infrastructure.user.hashing.HashingStrategyBCrypt;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertNotEquals;

public class HashingStrategyBCryptTest {

    private HashingStrategyBCrypt hashingStrategy;
    private static final String A_PASSWORD = "123456";
    private static final String ANOTHER_PASSWORD = "abc123";

    @Before
    public void setup() {
        hashingStrategy = new HashingStrategyBCrypt();
    }

    @Test
    public void givenAPassword_whenHashingPassword_thenPasswordIsHashed() {

        String hashedPassword = hashingStrategy.hashPassword(A_PASSWORD);

        assertNotEquals(A_PASSWORD, hashedPassword);
    }

    @Test
    public void givenTheSamePasswords_whenHashingPasswords_thenHashIsDifferent() {

        String hashedPassword = hashingStrategy.hashPassword(A_PASSWORD);
        String hashedPassword2 = hashingStrategy.hashPassword(A_PASSWORD);

        assertNotEquals(hashedPassword2, hashedPassword);
    }

    @Test
    public void givenAHashedPasswordAndAValidPassword_whenValidatingPassword_thenPasswordIsValid() {
        String hashedPassword = hashingStrategy.hashPassword(A_PASSWORD);

        boolean isValid = hashingStrategy.validatePassword(hashedPassword, A_PASSWORD);

        assertTrue(isValid);
    }

    @Test
    public void givenAHashedPasswordAndAnInvalidPassword_whenValidatingPassword_thenPasswordIsInvalid() {
        String hashedPassword = hashingStrategy.hashPassword(A_PASSWORD);

        boolean isValid = hashingStrategy.validatePassword(hashedPassword, ANOTHER_PASSWORD);

        assertFalse(isValid);
    }
}
