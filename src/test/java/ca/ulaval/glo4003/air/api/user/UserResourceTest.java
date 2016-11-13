package ca.ulaval.glo4003.air.api.user;

import ca.ulaval.glo4003.air.api.user.dto.UserDto;
import ca.ulaval.glo4003.air.api.user.dto.UserPreferencesDto;
import ca.ulaval.glo4003.air.domain.user.InvalidTokenException;
import ca.ulaval.glo4003.air.domain.user.User;
import ca.ulaval.glo4003.air.domain.user.UserPreferences;
import ca.ulaval.glo4003.air.domain.user.UserService;
import ca.ulaval.glo4003.air.transfer.user.UserAssembler;
import org.eclipse.jetty.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.ws.rs.WebApplicationException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class UserResourceTest {

    private static final UserPreferencesDto USER_PREFERENCES_DTO = new UserPreferencesDto();
    private static final UserDto USER_DTO = new UserDto();
    private static final String TOKEN = "foxpidesfoisfalco";

    @Mock
    private UserService userService;

    @Mock
    private UserAssembler userAssembler;

    @Mock
    private UserPreferences userPreferences;

    @Mock
    private User user;

    private UserResource userResource;

    @Before
    public void setup() {
        userResource = new UserResource(userService, userAssembler);
        given(userAssembler.createUserPreferences(USER_PREFERENCES_DTO)).willReturn(userPreferences);
    }

    @Test
    public void givenAValidToken_whenUpdate_thenItsDelegatedToTheService() throws InvalidTokenException {
        given(userService.updateAuthenticatedUser(TOKEN, userPreferences)).willReturn(user);
        given(userAssembler.create(user)).willReturn(USER_DTO);

        UserDto result = userResource.update(USER_PREFERENCES_DTO, TOKEN);

        assertEquals(result, USER_DTO);
    }

    @Test
    public void givenAnInvalidToken_whenUpdate_thenItsDelegatedToTheService() throws InvalidTokenException {
        given(userService.updateAuthenticatedUser(TOKEN, userPreferences)).willThrow(new InvalidTokenException());

        UserDto result = null;
        try {
            userResource.update(USER_PREFERENCES_DTO, TOKEN);
            fail("Exception not thrown");
        } catch (WebApplicationException e) {
            assertThat(e.getResponse().getStatus(), is(equalTo(HttpStatus.UNAUTHORIZED_401)));
        }
    }
}