package de.htw.ai.kbe.songsjpa.config;

import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: Donat Broszka
 */
@WebListener
public class Config implements ServletContextListener {

    private static List<EntityManagerFactory> emfs = new ArrayList<>();

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // not used.
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        for (EntityManagerFactory emf : emfs) {
            emf.close();
        }
    }

    public static void addEntityManagerFactory(EntityManagerFactory emf) {
        emfs.add(emf);
    }

}
