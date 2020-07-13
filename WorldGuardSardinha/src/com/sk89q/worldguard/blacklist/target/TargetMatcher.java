package com.sk89q.worldguard.blacklist.target;

public interface TargetMatcher {
  int getMatchedTypeId();
  
  boolean test(Target paramTarget);
}


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\blacklist\target\TargetMatcher.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */