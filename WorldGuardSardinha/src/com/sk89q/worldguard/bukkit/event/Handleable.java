package com.sk89q.worldguard.bukkit.event;

import org.bukkit.event.Event;

public interface Handleable {
  Event.Result getResult();
  
  void setResult(Event.Result paramResult);
}


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\bukkit\event\Handleable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */