package com.sk89q.worldguard.protection.managers.storage;

import java.util.List;

public interface RegionDriver {
  RegionDatabase get(String paramString);
  
  List<RegionDatabase> getAll() throws StorageException;
}


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\protection\managers\storage\RegionDriver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */