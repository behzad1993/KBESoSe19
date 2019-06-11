package de.htw.ai.kbe.songsrx.depinjection;

import de.htw.ai.kbe.songsrx.storage.ISongStorage;
import de.htw.ai.kbe.songsrx.storage.IUserStorage;
import de.htw.ai.kbe.songsrx.storage.InMemorySongStorage;
import de.htw.ai.kbe.songsrx.storage.InMemoryUserStorage;
import org.glassfish.hk2.utilities.binding.AbstractBinder;


public class DependencyBinder extends AbstractBinder {
    @Override
    protected void configure() {
        bind(new InMemorySongStorage("/Users/behzadkarimi/Documents/HTW/Komponentenbasierte_Entwicklung/KBESoSe19/songsRX/extern/songs.xml")).to(ISongStorage.class); // scope: Singleton.class
//        bind(new InMemorySongStorage("/Users/behzadkarimi/Documents/HTW/Komponentenbasierte_Entwicklung/KBESoSe19/songsServlet/extern/songs.xml")).to(ISongStorage.class); // scope: Singleton.class
        bind(new InMemoryUserStorage("/Users/behzadkarimi/Documents/HTW/Komponentenbasierte_Entwicklung/KBESoSe19/songsRX/extern/test/users.json")).to(IUserStorage.class); // scope: Singleton.class
    }
}