package net.novaware.tulover.transfer;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Transfer representation (a structure, not action)
 */
public class TransferEntity implements Cloneable {

  private UUID id;
  private LocalDateTime createdAt;

  private List<SplitEntity> splits;

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public List<SplitEntity> getSplits() {
    return splits;
  }

  public void setSplits(List<SplitEntity> splits) {
    this.splits = splits;
  }

  @Override
  public TransferEntity clone() {
    try {
      TransferEntity clone = (TransferEntity) super.clone();

      //splits are mutable
      if (this.splits != null) {
        clone.splits = this.splits.stream().map(SplitEntity::clone).collect(Collectors.toList());
      }

      return clone;
    } catch (CloneNotSupportedException e) {
      throw new AssertionError("should implement Cloneable", e);
    }
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((createdAt == null) ? 0 : createdAt.hashCode());
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime * result + ((splits == null) ? 0 : splits.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    TransferEntity other = (TransferEntity) obj;
    if (createdAt == null) {
      if (other.createdAt != null)
        return false;
    } else if (!createdAt.equals(other.createdAt))
      return false;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    if (splits == null) {
      if (other.splits != null)
        return false;
    } else if (!splits.equals(other.splits))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "TransferEntity [id=" + id + ", createdAt=" + createdAt + ", splits=" + splits
        + "]";
  }

}
