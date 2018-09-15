package net.novaware.tulover.transfer;

import javax.inject.Inject;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Path("transfers")
public class TransfersResourceImpl implements TransfersResource {

  private TransferValidator validator;
  
  @Inject
  public TransfersResourceImpl(TransferValidator validator) {
    this.validator = validator;
  }
  
  @Override
  public Response create(Transfer prototype) {
    if(!validator.isValidPrototype(prototype)) {
      return Response.status(Status.BAD_REQUEST).build();
    }
    
    return null;
  }

  @Override
  public Response getAll() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Response get(String id) {
    // TODO Auto-generated method stub
    return null;
  }

}
