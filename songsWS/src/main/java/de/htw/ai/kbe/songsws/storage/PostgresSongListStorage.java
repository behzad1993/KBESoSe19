package de.htw.ai.kbe.songsws.storage;

import de.htw.ai.kbe.songsws.config.Config;
import de.htw.ai.kbe.songsws.pojos.SongList;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

import static java.util.logging.Level.INFO;
import static java.util.logging.Level.SEVERE;

public class PostgresSongListStorage implements ISongListStorage {

    private static final Logger LOGGER = Logger.getLogger(PostgresSongListStorage.class.getName());
    private EntityManagerFactory factory;
    private AtomicInteger counterOfIds;


    @Inject
    public PostgresSongListStorage(EntityManagerFactory factory) {
        this.factory = factory;
        Config.addEntityManagerFactory(factory);
        this.counterOfIds = new AtomicInteger(countSongLists());
    }

    @Override
    public int add(SongList songList) {
        // isPublic is required
        if (songList.getIsPublic() == null) {
            throw new NullPointerException();
        }

        songList.setId(counterOfIds.incrementAndGet());

        EntityManager em = factory.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.persist(songList);
            transaction.commit();
            return songList.getId();
        } catch (Exception e) {
            LOGGER.log(SEVERE, "Error adding song list: {0}", e.getMessage());
            transaction.rollback();
            counterOfIds.decrementAndGet();
            throw new PersistenceException("Could not persist entity: " + e.toString());
        } finally {
            em.close();
        }
    }

    @Override
    public void remove(Integer id) {
        EntityManager em = factory.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        SongList songList;
        try {
            songList = em.find(SongList.class, id);
            if (songList != null) {
                LOGGER.log(INFO, "Deleting: " + songList.getId());
                transaction.begin();
                em.remove(songList);
                transaction.commit();
            }
        } catch (Exception e) {
            LOGGER.log(SEVERE, "Error removing song list: {0}", e.getMessage());
            transaction.rollback();
            throw new PersistenceException("Could not remove entity: " + e.toString());
        } finally {
            em.close();
        }
    }

    @Override
    public List<SongList> getAllSongLists() {
        EntityManager em = factory.createEntityManager();
        try {
            TypedQuery<SongList> query = em.createQuery("SELECT s FROM SongList s", SongList.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<SongList> getSongListsFromUser(String userId, boolean privateIsOk) {
        EntityManager em = factory.createEntityManager();
        try {
            if (privateIsOk) {
                return em
                        .createQuery("SELECT s FROM SongList s WHERE s.owner.userId = :userId", SongList.class)
                        .setParameter("userId", userId)
                        .getResultList();
            } else {
                return em
                        .createQuery("SELECT s FROM SongList s WHERE s.owner.userId = :userId AND isPublic = true", SongList.class)
                        .setParameter("userId", userId)
                        .getResultList();
            }
        } catch (Exception e) {
            return Collections.emptyList();
        } finally {
            em.close();
        }
    }

    @Override
    public SongList getSingleSongList(int id) {
        EntityManager em = factory.createEntityManager();
        SongList entity;
        try {
            entity = em.find(SongList.class, id);
        } finally {
            em.close();
        }
        return entity;
    }

    private Integer countSongLists() {
        return getAllSongLists().size();
    }

}
