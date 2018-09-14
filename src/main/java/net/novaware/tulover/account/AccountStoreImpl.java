package net.novaware.tulover.account;

import java.util.UUID;

public class AccountStoreImpl implements AccountStore {

  @Override
  public AccountEntity get(UUID number) {
    AccountEntity e = new AccountEntity();
    e.setNumber(number);
    e.setCurrency("USD");
    
    return e;
  }

}
