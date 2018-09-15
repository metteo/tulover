package net.novaware.tulover.account

import java.util.function.Supplier

import org.mapstruct.factory.Mappers

import spock.lang.Specification

class AccountStoreImplSpec extends Specification {
  
  AccountStore instance = new AccountStoreImpl();
  
  def "should properly store new account" () {
    given:
    def entity = new AccountEntity(number: UUID.randomUUID())
    
    when:
    def created = instance.create(entity)
    
    then:
    created == entity
    !created.is(entity)
  }
  
  def "should properly hold an account" () {
    given:
    def number = UUID.randomUUID()
    def entity = new AccountEntity(number: number)
    
    instance.create(entity)
    
    when:
    def fromStore = instance.get(number)
    
    then:
    fromStore == entity
    !fromStore.is(entity)
  }
  
  def "should properly index by owner" () {
    given:
    def entity1 = new AccountEntity(number: UUID.randomUUID(), owner: "root")
    def entity2 = new AccountEntity(number: UUID.randomUUID(), owner: "root")
    
    instance.create(entity1)
    instance.create(entity2)
    
    when:
    def fromStore = instance.queryBy("root")
    
    then:
    fromStore == [entity1, entity2]
    !fromStore[0].is(entity1)
    !fromStore[1].is(entity2)
  }
}
