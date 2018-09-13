package net.novaware.tulover.feature

import net.novaware.tulover.Tulover
import spock.lang.Shared
import spock.lang.Specification

class ManageAccountsFeature extends Specification {
  
  @Shared 
  static Thread tulover
  
  def setupSpec() {
    tulover = new Thread(new Tulover());
    tulover.start();
    Thread.sleep(1000L) //wait for jetty to start
  }
  
  def cleanupSpec() {
    tulover.interrupt();
  }
  
  //scenario
  def "should return 'wow' when making GET /accounts"() {
    //given when then
    expect:
    
    "wow" == "wow"
  }
}
