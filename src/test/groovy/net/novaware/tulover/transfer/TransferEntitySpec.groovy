package net.novaware.tulover.transfer

import static java.util.UUID.*
import static java.util.Arrays.*;

import spock.lang.Specification

class TransferEntitySpec extends Specification {

  def "should deeply clone splits" () {
    given:
    def split1 = new SplitEntity(id:randomUUID())
    def split2 = new SplitEntity(id:randomUUID())

    def transfer = new TransferEntity(id:randomUUID(), splits:asList(split1, split2))

    when:
    def clone = transfer.clone()
    
    then:
    clone == transfer
    
    !clone.is(transfer)
    !clone.splits.is(transfer.splits)
    !clone.splits[0].is(transfer.splits[0])
    !clone.splits[1].is(transfer.splits[1])
  }
}
