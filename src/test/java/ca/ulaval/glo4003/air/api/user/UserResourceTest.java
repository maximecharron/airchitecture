package ca.ulaval.glo4003.air.api.user;

import ca.ulaval.glo4003.air.api.user.dto.UserDto;
import ca.ulaval.glo4003.air.api.user.dto.UserUpdateDto;
import ca.ulaval.glo4003.air.domain.user.InvalidTokenException;
import ca.ulaval.glo4003.air.domain.user.UserService;
import org.eclipse.jetty.http.HttpStatus;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.ws.rs.WebApplicationException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class UserResourceTest {
    private static final UserUpdateDto USER_UPDATE_DTO = new UserUpdateDto();
    private static final String TOKEN = "foxpidesfoisfalco";

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Mock
    private UserService userService;
    @Mock
    private UserDto userDtoMock;

    private UserResource userResource;

    @Before
    public void setup() {
        userResource = new UserResource(userService);
    }

    @Test
    public void givenAValidToken_whenUpdate_thenItsDelegatedToTheService() throws InvalidTokenException {
        given(userService.updateAuthenticatedUser(TOKEN, USER_UPDATE_DTO)).willReturn(userDtoMock);

        UserDto result = userResource.update(USER_UPDATE_DTO, TOKEN);

        assertEquals(result, userDtoMock);
    }

    @Test
    public void givenAnInvalidToken_whenUpdate_thenItsDelegatedToTheService() throws InvalidTokenException {
        given(userService.updateAuthenticatedUser(TOKEN, USER_UPDATE_DTO)).willThrow(new InvalidTokenException());

        UserDto result = null;
        try {
            result = userResource.update(USER_UPDATE_DTO, TOKEN);
            fail("Exception not thrown");
        }
        catch (WebApplicationException e) {
            assertThat(e.getResponse().getStatus(), is(equalTo(HttpStatus.UNAUTHORIZED_401)));
        }
    }
}