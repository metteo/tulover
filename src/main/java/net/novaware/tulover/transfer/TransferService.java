package net.novaware.tulover.transfer;

import java.util.List;

public interface TransferService {

  Transfer create(Transfer prototype);
  
  List<Transfer> queryAll();
  
  List<Transfer> queryBy(String account);
  
  Transfer get(String id);
}
