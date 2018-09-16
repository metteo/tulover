package net.novaware.tulover.transfer;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.UUID;

public class TransferMapper {

  public TransferEntity toTransferEntity(Transfer input) {
    SplitEntity from = new SplitEntity();
    from.setAccount(UUID.fromString(input.getFrom()));
    from.setAmount(input.getAmount().negate());

    SplitEntity to = new SplitEntity();
    to.setAccount(UUID.fromString(input.getTo()));
    to.setAmount(input.getAmount());

    TransferEntity transfer = new TransferEntity();
    transfer.setSplits(Arrays.asList(from, to)); // ordered by convention, not guaranteed with >2 splits
    return transfer;
  }

  public Transfer toTransfer(TransferEntity input) {
    if (input == null) { return null; }
    
    Transfer result = new Transfer();
    result.setId(input.getId().toString());
    result.setCreatedAt(input.getCreatedAt());

    for (SplitEntity s : input.getSplits()) {
      if (s.getAmount().compareTo(BigDecimal.ZERO) > 0) {
        result.setTo(s.getAccount().toString());
        result.setAmount(s.getAmount());
      } else {
        result.setFrom(s.getAccount().toString());
      }
    }
    return result;
  }
}
