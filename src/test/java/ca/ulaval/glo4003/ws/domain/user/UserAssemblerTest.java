package ca.ulaval.glo4003.ws.domain.user;

import ca.ulaval.glo4003.ws.api.user.dto.UserDto;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class UserAssemblerTest {

    private static final String EMAIL = "test@test.com";
    private static final String PASSWORD = "DEF";

    private UserAssembler userAssembler;

    @Before
    public void setup() {
        userAssembler = new UserAssembler();
    }

    @Test
    public void givenAFlight_whenCreatingAFlightDto_thenItHasAllTheRelevantProperties() {
        User user = givenAFlight();

        UserDto userDto = userAssembler.create(user);

        assertHasAllTheRelevantProperties(user, userDto);
    }

    private User givenAFlight() {
        User user = new User(EMAIL, PASSWORD);
        user.generateToken();
        return user;
    }


    private void assertHasAllTheRelevantProperties(User user, UserDto userDto) {
        assertEquals(user.getEmail(), userDto.email);
        assertEquals(user.getToken(), userDto.token);
    }
}