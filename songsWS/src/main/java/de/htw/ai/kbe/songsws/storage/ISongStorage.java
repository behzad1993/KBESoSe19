package de.htw.ai.kbe.songsws.storage;

import de.htw.ai.kbe.songsws.pojos.Song;

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


    /**
     * Checks if song is existing in storage
     *
     * @param id the song Id to check
     * @return if existing
     */
    boolean songExistsInSongStorage(int id);
}
