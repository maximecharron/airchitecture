package ca.ulaval.glo4003.ws.api.user;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/auth/")
public interface UserResource {

    @POST
    @Path("login/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    Response login(@FormParam("email") String email, @FormParam("password") String password);

    @POST
    @Path("signup/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    Response signup(@FormParam("email") String email, @FormParam("password") String password);
}
