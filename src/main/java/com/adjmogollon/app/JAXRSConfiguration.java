package com.adjmogollon.app;

import javax.ws.rs.ApplicationPath;
import org.glassfish.jersey.server.ResourceConfig;

/**
 * Configures JAX-RS for the application.
 * @author anibal.mogollon
 */
@ApplicationPath("resources")
public class JAXRSConfiguration extends ResourceConfig {

    public JAXRSConfiguration() {
        packages("com.adjmogollon.app.resources");
    }

}


