package de.htw.ai.kbe.songsrx.services;


import de.htw.ai.kbe.songsrx.storage.IUserStorage;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.logging.Logger;

import static java.util.logging.Level.INFO;

@Path("/auth")
public class AuthWebService {

    private static final Logger LOGGER = Logger.getLogger(AuthWebService.class.getName());
    @Inject
    private IUserStorage userStorage;


    @GET
    @Produces("text/plain")
    public Response getOrRefreshAuthToken(@Context UriInfo info) {
        String userId = info.getQueryParameters().getFirst("userId");
        if (userId == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Bad request.").build();
        }
        if (userStorage.isExistingUser(userId)) {
            LOGGER.log(INFO, "Creating auth token for user: {0}", userId);
            return Response.ok(userStorage.createToken(userId).toString()).build();
        } else {
            return Response.status(Response.Status.FORBIDDEN).entity("Unknown user.").build();
        }
    }
}
