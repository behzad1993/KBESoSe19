package de.htw.ai.kbe.songsjpa.storage;

import de.htw.ai.kbe.songsjpa.config.Config;
import de.htw.ai.kbe.songsjpa.pojos.User;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Collections.singletonList;


public class PostgresUserStorage implements IUserStorage {

    private EntityManagerFactory factory;
    private static Map<String, UUID> validTokens = new ConcurrentHashMap<>();

    @Inject
    public PostgresUserStorage(EntityManagerFactory factory) {
        this.factory = factory;
        Config.addEntityManagerFactory(factory);
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
    public User getUser(UUID token) {
        for (Map.Entry<String, UUID> stringUUIDEntry : validTokens.entrySet()) {
            if (stringUUIDEntry.getValue().equals(token)) {
                return getSingleUser(stringUUIDEntry.getKey());
            }
        }
        return null;
    }

    @Override
    public boolean isExistingToken(UUID token) {
        return validTokens.containsValue(token);
    }

    @Override
    public boolean isExistingUser(String userID) {
        EntityManager em = factory.createEntityManager();
        List<User> listUsers;
        try {
            listUsers = singletonList(em.find(User.class, userID));
        } finally {
            em.close();
        }
        return listUsers != null && listUsers.get(0) != null;
    }

    private User getSingleUser(String userID) {
        EntityManager em = factory.createEntityManager();
        User entity;
        try {
            entity = em.find(User.class, userID);
        } finally {
            em.close();
        }
        return entity;
    }
}
