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
/*    */ public final class ItemDestroyWithBlacklistEvent
/*    */   extends ItemBlacklistEvent
/*    */ {
/*    */   public ItemDestroyWithBlacklistEvent(@Nullable LocalPlayer player, Vector position, Target target) {
/* 38 */     super(player, position, target);
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDescription() {
/* 43 */     return "destroy with";
/*    */   }
/*    */ 
/*    */   
/*    */   public EventType getEventType() {
/* 48 */     return EventType.DESTROY_WITH;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Vector getLoggedPosition() {
/* 54 */     return getPosition();
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\blacklist\event\ItemDestroyWithBlacklistEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */