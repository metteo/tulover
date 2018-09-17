package net.novaware.tulover.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

public interface TransfersResource {

  String PATH = "transfers";
  
  // query params
  String ACCOUNT = "account";

  @POST
  @Consumes(Transfer.MEDIA_TYPE_JSON_UTF8)
  @Produces(Transfer.MEDIA_TYPE_JSON_UTF8)
  Response create(Transfer prototype);

  @GET
  @Produces(Transfer.MEDIA_TYPE_JSON_UTF8)
  Response queryBy(@QueryParam(ACCOUNT) String account);

  @GET
  @Path("/{id}")
  @Produces(Transfer.MEDIA_TYPE_JSON_UTF8)
  Response get(@PathParam("id") String id);
}
