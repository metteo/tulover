package net.novaware.tulover.transfer

import javax.ws.rs.core.UriBuilder
import javax.ws.rs.core.UriInfo

import net.novaware.tulover.api.ItemHolder
import net.novaware.tulover.api.Transfer
import net.novaware.tulover.api.TransfersResource
import spock.lang.Specification

class TransfersResourceImplSpec extends Specification {

  TransferService service = Mock()
  
  TransferValidator validator = Mock()
  
  UriInfo uriInfo = Mock()
  
  TransfersResource instance = new TransfersResourceImpl(service, validator, uriInfo)
  
  def "should return '400 Bad request' when POSTing with invalid transfer"() {
    given:
    validator.isValidPrototype(_) >> false
    
    when:
    def response = instance.create(null)

    then:
    response.status == 400
  }
  
  def "should return '200 OK' when POSTing valid transfer" () {
    given:
    validator.isValidPrototype(_) >> true
    
    def transfer = new Transfer(id: UUID.randomUUID())
    
    uriInfo.getAbsolutePathBuilder() >> UriBuilder.newInstance().uri("http://host:1234/a/b")
    
    when:
    def response = instance.create(transfer)
    
    then:
    response.status == 201
    response.location.toString() == "http://host:1234/a/b/" + transfer.id
    response.entity.is(transfer)
    1 * service.create(transfer) >> transfer
  }
  
  def "should return '200 OK' when GETing all transfers"() {
    given:
    def transfers = [new Transfer(), new Transfer()]
    
    service.queryAll() >> transfers
    
    when:
    def response = instance.queryBy(null)
    
    then:
    response.status == 200
    ItemHolder<Transfer> holder = response.entity
    holder.count == 2
    holder.items.size() == 2
  }
  
  def "should return '404 Not found' when GETing non-existing transfer by id"() {
    given:
    String id = UUID.randomUUID()
    
    when:
    def response = instance.get(id)
    
    then:
    response.status == 404
    response.entity == null
  }
  
  def "should return '200 OK' when GETing transfer by id"() {
    given:
    String id = UUID.randomUUID()
    def transfer = new Transfer(id: id)
    
    service.get(id) >> transfer
    
    when:
    def response = instance.get(id)
    
    then:
    response.status == 200
    response.entity == transfer
  }
}
