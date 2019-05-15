package de.htw.ai.kbe.servlet;

import org.junit.Test;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertTrue;


public class XmlHandlerTest {

    @Test
    public void test_readXMLToSongs_succesReadingXML() throws IOException, JAXBException {
        String filename = "extern/songs.xml";
        List<Song> songs = XmlHandler.readXMLToSongs(filename);
        assertTrue(songs.size() > 0);
    }

    @Test
    public void test_writeSongsToJSON_succesCreatingXML() throws IOException {
        String filename = "extern/songsCreating.xml";
        Song randomSong = createRandomSong();
        Songs songs = new Songs();
        songs.addSongToList(randomSong);
        XmlHandler.writeSongsToXML(songs.getSongList(), filename);
    }

    private Song createRandomSong() {
        Song song = new Song();
        song.setId(1);
        song.setAlbum("album");
        song.setArtist("artist");
        song.setReleased(9999);
        song.setTitle("title");
        return song;
    }
}