package de.htw.ai.kbe.servlet;

import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "songs")
public class Songs {

    @XmlElementWrapper(name = "song")
    private List<Song> songList = new ArrayList<>();

    public Songs() {
    }

    public List<Song> getSongList() {
        return songList;
    }

    public void setSongList(List<Song> songList) {
        this.songList = songList;
    }

    public void addSongToList(Song song) {
        songList.add(song);
    }

    public Song getSongFromListById(Integer id) {
        for (Song song : songList) {
            if (song.getId() == id) {
                return song;
            }
        }
        return null;
    }
}
