package ca.ulaval.glo4003.ws.api.calllog;


import ca.ulaval.glo4003.ws.api.calllog.dto.CallLogDto;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/telephony/calllogs")
public interface CallLogController {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    List<CallLogDto> getCallLogs();

    @DELETE
    @Path("{id}")
    void deleteCallLog(@PathParam("id") String id);
}
