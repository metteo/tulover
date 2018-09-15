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
  
  def "should return '400 Bad request' when GETing list without owner"() {
    when:
    def response = instance.queryBy(null)
    List<Account> entity = response.getEntity()

    then:
    response.status == 400
  }
  
  def "should return '200 OK' when GETing by owner without accounts"() {
    given:
    repo.queryBy("test") >> new ArrayList();
    
    when:
    def response = instance.queryBy("test")
    List<Account> entity = response.getEntity()

    then:
    response.status == 200
    entity.isEmpty()
  }
  
  def "should return '200 OK' when GETing by owner with 2 accounts"() {
    given:
    def acc1 = new Account();
    def acc2 = new Account();
    repo.queryBy("test") >> [acc1, acc2];
    
    when:
    def response = instance.queryBy("test")
    List<Account> entity = response.getEntity()

    then:
    response.status == 200
    entity.size() == 2
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
    
    repo.get(number, false) >> account
    
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
    
    repo.get(number, true) >> account
    
    when:
    def response = instance.get(number, ["balance"])
    
    then:
    response.status == 200
    response.getEntity().is(account)
  }
}
