package net.novaware.tulover.resource;

import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Path("accounts")
public class AccountsResourceImpl implements AccountsResource {

  public Response queryBy(String owner) {
    if (owner == null) {
      return Response.noContent().build();
    }

    Account account = new Account();
    account.owner = owner;
    account.number = "1234";

    return Response.ok(account).build(); // TODO: list!
  }

  public Response get(String number, String fields) {
    if (number == null) {
      return Response.status(Status.NOT_FOUND).build();
    }

    Account account = new Account();
    account.owner = "unknown";
    account.number = number;

    return Response.ok(account).build();
  }

  public Response getTransfers(String number) {
    if (number == null) {
      return Response.status(Status.NOT_FOUND).build();
    }

    Account account = new Account();
    account.owner = "unknown";
    account.number = number;

    return Response.ok(account).build();
  }
}
