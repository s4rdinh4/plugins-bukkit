package com.sk89q.worldguard.util.profile.cache;

import com.google.common.collect.ImmutableMap;
import com.sk89q.worldguard.util.profile.Profile;
import java.util.UUID;
import javax.annotation.Nullable;

public interface ProfileCache {
  void put(Profile paramProfile);
  
  void putAll(Iterable<Profile> paramIterable);
  
  @Nullable
  Profile getIfPresent(UUID paramUUID);
  
  ImmutableMap<UUID, Profile> getAllPresent(Iterable<UUID> paramIterable);
}


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguar\\util\profile\cache\ProfileCache.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */