package ca.ulaval.glo4003.ws.api.user;

import ca.ulaval.glo4003.ws.api.user.dto.UserDto;
import ca.ulaval.glo4003.ws.domain.user.UserAlreadyExistException;
import ca.ulaval.glo4003.ws.domain.user.UserService;

import javax.naming.AuthenticationException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.util.logging.Logger;

public class UserResourceImpl implements UserResource {

    private Logger logger = Logger.getLogger(UserResourceImpl.class.getName());
    private UserService userService;

    public UserResourceImpl(UserService userService) {
        this.userService = userService;
    }

    public UserDto login(String email, String password) {
        try {
            return userService.authenticateUser(email, password);
        } catch (AuthenticationException e) {
            logger.info("Login failed for user with email "+ email);
            throw new WebApplicationException(Response.status(Response.Status.FORBIDDEN)
                                                      .entity("Authentication failed")
                                                      .build());
        }
    }

    public void signup(String email, String password) {
        try {
            userService.createUser(email, password);
        } catch (UserAlreadyExistException e){
            logger.info("Signup failed for user with email "+ email + " because it already exists");
            throw new WebApplicationException(Response.status(Response.Status.CONFLICT)
                                                      .entity("Signup failed")
                                                      .build());
        }
    }
}
