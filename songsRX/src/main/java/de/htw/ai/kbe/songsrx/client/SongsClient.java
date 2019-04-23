package de.htw.ai.kbe.songsrx.client;

import de.htw.ai.kbe.songsrx.bean.Song;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import static java.util.logging.Level.INFO;

public class SongsClient {

    private static final Logger LOGGER = Logger.getLogger(SongsClient.class.getName());

    private static Client client = ClientBuilder.newClient();
    private static WebTarget baseTarget = client.target("http://localhost:8080/songsRX/rest/songs");

    private static String validToken = getValidToken("http://localhost:8080/songsRX/rest/auth","mmuster");


    public static void main(String[] args) {
        listSongs();
        getSong(1);
        createSongs();
        deleteSongById(3, 4);
        listSongs();

        Song song = getSong(2);
        song.setArtist("NEW_ARTIST");
        updateSong(song);
        getSong(2);
    }

    private static void listSongs() {
        LOGGER.log(INFO, "------- Getting all Songs");

        String response = baseTarget
                .request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, validToken)
                .get(String.class);
        LOGGER.log(INFO, "JSON-Songs received: {0}", response);

        List<Song> songs = baseTarget
                .request()
                .header(HttpHeaders.AUTHORIZATION, validToken)
                .get(new GenericType<List<Song>>() {
                });
        songs.forEach(System.out::println);
    }

    private static Song getSong(long id) {
        LOGGER.log(INFO, "------- Getting song with id: {0}", id);

        String response = baseTarget
                .path(Long.toString(id))
                .request(MediaType.APPLICATION_XML)
                .header(HttpHeaders.AUTHORIZATION, validToken)
                .get(String.class);
        LOGGER.log(INFO, "XML-Song retrieved: {0}", response);

        Song song = baseTarget
                .path(Long.toString(id))
                .request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, validToken)
                .get(Song.class);
        LOGGER.log(INFO, "JSON-Song retrieved: {0}", song);

        id = 2000;
        LOGGER.log(INFO, "------- Trying to get song with not present id: {0}", id);

        Response response404 = baseTarget
                .path(Long.toString(id))
                .request(MediaType.APPLICATION_XML)
                .header(HttpHeaders.AUTHORIZATION, validToken)
                .get();
        LOGGER.log(INFO, "Song not found: {0}", response404);

        return song;
    }

    private static void createSongs() {
        LOGGER.log(INFO, "------- Creating or posting songs:");
        for (int i = 1; i <= 5; i++) {
            Song song = new Song();
            song.setTitle("FirstTitle " + i);
            song.setArtist("Uwe " + i);
            Response response;
            Entity<Song> entity;
            if (i % 2 == 0) {
                entity = Entity.xml(song);
            } else {
                entity = Entity.json(song);
            }
            LOGGER.log(INFO, "Posting new song: {0}", entity);
            response = baseTarget
                    .request()
                    .header(HttpHeaders.AUTHORIZATION, validToken)
                    .post(entity);
            LOGGER.log(INFO, "Created song: "
                    + response.getStatus() + " Location = "
                    + response.getLocation());
        }
    }

    private static void deleteSongById(int... ids) {
        System.out.printf("------- Deleting songs: %s\n", Arrays.toString(ids));
        for (int id : ids) {
            Response response = baseTarget
                    .path(Long.toString(id))
                    .request()
                    .header(HttpHeaders.AUTHORIZATION, validToken)
                    .delete();
            LOGGER.log(INFO, "Deleted song... Status code: {0}", response.getStatus());
        }
    }

    private static void updateSong(Song song) {
        LOGGER.log(INFO, "------- Updating (putting) a song with id: {0}", song.getId());
        Response response = baseTarget
                .path(Long.toString(song.getId()))
                .request()
                .header(HttpHeaders.AUTHORIZATION, validToken)
                .put(Entity.xml(song));
        LOGGER.log(INFO, "Updated song: {0}", response.getStatus());
    }

    private static String getValidToken(String uri, String userId) {
        WebTarget authTarget = client.target(uri);

        return authTarget
                .queryParam("userId", userId)
                .request()
                .get(String.class);
    }
}
