package net.novaware.tulover.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Path("accounts")
public class AccountsResource {

  @GET
  @Produces("application/vnd.novaware.tulover.account.v1+json;charset=utf8")
  public Response queryBy(@QueryParam("owner") String owner) {
    if (owner == null) {
      return Response.noContent().build();
    }

    Account account = new Account();
    account.owner = owner;
    account.number = "1234";

    return Response.ok(account).build(); // TODO: list!
  }

  @GET
  @Path("/{number}")
  @Produces("application/vnd.novaware.tulover.account.v1+json;charset=utf8")
  public Response get(@PathParam("number") String number, @QueryParam("fields") String fields) {
    if (number == null) {
      return Response.status(Status.NOT_FOUND).build();
    }

    Account account = new Account();
    account.owner = "unknown";
    account.number = number;

    return Response.ok(account).build();
  }

  @GET
  @Path("/{number}/transfers")
  @Produces("application/vnd.novaware.tulover.transfer.v1+json;charset=utf8")
  public Response getTransfers(@PathParam("number") String number) {
    if (number == null) {
      return Response.status(Status.NOT_FOUND).build();
    }

    Account account = new Account();
    account.owner = "unknown";
    account.number = number;

    return Response.ok(account).build();
  }
}
