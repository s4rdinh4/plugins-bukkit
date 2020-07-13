package com.sk89q.worldguard.blacklist.action;

import com.sk89q.worldguard.blacklist.event.BlacklistEvent;

public interface Action {
  ActionResult apply(BlacklistEvent paramBlacklistEvent, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3);
}


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\blacklist\action\Action.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */