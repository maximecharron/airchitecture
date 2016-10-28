package ca.ulaval.glo4003.air.api.user;

import ca.ulaval.glo4003.air.api.user.dto.UserDto;
import ca.ulaval.glo4003.air.domain.user.User;
import ca.ulaval.glo4003.air.domain.user.UserAlreadyExistException;
import ca.ulaval.glo4003.air.domain.user.UserService;
import ca.ulaval.glo4003.air.transfer.user.UserAssembler;

import javax.naming.AuthenticationException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.logging.Logger;

@Path("/auth")
public class AuthenticationResource {
    private final Logger logger = Logger.getLogger(AuthenticationResource.class.getName());

    private UserService userService;
    private UserAssembler userAssembler;

    public AuthenticationResource(UserService userService, UserAssembler userAssembler) {
        this.userService = userService;
        this.userAssembler = userAssembler;
    }

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public UserDto login(@FormParam("email") String email, @FormParam("password") String password) {
        try {
            User user = userService.authenticateUser(email, password);
            return userAssembler.create(user);
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
