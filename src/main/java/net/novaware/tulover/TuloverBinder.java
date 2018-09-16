package net.novaware.tulover;

import javax.inject.Singleton;

import org.glassfish.jersey.internal.inject.AbstractBinder;
import org.mapstruct.factory.Mappers;

import net.novaware.tulover.account.AccountMapper;
import net.novaware.tulover.account.AccountRepository;
import net.novaware.tulover.account.AccountRepositoryImpl;
import net.novaware.tulover.account.AccountStore;
import net.novaware.tulover.account.AccountStoreImpl;
import net.novaware.tulover.account.AccountValidator;
import net.novaware.tulover.transfer.TransferService;
import net.novaware.tulover.transfer.TransferServiceImpl;
import net.novaware.tulover.transfer.TransferValidator;

public class TuloverBinder extends AbstractBinder {

  @Override
  protected void configure() {
    bind(AccountStoreImpl.class).to(AccountStore.class).in(Singleton.class);
    bind(AccountRepositoryImpl.class).to(AccountRepository.class);
    bind(AccountValidator.class).to(AccountValidator.class);
    
    bind(TransferServiceImpl.class).to(TransferService.class);
    bind(TransferValidator.class).to(TransferValidator.class);
    
    AccountMapper mapper = Mappers.getMapper(AccountMapper.class);
    bind(mapper).to(AccountMapper.class).in(Singleton.class);
  }

}
