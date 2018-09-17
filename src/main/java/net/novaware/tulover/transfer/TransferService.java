package net.novaware.tulover.transfer;

import java.util.List;

import net.novaware.tulover.api.Transfer;

public interface TransferService {

  Transfer create(Transfer prototype);
  
  List<Transfer> queryAll();
  
  List<Transfer> queryBy(String account);
  
  Transfer get(String id);
}
