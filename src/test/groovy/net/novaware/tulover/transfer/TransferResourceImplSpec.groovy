package net.novaware.tulover.transfer

import spock.lang.Specification
import spock.lang.Unroll

class TransferResourceImplSpec extends Specification {

  TransferValidator validator = Mock();
  
  TransfersResourceImpl instance = new TransfersResourceImpl(validator)
  
  def "should return '400 Bad request' when POSTing with invalid body"() {
    given:
    validator.isValidPrototype(_) >> false
    
    when:
    def response = instance.create(null)

    then:
    response.status == 400
  }
}
