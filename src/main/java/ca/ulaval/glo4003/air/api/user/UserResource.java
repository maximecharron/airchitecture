package ca.ulaval.glo4003.air.api.user;

import ca.ulaval.glo4003.air.api.user.dto.UserSearchPreferencesDto;
import ca.ulaval.glo4003.air.api.user.dto.UserDto;
import ca.ulaval.glo4003.air.api.user.dto.UserSettingsDto;
import ca.ulaval.glo4003.air.domain.user.InvalidTokenException;
import ca.ulaval.glo4003.air.service.user.UserService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.util.logging.Logger;

@Path("/users")
public class UserResource {

    private final Logger logger = Logger.getLogger(UserResource.class.getName());

    private UserService userService;

    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @PUT
    @Path("/me")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public UserDto update(UserSettingsDto userSettingsDto, @HeaderParam("X-Access-Token") String token) {
        try {
            return this.userService.updateAuthenticatedUser(token, userSettingsDto);
        } catch (InvalidTokenException e) {
            logger.info("Update user failed because: " + e.getMessage());
            throw new WebApplicationException(Response.status(Status.UNAUTHORIZED)
                                                      .entity("Token is invalid.")
                                                      .build());
        }
    }

    @GET
    @Path("/me/searchPreferences")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public UserSearchPreferencesDto getSearchPreferences(@HeaderParam("X-Access-Token") String token) {
        try {
            return this.userService.getUserSearchPreferences(token);
        } catch (InvalidTokenException e) {
            logger.info("Update user failed because: " + e.getMessage());
            throw new WebApplicationException(Response.status(Status.UNAUTHORIZED)
                    .entity("Token is invalid.")
                    .build());
        }
    }
}
