package de.htw.ai.kbe.songsrx.services;


import de.htw.ai.kbe.songsrx.authentication.Authenticated;
import de.htw.ai.kbe.songsrx.bean.Song;
import de.htw.ai.kbe.songsrx.storage.ISongStorage;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.util.Collection;
import java.util.logging.Logger;

import static java.util.logging.Level.INFO;

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
        } catch (ClassCastException | NullPointerException | IllegalArgumentException e) {
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
        }
    }

    @DELETE
    @Authenticated
    @Path("/{id}")
    public Response deleteSong(@PathParam("id") Integer id) {
        Song song = songStorage.remove(id);
        if (song != null) {
            LOGGER.log(INFO, "Removed song with id: {0}", id);
            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("No song found with id " + id).build();
        }
    }
}
