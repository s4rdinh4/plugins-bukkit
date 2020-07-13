package com.sk89q.worldguard.protection.managers.index;

import com.google.common.base.Predicate;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.Vector2D;
import com.sk89q.worldguard.protection.managers.RegionDifference;
import com.sk89q.worldguard.protection.managers.RemovalStrategy;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.util.ChangeTracked;
import java.util.Collection;
import java.util.Set;
import javax.annotation.Nullable;

public interface RegionIndex extends ChangeTracked {
  void bias(Vector2D paramVector2D);
  
  void biasAll(Collection<Vector2D> paramCollection);
  
  void forget(Vector2D paramVector2D);
  
  void forgetAll();
  
  void add(ProtectedRegion paramProtectedRegion);
  
  void addAll(Collection<ProtectedRegion> paramCollection);
  
  Set<ProtectedRegion> remove(String paramString, RemovalStrategy paramRemovalStrategy);
  
  boolean contains(String paramString);
  
  @Nullable
  ProtectedRegion get(String paramString);
  
  void apply(Predicate<ProtectedRegion> paramPredicate);
  
  void applyContaining(Vector paramVector, Predicate<ProtectedRegion> paramPredicate);
  
  void applyIntersecting(ProtectedRegion paramProtectedRegion, Predicate<ProtectedRegion> paramPredicate);
  
  int size();
  
  RegionDifference getAndClearDifference();
  
  void setDirty(RegionDifference paramRegionDifference);
  
  Collection<ProtectedRegion> values();
}


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\protection\managers\index\RegionIndex.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */