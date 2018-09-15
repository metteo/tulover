package net.novaware.tulover.account;

import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;

public class AccountRepositoryImpl implements AccountRepository {
  
  private static final Logger logger = Logger.getLogger("AccountRepositoryImpl");
  
  private AccountStore store;
  
  private AccountMapper mapper;
  
  private Supplier<UUID> uuidGenerator;

  @Inject
  public AccountRepositoryImpl(AccountStore store, AccountMapper mapper) {
    this(store, mapper, UUID::randomUUID);
  }
  
  public AccountRepositoryImpl(AccountStore store, AccountMapper mapper, Supplier<UUID> uuidGenerator) {
    this.store = store;
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
    
    return mapper.toAccount(entity);
  }

}
