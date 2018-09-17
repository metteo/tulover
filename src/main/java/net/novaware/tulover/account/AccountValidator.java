package net.novaware.tulover.account;

import org.joda.money.CurrencyUnit;
import org.joda.money.IllegalCurrencyException;

import net.novaware.tulover.api.Account;

public class AccountValidator {

  public boolean isValidPrototype(Account account) {
    if(account == null) {
      return false;
    }
    
    if (account.getNumber() != null) {
      return false;
    }
    
    String owner = account.getOwner();
    if (owner == null || owner.isEmpty()) {
      return false;
    }
    
    String currency = account.getCurrency();
    if(currency == null || currency.isEmpty()) {
      return false;
    }
    
    try {
      CurrencyUnit.of(currency);
    } catch(IllegalCurrencyException e) {
      return false;
    }
    
    return true;
  }
}
