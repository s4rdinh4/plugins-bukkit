package com.sk89q.worldguard.domains;

import com.sk89q.worldguard.LocalPlayer;
import java.util.UUID;

public interface Domain {
  boolean contains(LocalPlayer paramLocalPlayer);
  
  boolean contains(UUID paramUUID);
  
  @Deprecated
  boolean contains(String paramString);
  
  int size();
  
  void clear();
}


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\domains\Domain.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */