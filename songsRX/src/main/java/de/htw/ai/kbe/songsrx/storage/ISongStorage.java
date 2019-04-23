package de.htw.ai.kbe.songsrx.storage;

import de.htw.ai.kbe.songsrx.bean.Song;

import java.util.List;

public interface ISongStorage {
    /**
     * Adds a song and computes the Id of that song
     *
     * @param song the song to add
     * @return the Id of the newly added song
     */
    int add(Song song);

    /**
     * Removes a Song by Id
     *
     * @param id of the song to delete
     * @return the deleted Song
     */
    Song remove(Integer id);

    /**
     * Gets all songs
     *
     * @return a list of Songs
     */
    List<Song> getAllSongs();

    /**
     * Updates a song
     *
     * @param id   the id of the song to update
     * @param song the song to update
     * @return if succeeded
     */
    boolean updateSong(Integer id, Song song);

    /**
     * Gets a single song by Id
     *
     * @param id the id to get song for
     * @return the requested single song
     */
    Song getSingleSong(int id);
}
