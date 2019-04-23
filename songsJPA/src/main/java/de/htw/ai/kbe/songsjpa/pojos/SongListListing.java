package de.htw.ai.kbe.songsjpa.pojos;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "songLists")
@XmlAccessorType(XmlAccessType.FIELD)
public class SongListListing {

    @XmlElement(name = "songList")
    private List<SongList> songListList;

    public SongListListing() {
        this.songListList = new ArrayList<>();
    }

    public void addSongListToList(List<SongList> songList) {
        this.songListList.addAll(songList);
    }
}