package de.htw.ai.kbe.songsws.services;

import de.htw.ai.kbe.songsws.authentication.Authenticated;
import de.htw.ai.kbe.songsws.pojos.Song;
import de.htw.ai.kbe.songsws.pojos.SongList;
import de.htw.ai.kbe.songsws.pojos.SongListListing;
import de.htw.ai.kbe.songsws.storage.ISongListStorage;
import de.htw.ai.kbe.songsws.storage.ISongStorage;
import de.htw.ai.kbe.songsws.storage.IUserStorage;

import javax.inject.Inject;
import javax.persistence.PersistenceException;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

import static java.util.logging.Level.INFO;
import static java.util.logging.Level.SEVERE;


@Path("/songLists")
public class SongListWebService {

    private static final Logger LOGGER = Logger.getLogger(SongListWebService.class.getName());
    @Inject
    private ISongListStorage songListStorage;
    @Inject
    private ISongStorage songStorage;
    @Inject
    private IUserStorage userStorage;

    @GET
    @Authenticated
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response getSongList(@PathParam("id") Integer id, @HeaderParam("authorization") UUID token) {
        SongList songList = songListStorage.getSingleSongList(id);
        if (songList != null) {
            if (songListIsPublic(songList) || tokenBelongsToUserId(token, songList.getOwner().getUserId())) {
                LOGGER.log(INFO, "getSongList: Returning songList for id: {0}", id);
                return Response.ok(songList).build();
            } else {
                return Response.status(Response.Status.FORBIDDEN).build();
            }
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("No song list found with id " + id).build();
        }
    }

    @DELETE
    @Authenticated
    @Path("/{id}")
    public Response deleteSong(@PathParam("id") Integer id, @HeaderParam("authorization") UUID token) {
        SongList songList = songListStorage.getSingleSongList(id);
        if (songList != null) {
            if (tokenBelongsToUserId(token, songList.getOwner().getUserId())) {
                LOGGER.log(INFO, "Remove song list with id: {0} ...", id);
                try {
                    songListStorage.remove(id);
                } catch (PersistenceException e) {
                    return Response.status(Response.Status.SERVICE_UNAVAILABLE).build();
                }
                return Response.noContent().build();
            } else {
                return Response.status(Response.Status.FORBIDDEN).build();
            }
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("No song list found with id " + id).build();
        }
    }

    @GET
    @Authenticated
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getSongLists(@Context UriInfo info, @HeaderParam("authorization") UUID token) {
        String userId = info.getQueryParameters().getFirst("userId");
        boolean privateIsOk = tokenBelongsToUserId(token, userId);

        List<SongList> songListsFromUser = songListStorage.getSongListsFromUser(userId, privateIsOk);
        SongListListing songListListing = new SongListListing();
        songListListing.addSongListToList(songListsFromUser);

        if (!songListsFromUser.isEmpty()) {
            return Response.ok(songListListing).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }


    @Context
    UriInfo uriInfo;

    @POST
    @Authenticated
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces(MediaType.TEXT_PLAIN)
    public Response createSongList(SongList songList, @HeaderParam("authorization") UUID token) {
        // set owner to authenticated user as this is the only possibility
        songList.setOwner(userStorage.getUser(token));

        if (songList.getSongs() != null &&
                !songList.getSongs().isEmpty() &&
                songList.getSongs().stream().map(Song::getId).allMatch(songStorage::songExistsInSongStorage)) {

            int newId;
            try {
                newId = songListStorage.add(songList);
            } catch (ClassCastException | NullPointerException | IllegalArgumentException | PersistenceException e) {
                LOGGER.log(SEVERE, "Error creating/ adding song list: {0}", e.getMessage());
                return Response.status(Response.Status.BAD_REQUEST).entity(
                        "Not created. (Hint: flag isPublic is required for song list creation)")
                        .build();
            }
            UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
            uriBuilder.path(Integer.toString(newId));

            return Response.created(uriBuilder.build()).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Not created. (Hint: at least one song is required for song list creation. All songs must exist.)")
                    .build();
        }

    }


    private boolean tokenBelongsToUserId(UUID token, String userId) {
        return userStorage.getToken(userId) != null && userStorage.getToken(userId).equals(token);
    }

    private boolean songListIsPublic(SongList songList) {
        return songList.getIsPublic();
    }


}
