package net.novaware.tulover.account

import javax.ws.rs.core.UriBuilder
import javax.ws.rs.core.UriInfo

import net.novaware.tulover.account.AccountsResource
import net.novaware.tulover.account.AccountsResourceImpl
import spock.lang.Specification
import spock.lang.Unroll

class AccountsResourceImplSpec extends Specification {

  AccountRepository repo = Mock()
  UriInfo uriInfo = Mock()

  AccountsResource instance = new AccountsResourceImpl(repo, new AccountValidator(), uriInfo)

  def "should return '400 Bad request' when POSTing with empty body"() {
    when:
    def response = instance.create(null)

    then:
    response.status == 400
  }

  @Unroll
  def "should return '#status' when POSTing Account(#number, #owner, #currency)"() {
    given:
    def account = new Account(number: number, owner: owner, currency: currency)

    when:
    def response = instance.create(account)

    then:
    response.status == status

    where:

    number            | owner      | currency || status
    UUID.randomUUID() | "testUser" | "USD"    || 400
    null              | null       | "USD"    || 400
    null              | "test"     | null     || 400
    null              | null       | null     || 400
    null              | "testUser" | "BLA"    || 400
  }
  
  def "should return '201 Created' when POSTing valid account"() {
    given:
    def account = new Account(owner: "test", currency: "USD")
    
    def created = account.clone()
    created.number = UUID.randomUUID()
    created.balance = 0g;
    
    repo.create(_) >> created
    uriInfo.getAbsolutePathBuilder() >> UriBuilder.newInstance().uri("http://host:1234/a/b")
    
    
    when:
    def response = instance.create(account)
    Account entity = response.getEntity()
    
    then:
    response.status == 201
    response.location.toString() == "http://host:1234/a/b/" + entity.number
    entity.owner == "test" //owner is a reference to this spec, doesn't work with groovy 'with'
    with(entity) {
      number != null
      currency == "USD"
      balance == 0g
    }
  }
}
