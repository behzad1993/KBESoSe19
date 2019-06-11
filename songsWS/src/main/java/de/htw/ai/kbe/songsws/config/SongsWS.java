package de.htw.ai.kbe.songsws.config;

import de.htw.ai.kbe.songsws.authentication.AuthenticationFilter;
import de.htw.ai.kbe.songsws.depinjection.DependencyBinder;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;

@ApplicationPath("/rest")
public class SongsWS extends ResourceConfig {
    public SongsWS() {
        register(new DependencyBinder());
        register(new AuthenticationFilter());
        packages("de.htw.ai.kbe.songsws.services");
    }
}