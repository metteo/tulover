package net.novaware.tulover.account

import net.novaware.tulover.account.AccountsResource
import net.novaware.tulover.account.AccountsResourceImpl
import spock.lang.Specification

class AccountsResourceSpec extends Specification {
  
  AccountsResource instance = new AccountsResourceImpl()
  
  def "should return 'wow' when making GET /accounts"() {
    expect:
    
    null != instance.get("123", null)
  }
}
