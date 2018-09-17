package net.novaware.tulover.api;

import java.math.BigDecimal;

/**
 * Account representation
 */
public class Account implements Cloneable {
  
  public static final String MEDIA_TYPE = "application/vnd.novaware.tulover.account.v1";
  public static final String MEDIA_TYPE_JSON = MEDIA_TYPE + "+json";
  public static final String MEDIA_TYPE_JSON_UTF8 = MEDIA_TYPE_JSON + ";charset=utf8";
  
  // should be generated in meta class by annotation processor
  public static final String PROPERTY_BALANCE = "balance";

  private String number;
  private String owner;
  private String currency;

  /** optional */
  private BigDecimal balance;

  public String getNumber() {
    return number;
  }

  public void setNumber(String number) {
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

  public BigDecimal getBalance() {
    return balance;
  }

  public void setBalance(BigDecimal balance) {
    this.balance = balance;
  }
  
  @Override
  public Account clone() {
    try {
      Account clone = (Account) super.clone();
      return clone;
    } catch (CloneNotSupportedException e) {
      throw new AssertionError("should implement Cloneable", e);
    }
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((balance == null) ? 0 : balance.hashCode());
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
    Account other = (Account) obj;
    if (balance == null) {
      if (other.balance != null)
        return false;
    } else if (!balance.equals(other.balance))
      return false;
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
    return "Account [number=" + number + ", owner=" + owner + ", currency=" + currency + ", balance=" + balance + "]";
  }

}
