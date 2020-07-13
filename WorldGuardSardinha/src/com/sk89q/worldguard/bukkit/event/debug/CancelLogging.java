package com.sk89q.worldguard.bukkit.event.debug;

import java.util.List;
import org.bukkit.event.Cancellable;

public interface CancelLogging extends Cancellable {
  List<CancelAttempt> getCancels();
}


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\bukkit\event\debug\CancelLogging.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */