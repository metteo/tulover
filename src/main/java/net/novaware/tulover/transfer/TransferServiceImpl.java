package net.novaware.tulover.transfer;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.inject.Inject;

public class TransferServiceImpl implements TransferService {
  
  private TransferStore store;
  private TransferMapper mapper;

  @Inject
  public TransferServiceImpl(TransferStore store, TransferMapper mapper) {
    this.store = store;
    this.mapper = mapper;
  }

  @Override
  public Transfer create(Transfer prototype) {
    assert prototype != null : "transfer should not be null";
    
    //TODO: validate from and to exist and same currency
    //TODO: validate amount against above currency (num of decimal places etc)
    
    TransferEntity transfer = mapper.toTransferEntity(prototype);
    transfer.setId(UUID.randomUUID());
    transfer.setCreatedAt(LocalDateTime.now());

    TransferEntity created = store.create(transfer);
    
    return mapper.toTransfer(created);
  }

  @Override
  public List<Transfer> queryAll() {
    return store.queryAll().stream().map(mapper::toTransfer).collect(Collectors.toList());
  }

  @Override
  public Transfer get(String id) {
    if (id == null) {
      return null;
    }
    
    TransferEntity entity = store.get(UUID.fromString(id));
    return mapper.toTransfer(entity);
  }

  @Override
  public List<Transfer> queryBy(String account) {
    return store.queryBy(UUID.fromString(account)).stream().map(mapper::toTransfer).collect(Collectors.toList());
  }

}
