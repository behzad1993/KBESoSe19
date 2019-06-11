package de.htw.ai.kbe.songsrx.bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
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
