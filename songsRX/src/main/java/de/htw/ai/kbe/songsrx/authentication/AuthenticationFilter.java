package de.htw.ai.kbe.songsrx.authentication;

import de.htw.ai.kbe.songsrx.storage.IUserStorage;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.util.UUID;

@Authenticated
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {

    @Inject
    private IUserStorage userStorage;

    @Override
    public void filter(ContainerRequestContext requestContext) {
        try {
            // Get the Authorization header from the request
            String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

            // Extract the token from the Authorization header
            UUID token = UUID.fromString(authorizationHeader);

            // check if token is existing
            if (!userStorage.isExistingToken(token)) {
                throw new NotAuthorizedException(token);
            }
        } catch (Exception e) {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        }
    }
}