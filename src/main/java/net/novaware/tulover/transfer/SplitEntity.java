package net.novaware.tulover.transfer;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Split representation (a structure, not action)
 * <br>
 * Splits describe from/to where money goes as part of transfer
 * <li>amount < 0 - amount is transfered out of the account
 * <li>amount > 0 - amount is transfered into the account
 * <br>
 * Sum of all split amounts from a single transaction should equal 0
 */
public class SplitEntity implements Cloneable {

  private UUID id;
  private UUID account;
  private BigDecimal amount;

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public UUID getAccount() {
    return account;
  }

  public void setAccount(UUID account) {
    this.account = account;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  @Override
  public SplitEntity clone() {
    try {
      SplitEntity clone = (SplitEntity) super.clone();
      return clone;
    } catch (CloneNotSupportedException e) {
      throw new AssertionError("should implement Cloneable", e);
    }
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((account == null) ? 0 : account.hashCode());
    result = prime * result + ((amount == null) ? 0 : amount.hashCode());
    result = prime * result + ((id == null) ? 0 : id.hashCode());
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
    SplitEntity other = (SplitEntity) obj;
    if (account == null) {
      if (other.account != null)
        return false;
    } else if (!account.equals(other.account))
      return false;
    if (amount == null) {
      if (other.amount != null)
        return false;
    } else if (!amount.equals(other.amount))
      return false;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "SplitEntity [id=" + id + ", account=" + account + ", amount=" + amount + "]";
  }

}
