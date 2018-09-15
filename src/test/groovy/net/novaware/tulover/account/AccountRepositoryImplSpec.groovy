package net.novaware.tulover.account

import java.util.function.Supplier

import org.mapstruct.factory.Mappers

import spock.lang.Specification

class AccountRepositoryImplSpec extends Specification {
  
  AccountStore store = Mock()
  
  Supplier<UUID> uuidGenerator = Mock()
  
  AccountMapper mapper = Mappers.getMapper(AccountMapper.class);
  
  AccountRepository instance = new AccountRepositoryImpl(store, mapper, uuidGenerator)
  
  def "should create new account in store"() {
    given:
    def account = new Account(owner: "test", currency: "PLN")
    def number = UUID.randomUUID()
    
    uuidGenerator.get() >> number
    
    def entity = new AccountEntity(number: number, owner: "test", currency: "PLN");
    
    store.create(entity) >> entity
    
    when:
    def created = instance.create(account)
    
    then:
    created.number == number.toString()
    created.owner == "test"
    created.currency == "PLN"
  }
  
  def "should return null when getting account with invalid number"() {
    expect:
    instance.get("123", false) == null
  }
  
  def "should return null when getting account with valid number"() {
    given:
    String number = UUID.randomUUID().toString()
    
    expect:
    instance.get(number, false) == null
  }
  
  def "should return null when getting account with existing number"() {
    given:
    def number = UUID.randomUUID()
    
    def entity = new AccountEntity(number: number, owner: "test", currency: "PLN")
    
    store.get(number) >> entity
    
    when:
    def account = instance.get(number.toString(), false)
    
    then:
    account.number == number.toString()
  }
}
