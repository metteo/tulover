package net.novaware.tulover.account;

import java.util.List;
import java.util.UUID;

public class AccountRespositoryImpl implements AccountRepository {

  @Override
  public Account create(Account prototype) {
    prototype.setNumber(UUID.randomUUID().toString());
    return prototype;
  }

  @Override
  public List<Account> queryBy(String owner) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Account get(String number, boolean withBalance) {
    // TODO Auto-generated method stub
    return null;
  }

}
