package net.novaware.tulover.account;

import java.util.UUID;

public class AccountRespositoryImpl implements AccountRepository {

  @Override
  public Account create(Account prototype) {
    prototype.setNumber(UUID.randomUUID().toString());
    return prototype;
  }

}
