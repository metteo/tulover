package net.novaware.tulover.feature

import javax.ws.rs.client.Client
import javax.ws.rs.client.ClientBuilder
import javax.ws.rs.core.GenericType
import javax.ws.rs.core.Response.Status
import javax.ws.rs.core.Response.StatusType

import org.glassfish.jersey.client.proxy.WebResourceFactory

import net.novaware.tulover.Tulover
import net.novaware.tulover.api.Account
import net.novaware.tulover.api.AccountsResource
import net.novaware.tulover.api.ItemHolder
import spock.lang.Shared
import spock.lang.Specification

class ManageAccountsFeature extends Specification {

  @Shared
  Tulover tulover

  @Shared 
  AccountsResource accountsResource

  def setupSpec() {
    int port = 11111
    tulover = new Tulover().start(port)

    def target = ClientBuilder.newClient()
        .target("http://localhost:$port/")
        .path("resources")
        .path("accounts")

    accountsResource = WebResourceFactory.newResource(AccountsResource.class,
        target)
  }

  def cleanupSpec() {
    tulover.stop();
  }

  //scenario
  def "should accept and return accounts"() {
    given:
      def alice = "alice"
      def aliceUsd = new Account(owner: alice, currency: "USD")
      def aliceEur = new Account(owner: alice, currency: "EUR")
      
      def bob = "bob"
      def bobPln = new Account(owner: bob, currency: "PLN")
      def bobGbp = new Account(owner: bob, currency: "GBP")

    when:
    aliceUsd = create(aliceUsd)
    aliceEur = create(aliceEur)
    bobPln = create(bobPln)
    bobGbp = create(bobGbp)
    
    def aliceAllResp = accountsResource.queryBy(alice)
    
    def bobPln2Resp = accountsResource.get(bobPln.number, null)

    then:
    aliceAllResp.getStatus() == Status.OK.getStatusCode()
    def aliceAll = aliceAllResp.readEntity(new GenericType<ItemHolder<Account>>(){})
    aliceUsd == aliceAll.items[0]
    aliceEur == aliceAll.items[1]
    
    bobPln2Resp.getStatus() == Status.OK.getStatusCode()
    def bobPln2 = bobPln2Resp.readEntity(Account.class)
    bobPln == bobPln2
  }
  
  Account create(Account account) {
    def response = accountsResource.create(account);
    
    assert response.getStatus() == Status.CREATED.getStatusCode()
    return response.readEntity(Account.class);
  }
}
