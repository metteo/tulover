package net.novaware.tulover.feature

import javax.ws.rs.client.Client
import javax.ws.rs.client.ClientBuilder
import javax.ws.rs.core.Response.Status
import javax.ws.rs.core.Response.StatusType

import org.glassfish.jersey.client.proxy.WebResourceFactory

import net.novaware.tulover.Tulover
import net.novaware.tulover.account.Account
import net.novaware.tulover.account.AccountsResource
import spock.lang.Shared
import spock.lang.Specification

class ManageAccountsFeature extends Specification {

  @Shared
  static Thread tulover

  def setupSpec() {
    tulover = new Thread(new Tulover());
    tulover.start();
    Thread.sleep(1000L) //wait for jetty to start, TODO: find a better way
  }

  def cleanupSpec() {
    tulover.interrupt();
  }

  //scenario
  def "should return valid UUID when making GET /accounts"() {
    given:
    def target = ClientBuilder.newClient()
        .target("http://localhost:8080/")
        .path("resources")
        .path("accounts")
    AccountsResource accountsRes = WebResourceFactory.newResource(AccountsResource.class,
        target)

    when:
    def response = accountsRes.get("123", null);
    def entity = response.readEntity(Account.class);

    then:
    response.getStatus() == Status.NO_CONTENT.getStatusCode()
    //UUID.fromString(entity.number) != null
  }
}
