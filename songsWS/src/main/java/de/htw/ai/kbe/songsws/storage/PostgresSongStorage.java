package de.htw.ai.kbe.songsws.storage;

import de.htw.ai.kbe.songsws.config.Config;
import de.htw.ai.kbe.songsws.pojos.Song;

import javax.inject.Inject;
import javax.persistence.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

import static java.util.logging.Level.INFO;
import static java.util.logging.Level.SEVERE;


public class PostgresSongStorage implements ISongStorage {

    private static final Logger LOGGER = Logger.getLogger(PostgresSongStorage.class.getName());

    private EntityManagerFactory factory;
    private AtomicInteger counterOfIds;

    @Inject
    public PostgresSongStorage(EntityManagerFactory factory) {
        this.factory = factory;
        Config.addEntityManagerFactory(factory);
        this.counterOfIds = new AtomicInteger(countAllSongs());
    }

    @Override
    public int add(Song song) {
        // title is required
        if (song.getTitle() == null || song.getTitle().isEmpty()) {
            throw new NullPointerException();
        }
        song.setId(counterOfIds.incrementAndGet());

        EntityManager em = factory.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.persist(song);
            transaction.commit();
            return song.getId();
        } catch (Exception e) {
            LOGGER.log(SEVERE, "Error adding song: {0}", e.getMessage());
            transaction.rollback();
            counterOfIds.decrementAndGet();
            throw new PersistenceException("Could not persist entity: " + e.toString());
        } finally {
            em.close();
        }
    }

    @Override
    public List<Song> getAllSongs() {
        EntityManager em = factory.createEntityManager();
        try {
            TypedQuery<Song> query = em.createQuery("SELECT s FROM Song s", Song.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public boolean updateSong(Integer id, Song newSong) {
        if (!id.equals(newSong.getId())) {
            return false;
        }

        // title is required
        if (newSong.getTitle() == null || newSong.getTitle().isEmpty()) {
            throw new NullPointerException();
        }

        // update == 1.) delete and 2.) add
        if (!deleteSong(id)) {
            return false;
        }

        // add newSong with known id instead of using "counterOfIds"
        EntityManager em = factory.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.persist(newSong);
            transaction.commit();
            return true;
        } catch (Exception e) {
            LOGGER.log(SEVERE, "Error adding new song: {0}", e.getMessage());
            transaction.rollback();
            throw new PersistenceException("Could not persist entity: " + e.toString());
        } finally {
            em.close();
        }
    }

    @Override
    public Song getSingleSong(int id) {
        EntityManager em = factory.createEntityManager();
        Song entity;
        try {
            entity = em.find(Song.class, id);
        } finally {
            em.close();
        }
        return entity;
    }

    @Override
    public boolean songExistsInSongStorage(int id) {
        return getSingleSong(id) != null;
    }

    private Integer countAllSongs() {
        return getAllSongs().size();
    }

    public Boolean deleteSong(Integer id) {
        EntityManager em = factory.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        Song song;
        try {
            song = em.find(Song.class, id);
            if (song != null) {
                LOGGER.log(INFO, "Deleting: " + song.getId() + " with title: " + song.getTitle());
                transaction.begin();
                em.remove(song);
                transaction.commit();
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            LOGGER.log(SEVERE, "Error removing song: {0}", e.getMessage());
            transaction.rollback();
            throw new PersistenceException("Could not remove entity: " + e.toString());
        } finally {
            em.close();
        }
    }
}
