package com.sk89q.worldguard.protection;

import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.protection.association.RegionAssociable;
import com.sk89q.worldguard.protection.flags.Flag;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import java.util.Collection;
import java.util.Set;
import javax.annotation.Nullable;

public interface ApplicableRegionSet extends Iterable<ProtectedRegion> {
  boolean isVirtual();
  
  @Deprecated
  boolean canBuild(LocalPlayer paramLocalPlayer);
  
  boolean testState(@Nullable RegionAssociable paramRegionAssociable, StateFlag... paramVarArgs);
  
  @Nullable
  StateFlag.State queryState(@Nullable RegionAssociable paramRegionAssociable, StateFlag... paramVarArgs);
  
  @Nullable
  <V> V queryValue(@Nullable RegionAssociable paramRegionAssociable, Flag<V> paramFlag);
  
  <V> Collection<V> queryAllValues(@Nullable RegionAssociable paramRegionAssociable, Flag<V> paramFlag);
  
  @Deprecated
  boolean canConstruct(LocalPlayer paramLocalPlayer);
  
  @Deprecated
  boolean allows(StateFlag paramStateFlag);
  
  @Deprecated
  boolean allows(StateFlag paramStateFlag, @Nullable LocalPlayer paramLocalPlayer);
  
  boolean isOwnerOfAll(LocalPlayer paramLocalPlayer);
  
  boolean isMemberOfAll(LocalPlayer paramLocalPlayer);
  
  @Deprecated
  @Nullable
  <T extends Flag<V>, V> V getFlag(T paramT);
  
  @Deprecated
  @Nullable
  <T extends Flag<V>, V> V getFlag(T paramT, @Nullable LocalPlayer paramLocalPlayer);
  
  int size();
  
  Set<ProtectedRegion> getRegions();
}


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\protection\ApplicableRegionSet.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */