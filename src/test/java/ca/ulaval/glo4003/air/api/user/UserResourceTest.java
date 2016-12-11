package ca.ulaval.glo4003.air.api.user;

import ca.ulaval.glo4003.air.domain.user.InvalidTokenException;
import ca.ulaval.glo4003.air.service.user.UserService;
import ca.ulaval.glo4003.air.transfer.user.dto.UserDto;
import ca.ulaval.glo4003.air.transfer.user.dto.UserSearchPreferencesDto;
import ca.ulaval.glo4003.air.transfer.user.dto.UserSettingsDto;
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

    private static final String TOKEN = "foxpidesfoisfalco";

    @Mock
    private UserService userService;

    @Mock
    private UserSettingsDto userPreferences;

    @Mock
    private UserDto user;

    @Mock
    private UserSearchPreferencesDto searchPreferencesDto;

    private UserResource userResource;

    @Before
    public void setup() {
        userResource = new UserResource(userService);
    }

    @Test
    public void givenAValidToken_whenUpdate_thenItsDelegatedToTheService() throws InvalidTokenException {
        given(userService.updateAuthenticatedUser(TOKEN, userPreferences)).willReturn(user);

        UserDto result = userResource.update(userPreferences, TOKEN);

        assertEquals(result, user);
    }

    @Test
    public void givenAnInvalidToken_whenUpdate_thenItsDelegatedToTheService() throws InvalidTokenException {
        given(userService.updateAuthenticatedUser(TOKEN, userPreferences)).willThrow(new InvalidTokenException());

        UserDto result = null;
        try {
            userResource.update(userPreferences, TOKEN);
            fail("Exception not thrown");
        } catch (WebApplicationException e) {
            assertThat(e.getResponse().getStatus(), is(equalTo(HttpStatus.UNAUTHORIZED_401)));
        }
    }

    @Test
    public void givenAValidToken_whenGettingSearchPreferences_thenItsDelegatedToTheService() throws InvalidTokenException {
        given(userService.getUserSearchPreferences(TOKEN)).willReturn(searchPreferencesDto);

        UserSearchPreferencesDto result = userResource.getSearchPreferences(TOKEN);

        assertEquals(result, searchPreferencesDto);
    }

    @Test
    public void givenAnInvalidToken_whenGettingSearchPreferences_thenItsDelegatedToTheService() throws InvalidTokenException {
        given(userService.getUserSearchPreferences(TOKEN)).willThrow(new InvalidTokenException());

        UserSearchPreferencesDto result = null;
        try {
            userResource.getSearchPreferences(TOKEN);
            fail("Exception not thrown");
        } catch (WebApplicationException e) {
            assertThat(e.getResponse().getStatus(), is(equalTo(HttpStatus.UNAUTHORIZED_401)));
        }
    }
}