package de.htw.ai.kbe.songsjpa.storage;

import de.htw.ai.kbe.songsjpa.pojos.User;

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
     * Gets a User (by token)
     *
     * @param token to get the user for
     * @return the requested user
     */
    User getUser(UUID token);

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
