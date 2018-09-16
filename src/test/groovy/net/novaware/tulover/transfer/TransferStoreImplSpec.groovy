package net.novaware.tulover.transfer

import java.util.function.Supplier

import org.mapstruct.factory.Mappers

import spock.lang.Specification

class TransferStoreImplSpec extends Specification {

  TransferStore instance = new TransferStoreImpl();

  def "should properly hold a transfer" () {
    given:
    def id1 = UUID.randomUUID()
    def id2 = UUID.randomUUID()
    def account1 = UUID.randomUUID()
    def account2 = UUID.randomUUID()

    def transfer1 = new TransferEntity(id: id1, splits:[
      new SplitEntity(account: account1, amount: -23.45g),
      new SplitEntity(account: account2, amount: 23.45g)
    ])
    def transfer2 = new TransferEntity(id: id2, splits:[
      new SplitEntity(account: account2, amount: -13.98g),
      new SplitEntity(account: account1, amount: 13.98g)
    ])

    def created = instance.create(transfer1)
    instance.create(transfer2)

    when:
    def fromStore = instance.get(id1)
    def allFromStore = instance.queryAll()
    def account1Index = instance.queryBy(account1)
    def account2Balance = instance.getBalance(account2);

    then:
    created == transfer1
    !created.is(transfer1)
    
    and:
    fromStore == transfer1
    !fromStore.is(transfer1)

    and:
    allFromStore.size() == 2
    allFromStore[0] == transfer1
    !allFromStore[0].is(transfer1)
    allFromStore[1] == transfer2
    !allFromStore[1].is(transfer2)

    and:
    account1Index.size() == 2
    account1Index[0] == transfer1
    !account1Index[0].is(transfer1)
    account1Index[1] == transfer2
    !account1Index[1].is(transfer2)
    
    and:
    account2Balance == 9.47g
  }
}
