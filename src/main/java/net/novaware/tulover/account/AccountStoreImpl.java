package net.novaware.tulover.account;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.annotation.concurrent.GuardedBy;
import javax.annotation.concurrent.ThreadSafe;

import net.novaware.tulover.util.AbstractObjectStore;

@ThreadSafe
public class AccountStoreImpl extends AbstractObjectStore<AccountEntity, UUID> implements AccountStore {

  @GuardedBy("main")
  private Map<String, List<AccountEntity>> byOwnerIndex;

  public AccountStoreImpl() {
    super();
    
    byOwnerIndex = new HashMap<>();
  }
  
  @Override
  protected AccountEntity clone(AccountEntity object) {
    return object.clone();
  }

  @Override
  public AccountEntity create(AccountEntity object) {
    assert object != null : "object should not be null";

    AccountEntity forStorage = object.clone();

    main.writeLock().lock();

    try {
      storage.put(forStorage.getNumber(), forStorage);

      String owner = forStorage.getOwner();
      List<AccountEntity> byOwner = byOwnerIndex.get(owner);
      if (byOwner == null) {
        byOwner = new ArrayList<>();
        byOwnerIndex.put(owner, byOwner);
      }
      byOwner.add(forStorage);
    } finally {
      main.writeLock().unlock();
    }

    return forStorage.clone();
  }

  @Override
  public List<AccountEntity> queryBy(String owner) {
    assert owner != null : "owner should not be null";

    main.readLock().lock();

    List<AccountEntity> entities = null;
    try {
      List<AccountEntity> fromStorage = byOwnerIndex.get(owner);
      if (fromStorage != null) {
        entities = fromStorage.stream().map(AccountEntity::clone).collect(Collectors.toList());
      } else {
        entities = new ArrayList<>();
      }
    } finally {
      main.readLock().unlock();
    }

    return entities;
  }

}
