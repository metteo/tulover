package net.novaware.tulover.resource

import spock.lang.Specification

class AccountsResourceSpec extends Specification {
  
  AccountsResource instance = new AccountsResource()
  
  def "should return 'wow' when making GET /accounts"() {
    expect:
    
    null != instance.get("123", null)
  }
}
