package net.novaware.tulover.feature

import javax.ws.rs.client.ClientBuilder
import javax.ws.rs.core.Response.Status

import org.glassfish.jersey.client.proxy.WebResourceFactory

import net.novaware.tulover.Tulover
import net.novaware.tulover.account.Account
import net.novaware.tulover.account.AccountsResource
import net.novaware.tulover.transfer.Transfer
import net.novaware.tulover.transfer.TransfersResource
import spock.lang.Shared
import spock.lang.Specification

class TransferMoneyFeature extends Specification {
  
  @Shared
  Tulover tulover

  @Shared 
  AccountsResource accountsResource
  
  @Shared
  TransfersResource transfersResource

  def setupSpec() {
    int port = 22222
    tulover = new Tulover().start(port)

    def target = ClientBuilder.newClient()
        .target("http://localhost:$port/")
        .path("resources")

    accountsResource = WebResourceFactory.newResource(AccountsResource.class,
        target.path("accounts"))
    
    transfersResource = WebResourceFactory.newResource(TransfersResource.class,
      target.path("transfers"))
  }

  def cleanupSpec() {
    tulover.stop();
  }
  
  def "should allow money transfers"() {
    given:
    def aliceUsd = create(new Account(owner: "alice", currency: "USD"))
    def bobUsd = create(new Account(owner: "bob", currency: "USD"))
    
    def transfer = new Transfer(from: aliceUsd.number, to: bobUsd.number, amount: 123.45g)
    
    when:
    def createdResp = transfersResource.create(transfer)
    
    then:
    createdResp.status == 201
    def created = createdResp.readEntity(Transfer.class)
    created != null
  }
  
  Account create(Account account) {
    def response = accountsResource.create(account);
    
    assert response.getStatus() == Status.CREATED.getStatusCode()
    return response.readEntity(Account.class);
  }
}
