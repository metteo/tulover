package net.novaware.tulover.account;

import java.util.List;

import net.novaware.tulover.api.Account;

public interface AccountService {

  Account create(Account prototype);

  List<Account> queryBy(String owner);

  Account get(String number, boolean withBalance);
}
