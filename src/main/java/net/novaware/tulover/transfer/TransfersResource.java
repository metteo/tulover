package net.novaware.tulover.transfer;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("transfers")
public class TransfersResource {

  @GET
  @Produces("application/vnd.novaware.tulover.transfer.v1+json;charset=utf8")
  public String get() {
    return "so cool";
  }
}
