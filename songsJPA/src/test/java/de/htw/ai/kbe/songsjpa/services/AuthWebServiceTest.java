package de.htw.ai.kbe.songsjpa.services;

import de.htw.ai.kbe.songsjpa.storage.IUserStorage;
import de.htw.ai.kbe.songsjpa.storage.InMemoryUserStorage;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Assert;
import org.junit.Test;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;


public class AuthWebServiceTest extends JerseyTest {

    @Override
    protected Application configure() {
        return new ResourceConfig(AuthWebService.class).register(
                new AbstractBinder() {
                    @Override
                    protected void configure() {
                        bind(new InMemoryUserStorage("extern/test/users.json")).to(IUserStorage.class);
                    }
                });
    }


    @Test
    public void getAuthTokenHappyCaseNotImplementedDummy200() {
        // When
        Response response = target("/auth")
                .queryParam("userId", "mmuster")
                .request()
                .get();

        // Then
        Assert.assertEquals(200, response.getStatus());
    }

    @Test
    public void getAuthTokenNotExistingUser403() {
        // When
        Response response = target("/auth")
                .queryParam("userId", "notexistinguser")
                .request()
                .get();

        // Then
        Assert.assertEquals(403, response.getStatus());
    }

    @Test
    public void getAuthTokenWrongQuery400() {
        // When
        Response response = target("/auth")
                .queryParam("sdfiqjfvcf")
                .request()
                .get();

        // Then
        Assert.assertEquals(400, response.getStatus());
    }

    @Test
    public void getAuthTokenNoQuery400() {
        // When
        Response response = target("/auth")
                .request()
                .get();

        // Then
        Assert.assertEquals(400, response.getStatus());
    }

    @Test
    public void getAuthTokenNoParam403() {
        // When
        Response response = target("/auth")
                .queryParam("userId", "")
                .request()
                .get();

        // Then
        Assert.assertEquals(403, response.getStatus());
    }
}