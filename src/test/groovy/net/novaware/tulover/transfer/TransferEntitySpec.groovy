package net.novaware.tulover.transfer

import static java.util.UUID.*
import static java.util.Arrays.*;

import spock.lang.Specification

class TransferEntitySpec extends Specification {

  def "should deeply clone splits" () {
    given:
    def split1 = new SplitEntity(account:randomUUID())
    def split2 = new SplitEntity(account:randomUUID())

    def transfer1 = new TransferEntity(id:randomUUID()) // null splits
    def transfer2 = transfer1.clone();                  // 2 splits
    
    transfer2.setSplits(asList(split1, split2)); 

    when:
    def clone1 = transfer1.clone()
    def clone2 = transfer2.clone();
    
    then:
    clone1 == transfer1
    clone2 == transfer2
    
    !transfer1.is(transfer2)
    !clone1.is(transfer1)
    !clone2.is(transfer2)
    
    clone1.splits == null
    
    !clone2.splits.is(transfer2.splits)
    !clone2.splits[0].is(transfer2.splits[0])
    !clone2.splits[1].is(transfer2.splits[1])
    
    notThrown(NullPointerException.class)
  }
}
