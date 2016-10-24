package ca.ulaval.glo4003.air.domain.user;

import ca.ulaval.glo4003.air.api.user.dto.UserDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class UserAssemblerTest {

    private static final String EMAIL = "test@test.com";
    private static final String PASSWORD = "DEF";
    private static final String A_TOKEN = "aToken";
    @Mock
    private User user;
    private UserAssembler userAssembler;

    @Before
    public void setup() {
        userAssembler = new UserAssembler();
    }

    @Test
    public void givenAUser_whenCreatingAUserDto_thenItHasAllTheRelevantProperties() {
        givenAUser();

        UserDto userDto = userAssembler.create(user);

        assertHasAllTheRelevantProperties(userDto);
    }

    private void givenAUser() {
        given(user.getEmailAddress()).willReturn(EMAIL);
        given(user.getPassword()).willReturn(PASSWORD);
        given(user.getToken()).willReturn(A_TOKEN);
    }


    private void assertHasAllTheRelevantProperties(UserDto userDto) {
        assertEquals(EMAIL, userDto.emailAddress);
        assertEquals(A_TOKEN, userDto.token);
    }
}