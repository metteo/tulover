package net.novaware.tulover.account;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface AccountMapper {

  @Mapping(target = "balance", ignore = true)
  Account toAccount(AccountEntity input);
  
  AccountEntity toAccountEntity(Account input);
}
