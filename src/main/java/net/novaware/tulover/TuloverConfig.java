package net.novaware.tulover;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("/resources")
public class TuloverConfig extends ResourceConfig {

  public TuloverConfig() {
    packages("net.novaware.tulover");
    register(new TuloverBinder());
  }
  
}
