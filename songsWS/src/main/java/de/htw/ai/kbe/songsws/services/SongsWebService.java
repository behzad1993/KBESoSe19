package de.htw.ai.kbe.songsws.services;


import de.htw.ai.kbe.songsws.authentication.Authenticated;
import de.htw.ai.kbe.songsws.pojos.Song;
import de.htw.ai.kbe.songsws.storage.ISongStorage;

import javax.inject.Inject;
import javax.persistence.PersistenceException;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.util.Collection;
import java.util.logging.Logger;

import static java.util.logging.Level.INFO;
import static java.util.logging.Level.SEVERE;

@Path("/songs")
public class SongsWebService {

    private static final Logger LOGGER = Logger.getLogger(SongsWebService.class.getName());
    @Inject
    private ISongStorage songStorage;


    @GET
    @Authenticated
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Collection<Song> getAllSongs() {
        LOGGER.log(INFO, "getAllSongs: Returning all songs!");
        return songStorage.getAllSongs();
    }

    @GET
    @Authenticated
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response getSong(@PathParam("id") Integer id) {
        Song song = songStorage.getSingleSong(id);
        if (song != null) {
            LOGGER.log(INFO, "getSong: Returning song for id: {0}", id);
            return Response.ok(song).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("No song found with id " + id).build();
        }
    }

    @Context
    UriInfo uriInfo;

    @POST
    @Authenticated
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces(MediaType.TEXT_PLAIN)
    public Response createSong(Song song) {
        int newId;
        try {
            newId = songStorage.add(song);
        } catch (ClassCastException | NullPointerException | IllegalArgumentException | PersistenceException e) {
            LOGGER.log(SEVERE, "Error creating/ adding song: {0}", e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity("Not created. (Hint: title is required)").build();
        }
        UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
        uriBuilder.path(Integer.toString(newId));
        return Response.created(uriBuilder.build()).build();
    }

    @PUT
    @Authenticated
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Path("/{id}")
    public Response updateSong(@PathParam("id") Integer id, Song song) {
        try {
            if (songStorage.updateSong(id, song)) {
                return Response.noContent().build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST).entity("Not updated.").build();
            }
            } catch (ClassCastException | NullPointerException | IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Not updated. (Hint: title is required)").build();
        } catch (PersistenceException p) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Not updated. (Hint: songs already used in songlists can't be updated)")
                    .build();
        }
    }
}
