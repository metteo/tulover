package net.novaware.tulover;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.logging.LoggingFeature;
import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("/resources")
public class TuloverConfig extends ResourceConfig {

  public TuloverConfig() {
    packages("net.novaware.tulover");
    register(new TuloverBinder());

    Logger logger = Logger.getLogger("LoggingFeature");
    register(new LoggingFeature(logger, Level.INFO, LoggingFeature.Verbosity.PAYLOAD_ANY,
        LoggingFeature.DEFAULT_MAX_ENTITY_SIZE));
  }

}
