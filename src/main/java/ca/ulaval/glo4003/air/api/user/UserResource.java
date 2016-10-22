package ca.ulaval.glo4003.air.api.user;

import ca.ulaval.glo4003.air.api.user.dto.UserDto;
import ca.ulaval.glo4003.air.api.user.dto.UserUpdateDto;
import ca.ulaval.glo4003.air.domain.user.InvalidTokenException;
import ca.ulaval.glo4003.air.domain.user.UserService;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.util.logging.Logger;

@Path("/users")
public class UserResource {
    private Logger logger = Logger.getLogger(UserResource.class.getName());
    private UserService userService;
    @Context
    private HttpServletResponse response;

    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @PUT
    @Path("/me")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public UserDto update(UserUpdateDto userUpdateDto, @HeaderParam("X-Access-Token") String token) {
        response.setHeader("Access-Control-Allow-Headers", "X-Access-Token");
        try {
            return this.userService.updateAuthenticatedUser(token, userUpdateDto);
        }
        catch (InvalidTokenException e) {
            logger.info("Update user failed because: " + e.getMessage());
            throw new WebApplicationException(Response.status(Status.UNAUTHORIZED)
                    .entity("Token is invalid.")
                    .build());
        }
    }
}
