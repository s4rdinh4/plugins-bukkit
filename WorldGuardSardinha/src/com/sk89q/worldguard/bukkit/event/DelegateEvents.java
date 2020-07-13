/*    */ package com.sk89q.worldguard.bukkit.event;
/*    */ 
/*    */ import org.bukkit.event.Event;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class DelegateEvents
/*    */ {
/*    */   public static <T extends DelegateEvent> T setSilent(T event) {
/* 40 */     event.setSilent(true);
/* 41 */     return event;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static <T extends DelegateEvent> T setSilent(T event, boolean silent) {
/* 53 */     event.setSilent(silent);
/* 54 */     return event;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static <T extends Handleable> T setAllowed(T event, boolean allowed) {
/* 66 */     if (allowed) {
/* 67 */       event.setResult(Event.Result.ALLOW);
/*    */     }
/* 69 */     return event;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\bukkit\event\DelegateEvents.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */