package ca.ulaval.glo4003.ws.api.user;

import ca.ulaval.glo4003.ws.domain.user.UserAlreadyExistException;
import ca.ulaval.glo4003.ws.domain.user.UserService;

import javax.naming.AuthenticationException;
import javax.ws.rs.core.Response;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserResourceImpl implements UserResource {

    private Logger logger = Logger.getLogger(UserResourceImpl.class.getName());
    private UserService userService;

    public UserResourceImpl(UserService userService) {
        this.userService = userService;
    }

    public Response login(String email, String password) {
        try {
            userService.authenticateUser(email, password);
            return Response.ok().build();
        } catch (AuthenticationException e) {
            return Response.status(401).build();
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
