package net.novaware.tulover.account;

public class AccountStoreImpl implements AccountStore {

  @Override
  public AccountEntity get(String number) {
    AccountEntity e = new AccountEntity();
    e.setNumber(number);
    e.setCurrency("USD");
    
    return e;
  }

}
