package net.novaware.tulover.account;

import java.math.BigDecimal;
import java.util.List;

import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.mapstruct.factory.Mappers;

@Path("accounts")
public class AccountsResourceImpl implements AccountsResource {

  public Response queryBy(String owner) {
    if (owner == null) {
      return Response.noContent().build();
    }

    Account account = new Account();
    // account.owner = owner;
    // account.number = "1234";

    return Response.ok(account).build(); // TODO: list!
  }

  public Response get(String number, List<String> fields) {
    if (number == null) {
      return Response.status(Status.NOT_FOUND).build();
    }

    AccountEntity account = new AccountStoreImpl().get(number);
    Account a = Mappers.getMapper(AccountMapper.class).toAccount(account);

    if (fields != null && fields.contains("balance")) {
      a.setBalance(BigDecimal.ZERO);
    }

    return Response.ok(a).build();
  }

  public Response getTransfers(String number) {
    if (number == null) {
      return Response.status(Status.NOT_FOUND).build();
    }

    Account account = new Account();
    // account.owner = "unknown";
    // account.number = number;

    return Response.ok(account).build();
  }
}
