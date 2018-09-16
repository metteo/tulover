package net.novaware.tulover;

import static org.eclipse.jetty.servlet.ServletContextHandler.NO_SECURITY;
import static org.eclipse.jetty.servlet.ServletContextHandler.NO_SESSIONS;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;

public class Tulover {

  private static final Logger logger = Logger.getLogger("Tulover");
  
  private Server server;
  
  public Tulover start() {
    ServletContextHandler servletContext = new ServletContextHandler(NO_SESSIONS | NO_SECURITY);
    servletContext.setContextPath("/");

    ServletHolder jerseyServlet = new ServletHolder(new ServletContainer(new TuloverConfig()));
    servletContext.addServlet(jerseyServlet, "/resources/*");

    Integer port = Integer.getInteger("port", 8080);
    
    server = new Server(port);
    server.setHandler(servletContext);

    try {
      server.start();
    } catch (Exception ex) {
      server = null;
      logger.log(Level.SEVERE, "Unable to start the server: ", ex);
    }
    
    return this;
  }
  
  public void join() {
    try {
      if(server != null) {
        server.join();
      }
    } catch (InterruptedException e) {
      logger.log(Level.WARNING, "Interrupted while joining: ", e);
    }
  }
  
  public void stop() {
    if(server != null) {
      try {
        server.stop();
      } catch (Exception e) {
        logger.log(Level.WARNING, "Error while stopping: ", e);
      }
    }
  }
  
  public static void main(String[] args) {
    new Tulover().start().join();
  }
}
