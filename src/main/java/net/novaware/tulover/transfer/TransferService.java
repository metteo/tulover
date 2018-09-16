package net.novaware.tulover.transfer;

import java.util.List;

public interface TransferService {

  Transfer create(Transfer prototype);
  
  List<Transfer> queryAll();
  
  Transfer get(String id);
}
