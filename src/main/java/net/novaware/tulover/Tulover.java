package net.novaware.tulover;

import static org.eclipse.jetty.servlet.ServletContextHandler.NO_SECURITY;
import static org.eclipse.jetty.servlet.ServletContextHandler.NO_SESSIONS;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;

public class Tulover implements Runnable {

  private static final Logger logger = Logger.getLogger("Tulover");
  
  @Override
  public void run() {
    ServletContextHandler servletContext = new ServletContextHandler(NO_SESSIONS | NO_SECURITY);
    servletContext.setContextPath("/");

    ServletHolder jerseyServlet = new ServletHolder(new ServletContainer(new TuloverConfig()));
    servletContext.addServlet(jerseyServlet, "/resources/*");

    Integer port = Integer.getInteger("port", 8080);
    
    Server server = new Server(port);
    server.setHandler(servletContext);

    try {
      server.start();
      server.join(); //TODO: notify waiting tests before joining
    } catch (InterruptedException ex) {
      Thread.currentThread().interrupt();
      logger.info("Interrupted. Stopping the server");
    } catch (Exception ex) {
      logger.log(Level.SEVERE, "Unable to start the server: ", ex);
    }
  }
  
  public static void main(String[] args) {
    new Tulover().run();
  }
}
