package net.novaware.tulover.account;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

import javax.annotation.concurrent.GuardedBy;
import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
public class AccountStoreImpl implements AccountStore {

  private ReadWriteLock main;

  @GuardedBy("main")
  private Map<UUID, AccountEntity> storage;

  @GuardedBy("main")
  private Map<String, List<AccountEntity>> byOwnerIndex;

  public AccountStoreImpl() {
    main = new ReentrantReadWriteLock(true);

    storage = new HashMap<>();
    byOwnerIndex = new HashMap<>();
  }

  @Override
  public AccountEntity create(AccountEntity object) {
    assert object != null : "object should not be null";

    AccountEntity forStorage = object.clone();

    // do processing here if needed

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
  public AccountEntity get(UUID number) {
    assert number != null : "number should not be null";

    main.readLock().lock();

    AccountEntity entity = null;
    try {
      AccountEntity fromStorage = storage.get(number);
      if (fromStorage != null) {
        entity = fromStorage.clone();
      }
    } finally {
      main.readLock().unlock();
    }

    return entity;
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
      }
    } finally {
      main.readLock().unlock();
    }

    return entities;
  }

}
