package de.htw.ai.kbe.servlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.logging.Logger;

import static java.util.logging.Level.SEVERE;


/**
 * Songs Servlet
 */
public class SongsServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(SongsServlet.class.getName());
    private static final long serialVersionUID = 1L;
    private static final String APPLICATION_JSON = "application/json";
    private static final String URL = "http://localhost:8080/songsServlet/";

    private SongDataFile songDataFile;
    private String uriToFakeDB;

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        uriToFakeDB = servletConfig.getInitParameter("uriToFakeDBComponent");
        songDataFile = new SongDataFile(uriToFakeDB);
        try {
            songDataFile.load();
        } catch (IOException e) {
            LOGGER.log(SEVERE, "Can not load json songs data from file: {0}", uriToFakeDB);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!checkAcceptHeader(request)) {
            sendErrorResponse(response, 406, "Accept header not supported.");
            return;
        }

        // all parameter (keys)
        Enumeration<String> paramNames = request.getParameterNames();

        String param = "";
        while (paramNames.hasMoreElements()) {
            param = paramNames.nextElement();
        }
        if (param.contains("all")) {
            response.setContentType(APPLICATION_JSON);
            try {
                JsonHandler.writeSongsToJSON(songDataFile.getAllSongs(), response.getOutputStream());
                if (songDataFile.getAllSongs().size() == 0) {
                    sendErrorResponse(response, 404, "Song list seems not to be existing or empty.");
                }
            } catch (IOException e) {
                LOGGER.log(SEVERE, "Couldn't write json songs to output stream.");
            }
        } else if (param.contains("songId")) {
            response.setContentType(APPLICATION_JSON);
            List<Song> singleSong = new ArrayList<>();
            try {
                singleSong.add(songDataFile.getSingleSong(Integer.valueOf(request.getParameter("songId"))));
            } catch (NumberFormatException e) {
                sendErrorResponse(response, 400, "Song Id might be not given or not a number.");
                return;
            }
            if (singleSong.get(0) == null) {
                sendErrorResponse(response, 404, "Song Id seems not to be existing.");
                return;
            }
            try {
                JsonHandler.writeSongsToJSON(singleSong, response.getOutputStream());
            } catch (IOException e) {
                LOGGER.log(SEVERE, "Couldn't write json song to output stream.");
            }
        } else {
            response.setContentType("text/plain");
            sendErrorResponse(response, 400, "Not supported.");
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain");
        try {
            ServletInputStream inputStream = request.getInputStream();
            int idOfNewlyCreatedSong = songDataFile.add(JsonHandler.readJSONToSong(inputStream));
            // field title is required
            if (songDataFile.getSingleSong(idOfNewlyCreatedSong).getTitle() == null ||
                    songDataFile.getSingleSong(idOfNewlyCreatedSong).getTitle().isEmpty()) {
                throw new IOException();
            }
            response.setHeader("Location", URL + "?songId=" + idOfNewlyCreatedSong);
        } catch (IOException e) {
            LOGGER.log(SEVERE, "Couldn't read json song from input stream.");
            sendErrorResponse(response, 400, "Song might be of wrong format.");
        }
    }

    @Override
    public void destroy() {
        try {
            songDataFile.saveToFile();
        } catch (IOException e) {
            LOGGER.log(SEVERE, "Could not save json songs data to file: {0}", uriToFakeDB);
        }
    }

    protected String getUriToFakeDB() {
        return uriToFakeDB;
    }

    private boolean checkAcceptHeader(HttpServletRequest request) {
        String acceptHeader = request.getHeader("Accept");
        return (acceptHeader == null ||
                acceptHeader.isEmpty() ||
                acceptHeader.contains(APPLICATION_JSON) ||
                acceptHeader.contains("*"));
    }

    private void sendErrorResponse(HttpServletResponse response, int errorCode, String description) {
        try {
            response.sendError(errorCode, description);
        } catch (IOException e) {
            LOGGER.log(SEVERE, "Couldn't send error response.");
        }
    }
}