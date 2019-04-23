package de.htw.ai.kbe.songsrx.config;

import de.htw.ai.kbe.songsrx.authentication.AuthenticationFilter;
import de.htw.ai.kbe.songsrx.depinjection.DependencyBinder;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;

@ApplicationPath("/rest")
public class SongsRX extends ResourceConfig {
    public SongsRX() {
        register(new DependencyBinder());
        register(new AuthenticationFilter());
        packages("de.htw.ai.kbe.songsrx.services");
    }
}