package net.novaware.tulover.transfer;

import java.net.URI;
import java.util.List;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import net.novaware.tulover.util.ItemHolder;

@Path("transfers")
public class TransfersResourceImpl implements TransfersResource {
  
  private static final Logger logger = Logger.getLogger("TransfersResourceImpl");

  private TransferService service;
  private TransferValidator validator;
  private UriInfo uriInfo;
  
  @Inject
  public TransfersResourceImpl(TransferService service, TransferValidator validator, UriInfo uriInfo) {
    this.service = service;
    this.validator = validator;
    this.uriInfo = uriInfo;
  }
  
  @Override
  public Response create(Transfer prototype) {
    if(!validator.isValidPrototype(prototype)) {
      return Response.status(Status.BAD_REQUEST).build();
    }
    
    Transfer created = service.create(prototype);
    assert created != null : "service should return or throw";
    
    URI link = uriInfo.getAbsolutePathBuilder().path(created.getId()).build();
    
    return Response.created(link).entity(created).build();
  }

  @Override
  public Response queryBy(String account) {
    
    
    List<Transfer> transfers;
    if (account == null || account.isEmpty()) {
      logger.severe("queryAll endpoint is only for debugging !!1one");
      transfers = service.queryAll();
    } else {
      transfers = service.queryBy(account);
    }
    
    assert transfers != null : "should return empty list or throw";
    
    return Response.ok(new ItemHolder<>(transfers)).build();
  }

  @Override
  public Response get(String id) {
    assert id != null && !id.isEmpty() : "id should be given";
    
    Transfer transfer = service.get(id);
    
    if(transfer == null) {
      return Response.status(Status.NOT_FOUND).build();
    }
    
    return Response.ok(transfer).build();
  }

}
