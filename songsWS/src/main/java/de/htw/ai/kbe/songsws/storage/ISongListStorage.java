package de.htw.ai.kbe.songsws.storage;

import de.htw.ai.kbe.songsws.pojos.SongList;

import java.util.List;

public interface ISongListStorage {
    /**
     * Adds a songList and computes the Id of that songList
     *
     * @param songList the songList to add
     * @return the Id of the newly added songList
     */
    int add(SongList songList);

    /**
     * Removes a SongList by Id
     *
     * @param id of the songList to delete
     */
    void remove(Integer id);

    /**
     * Gets all songLists
     *
     * @return a list of SongLists
     */
    List<SongList> getAllSongLists();

    /**
     * Get all songLists from user by his ID
     *
     * @param userId      the id of the user
     * @param privateIsOk shows if private song lists are included
     * @return the requested songLists
     */
    List<SongList> getSongListsFromUser(String userId, boolean privateIsOk);

    /**
     * Gets a single songList by Id
     *
     * @param id the id to get songList for
     * @return the requested single songList
     */
    SongList getSingleSongList(int id);
}
