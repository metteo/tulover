package net.novaware.tulover.transfer;

import java.util.List;

public interface TransferService {

  Transfer create(Transfer prototype);
  
  List<Transfer> getAll();
  
  Transfer get(String id);
}
