package net.novaware.tulover.util;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.annotation.concurrent.GuardedBy;

public abstract class AbstractObjectStore<T, I> implements ObjectStore<T, I> {

  protected ReadWriteLock main;
  
  @GuardedBy("main")
  protected Map<I, T> storage;
  
  protected AbstractObjectStore() {
    main = new ReentrantReadWriteLock(true);

    storage = new LinkedHashMap<>(); // so transfers are in insert order
  }
  
  protected abstract T clone(T object);
  
  @Override
  public T get(I id) {
    assert id != null : "id should not be null";

    main.readLock().lock();

    T entity = null;
    try {
      T fromStorage = storage.get(id);
      if (fromStorage != null) {
        entity = clone(fromStorage);
      }
    } finally {
      main.readLock().unlock();
    }

    return entity;
  }
}
