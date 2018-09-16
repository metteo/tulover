package net.novaware.tulover.transfer;

import java.math.BigDecimal;
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
public class TransferStoreImpl extends AbstractObjectStore<TransferEntity, UUID> implements TransferStore {

  @GuardedBy("main")
  private Map<UUID, List<TransferEntity>> byAccountIndex;

  @GuardedBy("main")
  private Map<UUID, BigDecimal> accountBalanceView;

  public TransferStoreImpl() {
    super();

    byAccountIndex = new HashMap<>();
    accountBalanceView = new HashMap<>();
  }

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

      for (SplitEntity split : forStorage.getSplits()) {
        UUID account = split.getAccount();

        // update byAccountIndex
        List<TransferEntity> byAccount = byAccountIndex.get(account);
        if (byAccount == null) {
          byAccount = new ArrayList<>();
          byAccountIndex.put(account, byAccount);
        }
        byAccount.add(forStorage);

        // update accountBalanceView
        BigDecimal balance = accountBalanceView.get(account);
        if (balance == null) {
          balance = BigDecimal.ZERO;
        }
        balance = balance.add(split.getAmount());
        accountBalanceView.put(account, balance);
      }
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

  @Override
  public List<TransferEntity> queryBy(UUID account) {
    main.readLock().lock();

    List<TransferEntity> entities = null;
    try {
      List<TransferEntity> fromStorage = byAccountIndex.get(account);
      if (fromStorage == null) {
        entities = new ArrayList<>();
      } else {
        entities = fromStorage.stream().collect(Collectors.toList());
      }
    } finally {
      main.readLock().unlock();
    }

    return entities;
  }

  @Override
  public BigDecimal getBalance(UUID account) {
    main.readLock().lock();

    BigDecimal balance = null;
    try {
      balance = accountBalanceView.get(account);
    } finally {
      main.readLock().unlock();
    }

    return balance;
  }

}
