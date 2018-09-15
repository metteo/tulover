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
    return null;
  }

  @Override
  public Response get(String number, List<String> fields) {
    return null;
  }

  @Override
  public Response getTransfers(String number) {
    return null;
  }
}
