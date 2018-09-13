package net.novaware.tulover;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.servlet.ServletContainer;

import static org.eclipse.jetty.servlet.ServletContextHandler.*;

public class Tulover implements Runnable {

  private static final Logger logger = Logger.getLogger("Tulover");

  public static void main(String[] args) {
    new Tulover().run();
  }

  @Override
  public void run() {
    ServletContextHandler servletContext = new ServletContextHandler(NO_SESSIONS | NO_SECURITY);
    servletContext.setContextPath("/");

    ServletHolder jerseyServlet = servletContext.addServlet(ServletContainer.class, "/resources/*");
    jerseyServlet.setInitParameter(ServerProperties.PROVIDER_PACKAGES, "net.novaware.tulover");

    Server server = new Server(8080);
    server.setHandler(servletContext);

    try {
      server.start();
      server.join();
    } catch (InterruptedException ex) {
      Thread.currentThread().interrupt();
      logger.info("Interrupted. Stopping the server");
    } catch (Exception ex) {
      logger.log(Level.SEVERE, "Unable to start the server: ", ex);
    } finally {
      server.destroy();
    }
  }
}
