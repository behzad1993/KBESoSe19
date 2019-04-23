package de.htw.ai.kbe.songsrx.storage;


import de.htw.ai.kbe.songsrx.bean.User;
import de.htw.ai.kbe.songsrx.utils.JsonHandler;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static java.util.logging.Level.INFO;
import static java.util.logging.Level.SEVERE;

/**
 * Representing a file with user data
 */
public class InMemoryUserStorage implements IUserStorage {

    private static final Logger LOGGER = Logger.getLogger(InMemoryUserStorage.class.getName());

    private Map<String, User> users = new ConcurrentHashMap<>();
    private static Map<String, UUID> validTokens = new ConcurrentHashMap<>();
    private String filePath;

    public InMemoryUserStorage(String filePath) {
        this.filePath = filePath;
        try {
            load();
        } catch (IOException e) {
            LOGGER.log(SEVERE, "Could not load data properly from: {0}", filePath);
        }
    }

    /**
     * (Initially) load JSON Users data from file into users and set counter of Id's
     *
     * @throws IOException if JSON users data couldn't be properly loaded from file
     */
    private void load() throws IOException {
        LOGGER.log(INFO, "Load json users data from file: {0}", filePath);
        List<User> userList = JsonHandler.readJSONToUsers(filePath);

        // https://www.mkyong.com/java8/java-8-convert-list-to-map/ Chapter: "List to Map – Collectors.toMap()"
        users = userList.stream().collect(Collectors.toMap(User::getUserId, user -> user));
    }

    @Override
    public UUID createToken(String userID) {
        UUID token = UUID.randomUUID();
        validTokens.put(userID, token);
        return validTokens.get(userID);
    }

    @Override
    public UUID getToken(String userID) {
        return validTokens.get(userID);
    }

    @Override
    public boolean isExistingToken(UUID token) {
        return validTokens.containsValue(token);
    }

    @Override
    public boolean isExistingUser(String userID) {
        return users.containsKey(userID);
    }

}
