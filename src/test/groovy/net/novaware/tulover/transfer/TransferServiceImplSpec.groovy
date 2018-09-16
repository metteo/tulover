package net.novaware.tulover.transfer

import static java.util.UUID.*
import spock.lang.Specification

class TransferServiceImplSpec extends Specification {
  
  TransferStore store = new TransferStoreImpl();
  
  TransferService instance = new TransferServiceImpl(store, new TransferMapper())
  
  def "should accept a valid transfer" () {
    given:
    String account1 = randomUUID()
    String account2 = randomUUID()
    def transfer = new Transfer(from: account1, to: account2, amount: 150.20g)
    
    when:
    def created = instance.create(transfer)
    
    then:
    with(created) {
      id != null
      createdAt != null
      from == account1
      to == account2
      amount == 150.20g
    }
    
    and:
    def all = instance.queryAll()
    all.size() == 1
    all[0] == created
    
    and:
    created == instance.get(created.getId())
  }
}
