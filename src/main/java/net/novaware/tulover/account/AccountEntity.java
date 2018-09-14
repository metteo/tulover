package net.novaware.tulover.account;

import java.util.UUID;

public class AccountEntity implements Cloneable {

  private UUID number;
  private String owner;
  private String currency;

  public UUID getNumber() {
    return number;
  }

  public void setNumber(UUID number) {
    this.number = number;
  }

  public String getOwner() {
    return owner;
  }

  public void setOwner(String owner) {
    this.owner = owner;
  }

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }
  
  @Override
  public AccountEntity clone() {
    try {
      AccountEntity clone = (AccountEntity) super.clone();
      return clone;
    } catch (CloneNotSupportedException e) {
      throw new AssertionError("should implement Cloneable", e);
    }
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((currency == null) ? 0 : currency.hashCode());
    result = prime * result + ((number == null) ? 0 : number.hashCode());
    result = prime * result + ((owner == null) ? 0 : owner.hashCode());
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
    AccountEntity other = (AccountEntity) obj;
    if (currency == null) {
      if (other.currency != null)
        return false;
    } else if (!currency.equals(other.currency))
      return false;
    if (number == null) {
      if (other.number != null)
        return false;
    } else if (!number.equals(other.number))
      return false;
    if (owner == null) {
      if (other.owner != null)
        return false;
    } else if (!owner.equals(other.owner))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "AccountEntity [number=" + number + ", owner=" + owner + ", currency=" + currency + "]";
  }
}
