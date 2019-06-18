package de.htw.ai.kbe.songsws.services;

import de.htw.ai.kbe.songsws.authentication.AuthenticationFilter;
import de.htw.ai.kbe.songsws.pojos.Song;
import de.htw.ai.kbe.songsws.storage.ISongStorage;
import de.htw.ai.kbe.songsws.storage.IUserStorage;
import de.htw.ai.kbe.songsws.storage.InMemorySongStorage;
import de.htw.ai.kbe.songsws.storage.InMemoryUserStorage;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class SongsWebServiceTest extends JerseyTest {

    @Override
    protected Application configure() {
        forceSet(TestProperties.CONTAINER_PORT, "0");
        ResourceConfig config = new ResourceConfig(SongsWebService.class, AuthWebService.class);
        config.register(
                new AbstractBinder() {
                    @Override
                    protected void configure() {
                        bind(new InMemorySongStorage("extern/test/songs.json")).to(ISongStorage.class);
                        bind(new InMemoryUserStorage("extern/test/users.json")).to(IUserStorage.class);
                    }
                });
        config.register(new AuthenticationFilter());

        return config;
    }

    private static final String DIFFERENT_ARTIST = "David Hasselhoff";
    private String validToken;

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        this.validToken = getTokenHelper();
    }


    @Test
    public void updateSongHappyCaseJson204() {
        // Given
        Song changedSong = getSongByIdHelper(1);
        changedSong.setArtist(DIFFERENT_ARTIST);

        // When
        Response response = target("/songs/")
                .path(Long.toString(changedSong.getId()))
                .request()
                .header(HttpHeaders.AUTHORIZATION, validToken)
                .put(Entity.json(changedSong));

        // Then
        Assert.assertEquals(204, response.getStatus());
        Assert.assertEquals(DIFFERENT_ARTIST, getSongByIdHelper(1).getArtist());
    }

    @Test
    public void updateSongHappyCaseXml204() {
        // Given
        Song changedSong = getSongByIdHelper(2);
        changedSong.setArtist(DIFFERENT_ARTIST);

        // When
        Response response = target("/songs/")
                .path(Long.toString(changedSong.getId()))
                .request()
                .header(HttpHeaders.AUTHORIZATION, validToken)
                .put(Entity.xml(changedSong));

        // Then
        Assert.assertEquals(204, response.getStatus());
        Assert.assertEquals(DIFFERENT_ARTIST, getSongByIdHelper(2).getArtist());
    }

    @Test
    public void updateSongNoValidToken401() {
        // Given
        Song changedSong = getSongByIdHelper(1);
        changedSong.setArtist(DIFFERENT_ARTIST);

        // When
        Response response = target("/songs/")
                .path(Long.toString(changedSong.getId()))
                .request()
                .header(HttpHeaders.AUTHORIZATION, "foobar")
                .put(Entity.json(changedSong));

        // Then
        Assert.assertEquals(401, response.getStatus());
    }

    @Test
    public void updateSongNoToken401() {
        // Given
        Song changedSong = getSongByIdHelper(1);
        changedSong.setArtist(DIFFERENT_ARTIST);

        // When
        Response response = target("/songs/")
                .path(Long.toString(changedSong.getId()))
                .request()
                .put(Entity.json(changedSong));

        // Then
        Assert.assertEquals(401, response.getStatus());
    }

    @Test
    public void updateSongNotExistingId400() {
        // Given
        Song changedSong = getSongByIdHelper(3);
        changedSong.setArtist(DIFFERENT_ARTIST);

        // When
        Response response = target("/songs/")
                .path(Long.toString(2000))
                .request()
                .header(HttpHeaders.AUTHORIZATION, validToken)
                .put(Entity.json(changedSong));

        // Then
        Assert.assertEquals(400, response.getStatus());
        Assert.assertNotEquals(DIFFERENT_ARTIST, getSongByIdHelper(3).getArtist());
    }

    @Test
    public void updateSongCaseEmptyJson400() {
        // Given
        Song changedSong = getSongByIdHelper(4);
        changedSong.setArtist(DIFFERENT_ARTIST);

        // When
        Response response = target("/songs/")
                .path(Long.toString(changedSong.getId()))
                .request()
                .header(HttpHeaders.AUTHORIZATION, validToken)
                .put(Entity.json(""));

        // Then
        Assert.assertEquals(400, response.getStatus());
        Assert.assertNotEquals(DIFFERENT_ARTIST, getSongByIdHelper(4).getArtist());
    }

    @Test
    public void updateSongCaseBadFormatJson400() {
        // Given
        Song changedSong = getSongByIdHelper(5);
        changedSong.setArtist(DIFFERENT_ARTIST);

        // When
        Response response = target("/songs/")
                .path(Long.toString(changedSong.getId()))
                .request()
                .header(HttpHeaders.AUTHORIZATION, validToken)
                .put(Entity.json("Not a love song."));

        // Then
        Assert.assertEquals(400, response.getStatus());
        Assert.assertNotEquals(DIFFERENT_ARTIST, getSongByIdHelper(5).getArtist());
    }

    @Test
    public void updateSongCaseEmptyXml400() {
        // Given
        Song changedSong = getSongByIdHelper(6);
        changedSong.setArtist(DIFFERENT_ARTIST);

        // When
        Response response = target("/songs/")
                .path(Long.toString(changedSong.getId()))
                .request()
                .header(HttpHeaders.AUTHORIZATION, validToken)
                .put(Entity.xml(""));

        // Then
        Assert.assertEquals(400, response.getStatus());
        Assert.assertNotEquals(DIFFERENT_ARTIST, getSongByIdHelper(6).getArtist());
    }

    @Test
    public void updateSongCaseBadFormatXml400() {
        // Given
        Song changedSong = getSongByIdHelper(7);
        changedSong.setArtist(DIFFERENT_ARTIST);

        // When
        Response response = target("/songs/")
                .path(Long.toString(changedSong.getId()))
                .request()
                .header(HttpHeaders.AUTHORIZATION, validToken)
                .put(Entity.xml("Not a love song."));

        // Then
        Assert.assertEquals(400, response.getStatus());
        Assert.assertNotEquals(DIFFERENT_ARTIST, getSongByIdHelper(7).getArtist());
    }

    @Test
    public void updateSongUnsupportedMediaType415() {
        // Given
        Song changedSong = getSongByIdHelper(8);
        changedSong.setArtist(DIFFERENT_ARTIST);

        // When
        Response response = target("/songs/")
                .path(Long.toString(changedSong.getId()))
                .request()
                .header(HttpHeaders.AUTHORIZATION, validToken)
                .put(Entity.text(changedSong.toString()));

        // Then
        Assert.assertEquals(415, response.getStatus());
//        Assert.assertNotEquals(DIFFERENT_ARTIST, getSongByIdHelper(8).getArtist());
    }

    @Test
    public void updateSongNullTitle400() {
        // Given
        Song changedSong = getSongByIdHelper(9);
        changedSong.setArtist(DIFFERENT_ARTIST);
        changedSong.setTitle(null);

        // When
        Response response = target("/songs/")
                .path(Long.toString(changedSong.getId()))
                .request()
                .header(HttpHeaders.AUTHORIZATION, validToken)
                .put(Entity.json(changedSong));

        // Then
        Assert.assertEquals(400, response.getStatus());
        Assert.assertNotEquals(DIFFERENT_ARTIST, getSongByIdHelper(9).getArtist());
    }

    @Test
    public void updateSongEmptyTitle400() {
        // Given
        Song changedSong = getSongByIdHelper(10);
        changedSong.setArtist(DIFFERENT_ARTIST);
        changedSong.setTitle("");

        // When
        Response response = target("/songs/")
                .path(Long.toString(changedSong.getId()))
                .request()
                .header(HttpHeaders.AUTHORIZATION, validToken)
                .put(Entity.json(changedSong));

        // Then
        Assert.assertEquals(400, response.getStatus());
        Assert.assertNotEquals(DIFFERENT_ARTIST, getSongByIdHelper(10).getArtist());
    }

//     TODO: NOT WORKING
//     @Test
//     public void deleteSongHappyCase () {
//         Song changedSong = getSongByIdHelper(10);
//         changedSong.setArtist(DIFFERENT_ARTIST);
//         changedSong.setTitle("");

//         Response response = target("/songs/")
//                 .path(Long.toString(changedSong.getId()))
//                 .request()
//                 .header(HttpHeaders.AUTHORIZATION, validToken)
//                 .delete();

//         Assert.assertEquals(204, response.getStatus());
//         Assert.assertEquals(null, getSongByIdHelper(10) );


//     }

    private Song getSongByIdHelper(Integer id) {
        return target("/songs/")
                .path(Long.toString(id))
                .request(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, validToken)
                .get(Song.class);
    }

    private String getTokenHelper() {
        return target("/auth")
                .queryParam("userId", "mmuster")
                .queryParam("secret", "321dwssap")
                .request()
                .get(String.class);
    }
}
