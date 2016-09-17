package ca.ulaval.glo4003.ws.api.contact;

import ca.ulaval.glo4003.ws.api.contact.dto.ContactDto;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/telephony/contacts")
public interface ContactController {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    List<ContactDto> getContacts();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    ContactDto getContact(@PathParam("id") String id);

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    void addContact(ContactDto contactDto);

    @PUT
    @Path("{id}")
    void updateContact(@PathParam("id") String id,
                       ContactDto contactDto);

    @DELETE
    @Path("{id}")
    void deleteContact(@PathParam("id") String id);
}
