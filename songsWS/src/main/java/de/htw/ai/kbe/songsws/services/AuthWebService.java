package de.htw.ai.kbe.songsws.services;


import de.htw.ai.kbe.songsws.storage.IUserStorage;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;
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
        MultivaluedMap<String, String> queryParameters = info.getQueryParameters();
        try {
            String userId = queryParameters.get("userId").get(0);
            String encodedPassword = queryParameters.get("secret").get(0);
            String password = decodePassword(encodedPassword);
            if (userId == null || password == null) {
                return Response.status(Response.Status.BAD_REQUEST).entity("Bad request.").build();
            }

            if (userStorage.isCorrectPassword(userId, password)) {
                LOGGER.log(INFO, "Creating auth token for user: {0}", userId);
                return Response.ok(userStorage.createToken(userId).toString()).build();
            } else {
                return Response.status(Response.Status.FORBIDDEN).entity("User and password combination is wrong").build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Bad request.").build();
        }
    }

    private String decodePassword(String encodedPoassword) {
        int iterator = encodedPoassword.length();
        StringBuilder decodedPasswordBuilder = new StringBuilder();
        for (; iterator > 0; iterator--) {
            decodedPasswordBuilder.append(encodedPoassword.charAt(iterator - 1));
        }
        return decodedPasswordBuilder.toString();
    }
}
