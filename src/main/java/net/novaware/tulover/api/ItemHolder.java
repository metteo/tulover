package net.novaware.tulover.api;

import java.beans.ConstructorProperties;
import java.util.List;

public class ItemHolder<T> {

  public final int count;
  public final List<T> items;
  
  @ConstructorProperties({"items"})
  public ItemHolder(List<T> items) {
    this.count = items == null ? 0 : items.size();
    this.items = items;
  }
}
