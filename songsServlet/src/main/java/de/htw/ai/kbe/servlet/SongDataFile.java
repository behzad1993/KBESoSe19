package de.htw.ai.kbe.servlet;

import javax.xml.bind.JAXBException;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static java.util.logging.Level.INFO;

/**
 * Representing a file with songs data
 */
public class SongDataFile {

    private static final Logger LOGGER = Logger.getLogger(SongDataFile.class.getName());

    private String filePath;
    private Map<Integer, Song> songs = new HashMap<>();
    private int counterOfIds;

    public SongDataFile(String filePath) {
        this.filePath = filePath;
    }

    /**
     * (Initially) load JSON Songs data from file into songs and set counter of Id's
     *
     * @throws IOException if JSON Songs data couldn't be properly loaded from file
     */
    public synchronized void load() throws IOException, JAXBException {
        LOGGER.log(INFO, "Load json songs data from file: {0}", filePath);
//        List<Song> songList = JsonHandler.readJSONToSongs(filePath);
        List<Song> songList = XmlHandler.readXMLToSongs(filePath);
        // https://www.mkyong.com/java8/java-8-convert-list-to-map/ Chapter: "List to Map – Collectors.toMap()"
        songs = songList.stream().collect(Collectors.toMap(Song::getId, song -> song));
        counterOfIds = songs.size() + 1;
    }

    /**
     * Saves JSON Songs data in songs to file (on shutdown)
     *
     * @throws IOException in case of missing file or not proper data
     */
    public synchronized void saveToFile() throws IOException {
        LOGGER.log(INFO, "Save json songs data to file: {0}", filePath);
        OutputStream os = new BufferedOutputStream(new FileOutputStream(filePath));
//        JsonHandler.writeSongsToJSON(getAllSongs(), os);
        XmlHandler.writeSongsToXML(getAllSongs(), filePath);
    }

    /**
     * Adds a song and computes the Id of that song
     *
     * @param song the song to add
     * @return the Id of the newly added song
     */
    public synchronized int add(Song song) {
        LOGGER.log(INFO, "Add json song with Id: {0}", counterOfIds);
        song.setId(counterOfIds);
        counterOfIds++;
        songs.put(song.getId(), song);
        return song.getId();
    }

    /**
     * Gets all songs
     *
     * @return a list of Songs
     */
    public synchronized List<Song> getAllSongs() {
        LOGGER.log(INFO, "Get all songs.");
        return new ArrayList<>(songs.values());
    }

    /**
     * Gets a single song by Id
     *
     * @param id the id to get song for
     * @return the requested single song
     */
    public synchronized Song getSingleSong(int id) {
        LOGGER.log(INFO, "Get json song with Id: {0}", id);
        return songs.get(id);
    }
}
