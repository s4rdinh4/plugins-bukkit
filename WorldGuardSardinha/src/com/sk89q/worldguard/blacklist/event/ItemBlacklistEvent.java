/*    */ package com.sk89q.worldguard.blacklist.event;
/*    */ 
/*    */ import com.sk89q.worldedit.Vector;
/*    */ import com.sk89q.worldguard.LocalPlayer;
/*    */ import com.sk89q.worldguard.blacklist.target.Target;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ abstract class ItemBlacklistEvent
/*    */   extends AbstractBlacklistEvent
/*    */ {
/*    */   ItemBlacklistEvent(@Nullable LocalPlayer player, Vector position, Target target) {
/* 38 */     super(player, position, target);
/*    */   }
/*    */ 
/*    */   
/*    */   public String getLoggerMessage() {
/* 43 */     return getPlayerName() + " tried to " + getDescription() + " " + getTarget().getFriendlyName();
/*    */   }
/*    */ 
/*    */   
/*    */   public Vector getLoggedPosition() {
/* 48 */     return (getPlayer() != null) ? getPlayer().getPosition() : getPosition();
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\blacklist\event\ItemBlacklistEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */