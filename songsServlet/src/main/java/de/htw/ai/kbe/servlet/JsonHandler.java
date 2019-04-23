package de.htw.ai.kbe.servlet;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * Handler for Song POJO JSON data:
 * reading from file or stream and writing to stream
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
    static List<Song> readJSONToSongs(String filename) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        try (InputStream is = new BufferedInputStream(new FileInputStream(filename))) {
            return objectMapper.readValue(is, new TypeReference<List<Song>>() {
            });
        }
    }

    /**
     * Reads a song in JSON format from input stream into Song
     *
     * @param is InputStream to read JSON Song data from
     * @return the read song
     * @throws IOException in case of not proper data
     */
    static Song readJSONToSong(InputStream is) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(is, new TypeReference<Song>() {
        });
    }

    /**
     * Writes a List<Song> to an output stream in JSON
     *
     * @param songs the songs to write
     * @param os    the output stream to write to
     * @throws IOException in case of not proper data
     */
    static void writeSongsToJSON(List<Song> songs, OutputStream os) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(os, songs);
    }
}
