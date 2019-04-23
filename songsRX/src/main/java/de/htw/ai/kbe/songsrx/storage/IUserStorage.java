package de.htw.ai.kbe.songsrx.storage;

import java.util.UUID;

public interface IUserStorage {

    /**
     * Creates a new token for a user (by Id)
     *
     * @param userID the Id of the user to create token for
     * @return the created token
     */
    UUID createToken(String userID);

    /**
     * Gets a token of a user (by Id)
     *
     * @param userID to get the token for
     * @return the requested token
     */
    UUID getToken(String userID);

    /**
     * Checks if token is valid/ existing
     *
     * @param token to check
     * @return if token is valid i.e. existing
     */
    boolean isExistingToken(UUID token);

    /**
     * Checks if user is existing
     *
     * @param userID the user Id to check
     * @return if user (Id) is existing
     */
    boolean isExistingUser(String userID);
}
