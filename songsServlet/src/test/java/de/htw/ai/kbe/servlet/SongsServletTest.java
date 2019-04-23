package de.htw.ai.kbe.servlet;

import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletConfig;

import javax.servlet.ServletException;
import java.io.IOException;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class SongsServletTest {

    private SongsServlet servlet;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;

    private static final String URITOFAKEDB_STRING = "extern/songs.json.bak";
    private static final String URITOFAKEEMPTYDB_STRING = "extern/emptySongList.json.bak";
    private static final String APPLICATION_JSON = "application/json";
    private static final String TEXT_PLAIN = "text/plain";
    private static final String ACCEPT = "Accept";
    private static final String SONG_ID = "songId";

    @Before
    public void setUp() throws ServletException {
        servlet = new SongsServlet();
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        MockServletConfig config = new MockServletConfig();
        config.addInitParameter("uriToFakeDBComponent", URITOFAKEDB_STRING);
        servlet.init(config); //throws ServletException
    }

    @Test
    public void initShouldSetDBComponentURI() {
        assertEquals(URITOFAKEDB_STRING, servlet.getUriToFakeDB());
    }

    @Test
    public void doGetAllSuccess() throws IOException, ServletException {
        request.addParameter("all");

        servlet.doGet(request, response);

        assertEquals(200, response.getStatus());
        assertEquals(APPLICATION_JSON, response.getContentType());
        String songs = response.getContentAsString();
        // check existence of song 1
        assertThat(songs, containsString("\"id\":1"));
        assertThat(songs, containsString("\"title\":\"Canâ\u0080\u0099t Stop the Feeling\""));
        assertThat(songs, containsString("\"artist\":\"Justin Timberlake\""));
        assertThat(songs, containsString("\"album\":\"Trolls\""));
        assertThat(songs, containsString("\"released\":2016"));
        // check existence of song 10
        assertThat(songs, containsString("\"id\":10"));
        assertThat(songs, containsString("\"title\":\"7 Years\""));
        assertThat(songs, containsString("\"artist\":\"Lukas Graham\""));
        assertThat(songs, containsString("\"album\":\"Lukas Graham (Blue Album)\""));
        assertThat(songs, containsString("\"released\":2015"));
    }

    @Test
    public void doGetSingleSongShouldResponseSongAsJsonSuccess() throws IOException, ServletException {
        request.addParameter(SONG_ID, "6");

        servlet.doGet(request, response);

        assertEquals(200, response.getStatus());
        assertEquals(APPLICATION_JSON, response.getContentType());
        String song = response.getContentAsString();
        assertThat(song, containsString("\"id\":6"));
        assertThat(song, containsString("\"title\":\"I Took a Pill in Ibiza\""));
        assertThat(song, containsString("\"artist\":\"Mike Posner\""));
        assertThat(song, containsString("\"album\":\"At Night, Alone.\""));
        assertThat(song, containsString("\"released\":2016"));
    }

    @Test
    public void doGetSingleSongNoJsonResponse406() throws IOException, ServletException {
        request.addParameter(SONG_ID, "6");
        request.addHeader(ACCEPT, "application/xml");

        servlet.doGet(request, response);

        assertEquals(406, response.getStatus());
        assertTrue(response.getErrorMessage().contains("Accept header not supported."));
    }

    @Test
    public void doGetSingleSongNotFoundIdShouldResponse404() throws IOException, ServletException {
        request.addParameter(SONG_ID, "600");

        servlet.doGet(request, response);

        assertEquals(APPLICATION_JSON, response.getContentType());
        assertEquals(404, response.getStatus());
        assertTrue(response.getErrorMessage().contains("Song Id seems not to be existing."));
    }

    @Test
    public void doGetSingleSongEmptyValueShouldResponse400() throws IOException, ServletException {
        request.addParameter(SONG_ID, "");

        servlet.doGet(request, response);

        assertEquals(APPLICATION_JSON, response.getContentType());
        assertEquals(400, response.getStatus());
        assertTrue(response.getErrorMessage().contains("Song Id might be not given or not a number."));
    }

    @Test
    public void doGetSingleSongWrongValueShouldResponse400() throws IOException, ServletException {
        request.addParameter(SONG_ID, "sieben einhalb bitte");

        servlet.doGet(request, response);

        assertEquals(APPLICATION_JSON, response.getContentType());
        assertEquals(400, response.getStatus());
        assertTrue(response.getErrorMessage().contains("Song Id might be not given or not a number."));
    }

    @Test
    public void doPostSuccess() throws IOException, ServletException {
        request.setContent("{\"title\" : \"a new title\", \"artist\" : \"YOU\", \"album\" : \"First Album\", \"released\" : 2017 }".getBytes());

        servlet.doPost(request, response);

        assertEquals(200, response.getStatus());
        assertEquals(TEXT_PLAIN, response.getContentType());
        assertTrue(response.getHeader("Location").contains("http://localhost:8080/songsServlet/?songId=11"));
    }

    @Test
    public void doPostEmptyTitle400() throws IOException, ServletException {
        request.setContent("{\"title\" : \"\", \"artist\" : \"YOU\", \"album\" : \"First Album\", \"released\" : 2017 }".getBytes());

        servlet.doPost(request, response);

        assertEquals(400, response.getStatus());
        assertEquals(TEXT_PLAIN, response.getContentType());
        assertTrue(response.getErrorMessage().contains("Song might be of wrong format."));
    }

    @Test
    public void doPostNoTitle400() throws IOException, ServletException {
        request.setContent("{\"artist\" : \"YOU\", \"album\" : \"First Album\", \"released\" : 2017 }".getBytes());

        servlet.doPost(request, response);

        assertEquals(400, response.getStatus());
        assertEquals(TEXT_PLAIN, response.getContentType());
        assertTrue(response.getErrorMessage().contains("Song might be of wrong format."));
    }

    @Test
    public void doPostWrongFormatForSongResponse400() throws IOException, ServletException {
        // adding not allowed extra property: "quality" : 9
        request.setContent(("{\"title\" : \"a new title\", \"artist\" : \"YOU\", \"album\" : " +
                "\"First Album\", \"released\" : 2017, \"quality\" : 9 }").getBytes());

        servlet.doPost(request, response);

        assertEquals(400, response.getStatus());
        assertTrue(response.getErrorMessage().contains("Song might be of wrong format."));
    }

    @Test
    public void doGetShouldNotEchoParametersAnyMore() throws IOException, ServletException {
        request.addParameter("username", "scott");

        servlet.doGet(request, response);

        assertEquals(TEXT_PLAIN, response.getContentType());
        assertEquals(400, response.getStatus());
        assertTrue(response.getErrorMessage().contains("Not supported."));
    }

    @Test
    public void doGetOnEmptyDB() throws ServletException, IOException {
        setUpEmptySongList();

        request.addParameter("all");

        servlet.doGet(request, response);

        assertEquals(404, response.getStatus());
        assertTrue(response.getErrorMessage().contains("Song list seems not to be existing or empty."));
    }

    private void setUpEmptySongList() throws ServletException {
        MockServletConfig config = new MockServletConfig();
        config.addInitParameter("uriToFakeDBComponent", URITOFAKEEMPTYDB_STRING);
        servlet.init(config); //throws ServletException
    }
}
