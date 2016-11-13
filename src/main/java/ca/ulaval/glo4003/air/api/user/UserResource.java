package ca.ulaval.glo4003.air.api.user;

import ca.ulaval.glo4003.air.api.user.dto.UserDto;
import ca.ulaval.glo4003.air.api.user.dto.UserPreferencesDto;
import ca.ulaval.glo4003.air.domain.user.InvalidTokenException;
import ca.ulaval.glo4003.air.domain.user.User;
import ca.ulaval.glo4003.air.domain.user.UserPreferences;
import ca.ulaval.glo4003.air.domain.user.UserService;
import ca.ulaval.glo4003.air.transfer.user.UserAssembler;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.util.logging.Logger;

@Path("/users")
public class UserResource {

    private final Logger logger = Logger.getLogger(UserResource.class.getName());

    private UserService userService;
    private UserAssembler userAssembler;

    public UserResource(UserService userService, UserAssembler userAssembler) {
        this.userService = userService;
        this.userAssembler = userAssembler;
    }

    @PUT
    @Path("/me")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public UserDto update(UserPreferencesDto userPreferencesDto, @HeaderParam("X-Access-Token") String token) {
        try {
            UserPreferences userPreferences = userAssembler.createUserPreferences(userPreferencesDto);
            User user = this.userService.updateAuthenticatedUser(token, userPreferences);
            return userAssembler.create(user);
        } catch (InvalidTokenException e) {
            logger.info("Update user failed because: " + e.getMessage());
            throw new WebApplicationException(Response.status(Status.UNAUTHORIZED)
                                                      .entity("Token is invalid.")
                                                      .build());
        }
    }
}
