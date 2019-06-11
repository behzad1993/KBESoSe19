package de.htw.ai.kbe.songsrx.utils;

import de.htw.ai.kbe.songsrx.bean.Song;
import de.htw.ai.kbe.songsrx.bean.Songs;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.util.List;

/**
 * Handler for Song POJO JSON data:
 * reading from file or stream and writing to stream
 */
public class XmlHandler {

    private XmlHandler() {
    }

    /**
     * Reads a list of songs from a JSON-file into List<Song>
     *
     * @param filename file to read JSON Songs data from
     * @return list of songs
     * @throws IOException in case of missing file or not proper data
     */
    public static List<Song> readXMLToSongs(String filename) throws IOException, JAXBException {

        JAXBContext context = JAXBContext.newInstance(Songs.class); // Songs?
        Unmarshaller unmarshaller = context.createUnmarshaller();
        try (InputStream is = new BufferedInputStream(new FileInputStream(filename))) {
            Songs songs = (Songs) unmarshaller.unmarshal(is);
            return songs.getSongList();
        }
    }


    /**
     * Writes a List<Song> to an output stream in JSON
     *
     * @param songList the songs to write
     * @param filename    the output filename to save
     * @throws IOException in case of not proper data
     */
    static void writeSongsToXML(List<Song> songList, String filename) throws IOException {
        try {
            File file = new File(filename);
            Songs songs = new Songs();
            songs.setSongList(songList);
            JAXBContext context = JAXBContext.newInstance(Songs.class); // Songs?
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, false);
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
            marshaller.marshal(songs, file);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}
