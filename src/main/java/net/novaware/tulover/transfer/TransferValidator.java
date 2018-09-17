package net.novaware.tulover.transfer;

import java.math.BigDecimal;

import net.novaware.tulover.api.Transfer;

public class TransferValidator {

  public boolean isValidPrototype(Transfer transfer) {
    if (transfer == null) {
      return false;
    }
    
    String from = transfer.getFrom();
    if (from == null || from.isEmpty()) {
      return false;
    }
    
    String to = transfer.getTo();
    if (to == null || to.isEmpty()) {
      return false;
    }
    
    BigDecimal amount = transfer.getAmount();
    if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
      return false;
    }
     
    return true;
  }
}
