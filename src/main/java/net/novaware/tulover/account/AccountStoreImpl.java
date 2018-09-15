package net.novaware.tulover.account;

import java.util.List;
import java.util.UUID;

public class AccountStoreImpl implements AccountStore {

  @Override
  public AccountEntity get(UUID number) {
    AccountEntity e = new AccountEntity();
    e.setNumber(number);
    e.setCurrency("USD");
    
    return e;
  }

  @Override
  public AccountEntity create(AccountEntity object) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<AccountEntity> queryBy(String owner) {
    // TODO Auto-generated method stub
    return null;
  }

}
