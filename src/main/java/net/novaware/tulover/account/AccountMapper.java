package net.novaware.tulover.account;

import java.util.List;
import java.util.UUID;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import net.novaware.tulover.api.Account;

@Mapper(imports = UUID.class)
public abstract class AccountMapper {

  @Mapping(target = Account.PROPERTY_BALANCE, ignore = true)
  public abstract Account toAccount(AccountEntity input);
  
  public abstract AccountEntity toAccountEntity(Account input);
  
  protected UUID toUUID(String number) {
    return UUID.fromString(number);
  }
  
  protected String toString(UUID number) {
    return number.toString();
  }

  public abstract List<Account> toAccounts(List<AccountEntity> entities);
}
