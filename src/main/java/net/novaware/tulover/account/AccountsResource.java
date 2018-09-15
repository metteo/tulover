package net.novaware.tulover.account;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import net.novaware.tulover.transfer.Transfer;

public interface AccountsResource {

  @POST
  @Consumes(Account.MEDIA_TYPE_JSON_UTF8)
  @Produces(Account.MEDIA_TYPE_JSON_UTF8)
  Response create(Account prototype);
  
  @GET
  @Produces(Account.MEDIA_TYPE_JSON_UTF8)
  Response queryBy(@QueryParam("owner") String owner);
  
  @GET
  @Path("/{number}")
  @Produces(Account.MEDIA_TYPE_JSON_UTF8)
  Response get(@PathParam("number") String number, @QueryParam("fields") List<String> fields);
  
  @GET
  @Path("/{number}/transfers")
  @Produces(Transfer.MEDIA_TYPE_JSON_UTF8)
  Response getTransfers(@PathParam("number") String number);
}
