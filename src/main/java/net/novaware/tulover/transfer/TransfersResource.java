package net.novaware.tulover.transfer;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

public interface TransfersResource {
  
  @POST
  @Consumes(Transfer.MEDIA_TYPE_JSON_UTF8)
  @Produces(Transfer.MEDIA_TYPE_JSON_UTF8)
  Response create(Transfer prototype);

  /**
   * Debug endpoint to view the general ledger
   * @return
   */
  @GET
  @Produces(Transfer.MEDIA_TYPE_JSON_UTF8)
  Response getAll();
  
  @GET
  @Path("/{id}")
  @Produces(Transfer.MEDIA_TYPE_JSON_UTF8)
  Response get(@PathParam("id") String id);
}
