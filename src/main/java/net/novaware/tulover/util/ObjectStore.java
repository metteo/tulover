package net.novaware.tulover.util;

/**
 * @param <T> main type
 * @param <I> id type
 */
public interface ObjectStore<T, I> {

  T get(I id);
}
