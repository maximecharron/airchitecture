package ca.ulaval.glo4003.ws.api.user;

import ca.ulaval.glo4003.ws.api.user.dto.UserDto;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/auth/")
public interface UserResource {

    @POST
    @Path("login/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    UserDto login(@FormParam("email") String email, @FormParam("password") String password);

    @POST
    @Path("signup/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    void signup(@FormParam("email") String email, @FormParam("password") String password);
}
