package de.htw.ai.kbe.songsws.pojos;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.*;
import java.util.Set;

/**
 * SongList
 */
@XmlRootElement(name = "songList")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Table(name = "songlist")
public class SongList {

    @Id
    private Integer id;

    @Column(name = "ispublic")
    private Boolean isPublic;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable
    @XmlElementWrapper(name = "songs")
    @XmlElement(name = "song")
    private Set<Song> songs;

    @ManyToOne
    @JoinColumn(name = "owner")
    @JsonIgnore
    @XmlTransient
    private User owner;


    public SongList() {
        // is necessary
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Boolean getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(Boolean isPublic) {
        this.isPublic = isPublic;
    }

    public Set<Song> getSongs() {
        return songs;
    }

    public void setSongs(Set<Song> songs) {
        this.songs = songs;
    }

    @Override
    public String toString() {
        return "SongList{" +
                "id=" + id +
                ", isPublic=" + isPublic +
                ", songs=" + songs +
                ", owner=" + owner +
                '}';
    }
}
