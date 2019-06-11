package de.htw.ai.kbe.songsrx.storage;

import de.htw.ai.kbe.songsrx.bean.Song;
import de.htw.ai.kbe.songsrx.utils.JsonHandler;
import de.htw.ai.kbe.songsrx.utils.XmlHandler;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static java.util.logging.Level.INFO;
import static java.util.logging.Level.SEVERE;

/**
 * Representing a file with songs data
 */
public class InMemorySongStorage implements ISongStorage {

    private static final Logger LOGGER = Logger.getLogger(InMemorySongStorage.class.getName());

    private Map<Integer, Song> songs = new ConcurrentHashMap<>();
    private String filePath;
    private AtomicInteger counterOfIds = new AtomicInteger(10);

    public InMemorySongStorage(String filePath) {
        this.filePath = filePath;
        try {
            load();
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.log(SEVERE, "Could not load data properly from: {0}", filePath);
        }
    }

    /**
     * (Initially) load JSON Songs data from file into songs and set counter of Id's
     *
     * @throws IOException if JSON Songs data couldn't be properly loaded from file
     */
    private void load() throws IOException, JAXBException {
        LOGGER.log(INFO, "Load json songs data from file: {0}", filePath);
        List<Song> songList = XmlHandler.readXMLToSongs(filePath);

        // https://www.mkyong.com/java8/java-8-convert-list-to-map/ Chapter: "List to Map – Collectors.toMap()"
        songs = songList.stream().collect(Collectors.toMap(Song::getId, song -> song));
    }

    public int add(Song song) {
        // title is required
        if (song.getTitle() == null || song.getTitle().isEmpty()) {
            throw new NullPointerException();
        }
        song.setId(counterOfIds.incrementAndGet());
        LOGGER.log(INFO, "Add json song with Id: {0}", song.getId());
        songs.put(song.getId(), song);
        return song.getId();
    }

    public Song remove(Integer id) {
        if (id == null) {
            return null;
        } else {
            LOGGER.log(INFO, "Remove json song with Id: {0}", id);
            return songs.remove(id);
        }
    }

    public List<Song> getAllSongs() {
        LOGGER.log(INFO, "Get all songs.");
        return new ArrayList<>(songs.values());
    }

    public boolean updateSong(Integer id, Song song) {
        if (!id.equals(song.getId())) {
            return false;
        }
        // title is required
        if (song.getTitle() == null || song.getTitle().isEmpty()) {
            throw new NullPointerException();
        }
        LOGGER.log(INFO, "Update json song with Id: {0}", id);
        return songs.replace(id, getSingleSong(id), song);
    }

    public Song getSingleSong(int id) {
        LOGGER.log(INFO, "Get json song with Id: {0}", id);
        return songs.get(id);
    }
}
