package net.novaware.tulover.transfer

import java.util.function.Supplier

import org.mapstruct.factory.Mappers

import spock.lang.Specification

class TransferStoreImplSpec extends Specification {
  
  TransferStore instance = new TransferStoreImpl();
  
  def "should properly store new transfer" () {
    given:
    def entity = new TransferEntity(id: UUID.randomUUID())
    
    when:
    def created = instance.create(entity)
    
    then:
    created == entity
    !created.is(entity)
  }
  
  def "should properly hold a transfer" () {
    given:
    def id = UUID.randomUUID()
    def entity = new TransferEntity(id: id)
    
    instance.create(entity)
    
    when:
    def fromStore = instance.get(id)
    def allFromStore = instance.queryAll();
    
    then:
    fromStore == entity
    !fromStore.is(entity)
    
    allFromStore.size() == 1
    allFromStore[0] == entity
    !allFromStore[0].is(entity)
  }
}
