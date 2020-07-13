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
/*    */ public final class BlockDispenseBlacklistEvent
/*    */   extends BlockBlacklistEvent
/*    */ {
/*    */   public BlockDispenseBlacklistEvent(@Nullable LocalPlayer player, Vector position, Target target) {
/* 38 */     super(player, position, target);
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDescription() {
/* 43 */     return "dispense";
/*    */   }
/*    */ 
/*    */   
/*    */   public String getLoggerMessage() {
/* 48 */     return getPosition() + " tried to " + getDescription() + " " + getTarget().getFriendlyName();
/*    */   }
/*    */ 
/*    */   
/*    */   public EventType getEventType() {
/* 53 */     return EventType.DISPENSE;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\blacklist\event\BlockDispenseBlacklistEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */