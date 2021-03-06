package net.novaware.tulover.transfer;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import net.novaware.tulover.util.ObjectStore;

public interface TransferStore extends ObjectStore<TransferEntity, UUID> {

  List<TransferEntity> queryAll();

  List<TransferEntity> queryBy(UUID account);

  BigDecimal getBalance(UUID account);
}
