package net.novaware.tulover.account;

import java.math.BigDecimal;

/**
 * Account representation
 */
public class Account {
  
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
}
