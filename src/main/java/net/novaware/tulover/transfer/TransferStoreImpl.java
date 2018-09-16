package net.novaware.tulover.transfer;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.annotation.concurrent.ThreadSafe;

import net.novaware.tulover.util.AbstractObjectStore;

@ThreadSafe
public class TransferStoreImpl extends AbstractObjectStore<TransferEntity, UUID> implements TransferStore {

  @Override
  protected TransferEntity clone(TransferEntity object) {
    return object.clone();
  }
  
  @Override
  public TransferEntity create(TransferEntity object) {
    assert object != null : "object should not be null";

    TransferEntity forStorage = object.clone();

    main.writeLock().lock();

    try {
      storage.put(forStorage.getId(), forStorage);
    } finally {
      main.writeLock().unlock();
    }

    return forStorage.clone();
  }

  @Override
  public List<TransferEntity> queryAll() {
    main.readLock().lock();

    List<TransferEntity> entities = null;
    try {
      entities = storage.values().stream().collect(Collectors.toList());
    } finally {
      main.readLock().unlock();
    }

    return entities;
  }

}
