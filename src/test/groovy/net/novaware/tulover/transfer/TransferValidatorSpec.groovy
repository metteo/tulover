package net.novaware.tulover.transfer

import static java.util.UUID.*

import spock.lang.Specification

class TransferValidatorSpec extends Specification {

  TransferValidator instance = new TransferValidator()

  def "should properly validate a prototype" () {
    expect:
    valid == instance.isValidPrototype(transfer)

    where:
    transfer                                                       || valid
    null                                                           || false
    new Transfer()                                                 || false
    new Transfer(to:randomUUID(), amount: 1g)                      || false
    new Transfer(from: randomUUID(), amount: 1g)                   || false
    new Transfer(from: randomUUID(), to:randomUUID())              || false
    new Transfer(from: randomUUID(), to:randomUUID(), amount: -1g) || false
    new Transfer(from: randomUUID(), to:randomUUID(), amount: 1g)  || true
  }
}
