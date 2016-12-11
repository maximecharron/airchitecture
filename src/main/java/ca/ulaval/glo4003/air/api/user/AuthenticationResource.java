package ca.ulaval.glo4003.air.api.user;

import ca.ulaval.glo4003.air.transfer.user.dto.UserDto;
import ca.ulaval.glo4003.air.domain.user.UserAlreadyExistException;
import ca.ulaval.glo4003.air.service.user.UserService;

import javax.naming.AuthenticationException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.logging.Logger;

@Path("/auth")
public class AuthenticationResource {

    private final Logger logger = Logger.getLogger(AuthenticationResource.class.getName());

    private UserService userService;

    public AuthenticationResource(UserService userService) {
        this.userService = userService;
    }

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public UserDto login(@FormParam("email") String email, @FormParam("password") String password) {
        try {
            return userService.authenticateUser(email, password);
        } catch (AuthenticationException e) {
            logger.info("Login failed for user with email " + email);
            throw new WebApplicationException(Response.status(Response.Status.FORBIDDEN)
                                                      .entity("Authentication failed")
                                                      .build());
        }
    }


    @POST
    @Path("/signup")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public void signup(@FormParam("email") String email, @FormParam("password") String password) {
        try {
            userService.createUser(email, password, false);
        } catch (UserAlreadyExistException e) {
            logger.info("Signup failed for user with email " + email + " because it already exists");
            throw new WebApplicationException(Response.status(Response.Status.CONFLICT)
                                                      .entity("Signup failed")
                                                      .build());
        }
    }
}
