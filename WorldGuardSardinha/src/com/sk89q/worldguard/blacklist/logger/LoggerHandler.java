package com.sk89q.worldguard.blacklist.logger;

import com.sk89q.worldguard.blacklist.event.BlacklistEvent;

public interface LoggerHandler {
  void logEvent(BlacklistEvent paramBlacklistEvent, String paramString);
  
  void close();
}


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\blacklist\logger\LoggerHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */