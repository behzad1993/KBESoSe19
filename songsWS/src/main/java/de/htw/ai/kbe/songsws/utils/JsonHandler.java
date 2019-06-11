package de.htw.ai.kbe.songsws.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.htw.ai.kbe.songsws.pojos.Song;
import de.htw.ai.kbe.songsws.pojos.User;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Handler for JSON data:
 */
public class JsonHandler {

    private JsonHandler() {
    }

    /**
     * Reads a list of songs from a JSON-file into List<Song>
     *
     * @param filename file to read JSON Songs data from
     * @return list of songs
     * @throws IOException in case of missing file or not proper data
     */
    @SuppressWarnings("unchecked")
    public static List<Song> readJSONToSongs(String filename) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        try (InputStream is = new BufferedInputStream(new FileInputStream(filename))) {
            return objectMapper.readValue(is, new TypeReference<List<Song>>() {
            });
        }
    }

    /**
     * Reads a list of users from a JSON-file into List<User>
     *
     * @param filename file to read JSON Users data from
     * @return list of users
     * @throws IOException in case of missing file or not proper data
     */
    @SuppressWarnings("unchecked")
    public static List<User> readJSONToUsers(String filename) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        try (InputStream is = new BufferedInputStream(new FileInputStream(filename))) {
            return objectMapper.readValue(is, new TypeReference<List<User>>() {
            });
        }
    }

}
