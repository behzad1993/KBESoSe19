package de.htw.ai.kbe.songsjpa.config;

import de.htw.ai.kbe.songsjpa.authentication.AuthenticationFilter;
import de.htw.ai.kbe.songsjpa.depinjection.DependencyBinder;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;

@ApplicationPath("/rest")
public class SongsJPA extends ResourceConfig {
    public SongsJPA() {
        register(new DependencyBinder());
        register(new AuthenticationFilter());
        packages("de.htw.ai.kbe.songsjpa.services");
    }
}