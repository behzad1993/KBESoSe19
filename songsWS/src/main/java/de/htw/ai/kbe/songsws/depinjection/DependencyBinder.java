package de.htw.ai.kbe.songsws.depinjection;

import de.htw.ai.kbe.songsws.storage.ISongListStorage;
import de.htw.ai.kbe.songsws.storage.ISongStorage;
import de.htw.ai.kbe.songsws.storage.IUserStorage;
import de.htw.ai.kbe.songsws.storage.PostgresSongListStorage;
import de.htw.ai.kbe.songsws.storage.PostgresSongStorage;
import de.htw.ai.kbe.songsws.storage.PostgresUserStorage;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

import javax.inject.Singleton;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public class DependencyBinder extends AbstractBinder {
    @Override
    protected void configure() {
        bind(Persistence
                .createEntityManagerFactory("songDB-PU"))
                .to(EntityManagerFactory.class);
        bind(PostgresSongStorage.class).to(ISongStorage.class).in(Singleton.class);
        bind(PostgresUserStorage.class).to(IUserStorage.class).in(Singleton.class);
        bind(PostgresSongListStorage.class).to(ISongListStorage.class).in(Singleton.class);
    }
}