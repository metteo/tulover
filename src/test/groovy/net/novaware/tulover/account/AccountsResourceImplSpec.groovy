package net.novaware.tulover.account

import javax.ws.rs.core.UriBuilder
import javax.ws.rs.core.UriInfo

import net.novaware.tulover.account.AccountsResourceImpl
import net.novaware.tulover.api.Account
import net.novaware.tulover.api.AccountsResource
import net.novaware.tulover.api.ItemHolder
import spock.lang.Specification
import spock.lang.Unroll

class AccountsResourceImplSpec extends Specification {

  AccountService service = Mock()
  UriInfo uriInfo = Mock()

  AccountsResource instance = new AccountsResourceImpl(service, new AccountValidator(), uriInfo)

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
    
    service.create(_) >> created
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
  
  def "should return '400 Bad request' when GETing list without owner"() {
    when:
    def response = instance.queryBy(null)
    List<Account> entity = response.getEntity()

    then:
    response.status == 400
  }
  
  def "should return '200 OK' when GETing by owner without accounts"() {
    given:
    service.queryBy("test") >> new ArrayList();
    
    when:
    def response = instance.queryBy("test")
    ItemHolder<Account> entity = response.getEntity()

    then:
    response.status == 200
    entity.count == 0
    entity.items.isEmpty()
  }
  
  def "should return '200 OK' when GETing by owner with 2 accounts"() {
    given:
    def acc1 = new Account();
    def acc2 = new Account();
    service.queryBy("test") >> [acc1, acc2];
    
    when:
    def response = instance.queryBy("test")
    ItemHolder<Account> entity = response.getEntity()

    then:
    response.status == 200
    entity.count == 2
    entity.items.size() == 2
  }
  
  def "should return '404 Not found' when GETing by wrong number"() {
    when:
    def response = instance.get(UUID.randomUUID().toString(), null)
    
    then:
    response.status == 404
  }
  
  def "should return '200 OK' when GETing by correct number"() {
    given:
    def number = UUID.randomUUID().toString()
    def account = new Account()
    
    service.get(number, false) >> account
    
    when:
    def response = instance.get(number, null)
    
    then:
    response.status == 200
    response.getEntity().is(account)
  }
  
  def "should return '200 OK' when GETing by correct number with balance"() {
    given:
    def number = UUID.randomUUID().toString()
    def account = new Account()
    
    service.get(number, true) >> account
    
    when:
    def response = instance.get(number, ["balance"])
    
    then:
    response.status == 200
    response.getEntity().is(account)
  }
}
