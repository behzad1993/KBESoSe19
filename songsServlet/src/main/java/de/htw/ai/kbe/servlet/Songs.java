package de.htw.ai.kbe.servlet;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "songs")
@XmlAccessorType(XmlAccessType.FIELD)
public class Songs {

    @XmlElement(name = "song")
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
}
