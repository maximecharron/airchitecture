package ca.ulaval.glo4003.ws.api.user;

import ca.ulaval.glo4003.ws.api.user.dto.UserDto;
import ca.ulaval.glo4003.ws.domain.user.exception.UserAlreadyExistException;
import ca.ulaval.glo4003.ws.domain.user.UserService;

import javax.naming.AuthenticationException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.util.logging.Level;
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
            throw new WebApplicationException(Response.status(Response.Status.FORBIDDEN)
                                                      .entity("Authentication failed")
                                                      .build());
        }
    }

    public Response signup(String email, String password) {
        try {
            userService.createUser(email, password);
            return Response.ok().build();
        } catch (UserAlreadyExistException e){
            logger.log(Level.INFO, "Unable to create user with email " + email + " because it already exists");
            return Response.status(409).build();
        }
    }
}
