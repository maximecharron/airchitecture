package ca.ulaval.glo4003.air.api.user;

import ca.ulaval.glo4003.air.domain.user.UserAlreadyExistException;
import ca.ulaval.glo4003.air.service.user.UserService;
import ca.ulaval.glo4003.air.transfer.user.dto.UserDto;
import org.eclipse.jetty.http.HttpStatus;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.naming.AuthenticationException;
import javax.ws.rs.WebApplicationException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class AuthenticationResourceTest {

    private static final String EMAIL = "test@test.com";
    private static final String PASSWORD = "DUB";
    private static final String BAD_PASSWORD = "";

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Mock
    private UserService userService;

    @Mock
    private UserDto user;

    private AuthenticationResource authenticationResource;

    @Before
    public void setup() {
        authenticationResource = new AuthenticationResource(userService);
    }

    @Test
    public void givenAnAuthenticationResource_whenLogin_thenItsDelegatedToTheService() throws AuthenticationException {
        given(userService.authenticateUser(EMAIL, PASSWORD)).willReturn(user);

        authenticationResource.login(EMAIL, PASSWORD);

        verify(userService).authenticateUser(EMAIL, PASSWORD);
    }

    @Test
    public void givenABadPassword_whenLogin_then403IsThrown() throws AuthenticationException {
        willThrow(AuthenticationException.class).given(userService).authenticateUser(EMAIL, BAD_PASSWORD);

        try {
            authenticationResource.login(EMAIL, BAD_PASSWORD);
            fail("Exception not thrown");
        } catch (WebApplicationException e) {
            assertThat(e.getResponse().getStatus(), is(equalTo(HttpStatus.FORBIDDEN_403)));
        }
    }

    @Test
    public void givenAnAuthenticationResource_whenSigningUp_thenItsDelegatedToTheService() throws AuthenticationException, UserAlreadyExistException {

        authenticationResource.signup(EMAIL, PASSWORD);

        verify(userService).createUser(EMAIL, PASSWORD, false);
    }

    @Test
    public void givenAlreadyExistingEmail_whenSigningUp_then409IsThrown() throws AuthenticationException, UserAlreadyExistException {
        doThrow(UserAlreadyExistException.class).when(userService).createUser(anyString(), anyString(), anyBoolean());
        try {
            authenticationResource.signup(EMAIL, PASSWORD);
            fail("Exception not thrown");
        } catch (WebApplicationException e) {
            assertThat(e.getResponse().getStatus(), is(equalTo(HttpStatus.CONFLICT_409)));
        }
    }
}