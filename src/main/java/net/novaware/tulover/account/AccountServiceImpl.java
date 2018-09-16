package net.novaware.tulover.account;

import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;

import net.novaware.tulover.transfer.TransferStore;

public class AccountServiceImpl implements AccountService {

  private static final Logger logger = Logger.getLogger("AccountRepositoryImpl");

  private AccountStore store;

  private TransferStore transferStore;

  private AccountMapper mapper;

  private Supplier<UUID> uuidGenerator;

  @Inject
  public AccountServiceImpl(AccountStore store, TransferStore transferStore, AccountMapper mapper) {
    this(store, transferStore, mapper, UUID::randomUUID);
  }

  public AccountServiceImpl(AccountStore store, TransferStore transferStore, AccountMapper mapper,
      Supplier<UUID> uuidGenerator) {
    this.store = store;
    this.transferStore = transferStore;
    this.mapper = mapper;
    this.uuidGenerator = uuidGenerator;
  }

  @Override
  public Account create(Account prototype) {
    UUID number = uuidGenerator.get();

    prototype.setNumber(number.toString());

    AccountEntity entity = mapper.toAccountEntity(prototype);
    AccountEntity created = store.create(entity);

    return mapper.toAccount(created);
  }

  @Override
  public List<Account> queryBy(String owner) {
    List<AccountEntity> entities = store.queryBy(owner);

    return mapper.toAccounts(entities);
  }

  @Override
  public Account get(String number, boolean withBalance) {
    UUID numberUuid;

    try {
      numberUuid = UUID.fromString(number);
    } catch (IllegalArgumentException e) {
      logger.fine(() -> "Account requested with invalid number: " + number);
      logger.log(Level.FINER, "", e);
      return null;
    }

    AccountEntity entity = store.get(numberUuid);
    Account result = mapper.toAccount(entity);

    if (withBalance) {
      result.setBalance(transferStore.getBalance(numberUuid));
    }
    
    return result;
  }

}
