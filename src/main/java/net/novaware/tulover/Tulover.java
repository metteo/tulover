package net.novaware.tulover;

import static org.eclipse.jetty.servlet.ServletContextHandler.NO_SECURITY;
import static org.eclipse.jetty.servlet.ServletContextHandler.NO_SESSIONS;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;

import net.novaware.tulover.api.RootResource;

public class Tulover {

  private static final Logger logger = Logger.getLogger("Tulover");
  
  public static final int DEFAULT_PORT = 8080;
  
  private Server server;
  
  public Tulover start(int port) {
    ServletContextHandler servletContext = new ServletContextHandler(NO_SESSIONS | NO_SECURITY);
    servletContext.setContextPath("/");

    ServletHolder jerseyServlet = new ServletHolder(new ServletContainer(new TuloverConfig()));
    servletContext.addServlet(jerseyServlet, "/" + RootResource.PATH + "/*");
    
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
    int port = Integer.getInteger("port", DEFAULT_PORT);
    new Tulover().start(port).join();
  }
}
