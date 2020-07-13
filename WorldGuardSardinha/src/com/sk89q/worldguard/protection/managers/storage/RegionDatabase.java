package com.sk89q.worldguard.protection.managers.storage;

import com.sk89q.worldguard.protection.managers.RegionDifference;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import java.util.Set;

public interface RegionDatabase {
  String getName();
  
  Set<ProtectedRegion> loadAll() throws StorageException;
  
  void saveAll(Set<ProtectedRegion> paramSet) throws StorageException;
  
  void saveChanges(RegionDifference paramRegionDifference) throws DifferenceSaveException, StorageException;
}


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\protection\managers\storage\RegionDatabase.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */