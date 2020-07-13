package com.sk89q.worldguard.util.profile.resolver;

import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableList;
import com.sk89q.worldguard.util.profile.Profile;
import java.io.IOException;
import javax.annotation.Nullable;

public interface ProfileService {
  int getIdealRequestLimit();
  
  @Nullable
  Profile findByName(String paramString) throws IOException, InterruptedException;
  
  ImmutableList<Profile> findAllByName(Iterable<String> paramIterable) throws IOException, InterruptedException;
  
  void findAllByName(Iterable<String> paramIterable, Predicate<Profile> paramPredicate) throws IOException, InterruptedException;
}


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguar\\util\profile\resolver\ProfileService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */