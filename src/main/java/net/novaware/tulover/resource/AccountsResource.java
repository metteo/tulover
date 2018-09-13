package net.novaware.tulover.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

public interface AccountsResource {

  @GET
  @Produces("application/vnd.novaware.tulover.account.v1+json;charset=utf8")
  Response queryBy(@QueryParam("owner") String owner);

  @GET
  @Path("/{number}")
  @Produces("application/vnd.novaware.tulover.account.v1+json;charset=utf8")
  Response get(@PathParam("number") String number, @QueryParam("fields") String fields);

  @GET
  @Path("/{number}/transfers")
  @Produces("application/vnd.novaware.tulover.transfer.v1+json;charset=utf8")
  Response getTransfers(@PathParam("number") String number);
}
