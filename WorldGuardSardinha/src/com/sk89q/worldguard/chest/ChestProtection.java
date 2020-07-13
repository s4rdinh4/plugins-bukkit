package com.sk89q.worldguard.chest;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public interface ChestProtection {
  boolean isProtected(Block paramBlock, Player paramPlayer);
  
  boolean isProtectedPlacement(Block paramBlock, Player paramPlayer);
  
  boolean isAdjacentChestProtected(Block paramBlock, Player paramPlayer);
  
  @Deprecated
  boolean isChest(Material paramMaterial);
  
  boolean isChest(int paramInt);
}


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\chest\ChestProtection.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */