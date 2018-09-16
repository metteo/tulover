package net.novaware.tulover.account;

import java.net.URI;
import java.util.List;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.Status;

import net.novaware.tulover.util.ItemHolder;

@Path("accounts")
public class AccountsResourceImpl implements AccountsResource {
  
  private static final Logger logger = Logger.getLogger("AccountsResourceImpl");

  private AccountService service;
  private AccountValidator validator;
  private UriInfo uriInfo;

  @Inject
  public AccountsResourceImpl(AccountService service, AccountValidator validator, UriInfo uriInfo) {
    this.service = service;
    this.validator = validator;
    this.uriInfo = uriInfo;
  }

  @Override
  public Response create(Account prototype) {
    logger.info(() -> "create: " + prototype);
    
    if (!validator.isValidPrototype(prototype)) {
      return Response.status(Status.BAD_REQUEST).build();
    }
    
    Account created = service.create(prototype); //TODO: handle throw case if needed
    assert created != null : "service should return or throw";
    
    URI link = uriInfo.getAbsolutePathBuilder().path(created.getNumber()).build();

    return Response.created(link).entity(created).build();
  }

  @Override
  public Response queryBy(String owner) {
    logger.info(() -> "queryBy: " + owner);
    
    if (owner == null || owner.isEmpty()) {
      return Response.status(Status.BAD_REQUEST).build();
    }
    
    List<Account> accounts = service.queryBy(owner);
    assert accounts != null : "account list should be empty if no results";
    
    return Response.ok(new ItemHolder<>(accounts)).build();
  }

  @Override
  public Response get(String number, List<String> fields) {
    logger.info(() -> "get: " + number + ", with: " + fields);
    
    assert number != null && !number.isEmpty() : "number should be given";
    
    boolean withBalance = false;
    if (fields != null && !fields.isEmpty()) {
      withBalance = fields.contains("balance");
    }
    
    Account account = service.get(number, withBalance);
    
    if(account == null) {
      return Response.status(Status.NOT_FOUND).build();
    }
    
    return Response.ok(account).build();
  }

  @Override
  public Response getTransfers(String number) {
    logger.info(() -> "getTransfers: " + number);
    return Response.status(Status.NOT_IMPLEMENTED).build();
  }
}
