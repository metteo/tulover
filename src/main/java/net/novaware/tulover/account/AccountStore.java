package net.novaware.tulover.account;

import java.util.List;
import java.util.UUID;

import net.novaware.tulover.util.ObjectStore;

public interface AccountStore extends ObjectStore<AccountEntity, UUID> {

  List<AccountEntity> queryBy(String owner);

}
