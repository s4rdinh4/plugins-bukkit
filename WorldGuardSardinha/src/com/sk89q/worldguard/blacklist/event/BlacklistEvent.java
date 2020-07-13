package com.sk89q.worldguard.blacklist.event;

import com.sk89q.worldedit.Vector;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.blacklist.target.Target;
import javax.annotation.Nullable;

public interface BlacklistEvent {
  @Nullable
  LocalPlayer getPlayer();
  
  String getCauseName();
  
  Vector getPosition();
  
  Vector getLoggedPosition();
  
  Target getTarget();
  
  String getDescription();
  
  String getLoggerMessage();
  
  EventType getEventType();
}


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\blacklist\event\BlacklistEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */