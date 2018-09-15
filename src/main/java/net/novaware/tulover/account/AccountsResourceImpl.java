package net.novaware.tulover.account;

import java.net.URI;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.Status;

@Path("accounts")
public class AccountsResourceImpl implements AccountsResource {

  private AccountRepository repository;
  private AccountValidator validator;
  private UriInfo uriInfo;

  @Inject
  public AccountsResourceImpl(AccountRepository repository, AccountValidator validator, UriInfo uriInfo) {
    this.repository = repository;
    this.validator = validator;
    this.uriInfo = uriInfo;
  }

  @Override
  public Response create(Account prototype) {
    if (!validator.isValid(prototype)) {
      return Response.status(Status.BAD_REQUEST).build();
    }
    
    Account created = repository.create(prototype); //TODO: handle throw case if needed
    assert created != null : "repo should return or throw";
    
    URI link = uriInfo.getAbsolutePathBuilder().path(created.getNumber()).build();

    return Response.created(link).entity(created).build();
  }

  @Override
  public Response queryBy(String owner) {
    if (owner == null || owner.isEmpty()) {
      return Response.status(Status.BAD_REQUEST).build();
    }
    
    List<Account> accounts = repository.queryBy(owner);
    assert accounts != null : "account list should be empty if no results";
    
    return Response.ok(accounts).build();
  }

  @Override
  public Response get(String number, List<String> fields) {
    assert number != null && !number.isEmpty() : "number should be given";
    
    boolean withBalance = false;
    if (fields != null && !fields.isEmpty()) {
      withBalance = fields.contains("balance");
    }
    
    Account account = repository.get(number, withBalance);
    
    if(account == null) {
      return Response.status(Status.NOT_FOUND).build();
    }
    
    return Response.ok(account).build();
  }

  @Override
  public Response getTransfers(String number) {
    return Response.status(Status.NOT_IMPLEMENTED).build();
  }
}
