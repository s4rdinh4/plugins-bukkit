/*    */ package com.sk89q.worldguard.blacklist.event;
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
/*    */ public enum EventType
/*    */ {
/* 24 */   BREAK((Class)BlockBreakBlacklistEvent.class, "on-break"),
/* 25 */   PLACE((Class)BlockPlaceBlacklistEvent.class, "on-place"),
/* 26 */   INTERACT((Class)BlockInteractBlacklistEvent.class, "on-interact"),
/* 27 */   DISPENSE((Class)BlockDispenseBlacklistEvent.class, "on-dispense"),
/* 28 */   DESTROY_WITH((Class)ItemDestroyWithBlacklistEvent.class, "on-destroy-with"),
/* 29 */   ACQUIRE((Class)ItemAcquireBlacklistEvent.class, "on-acquire"),
/* 30 */   DROP((Class)ItemDropBlacklistEvent.class, "on-drop"),
/* 31 */   USE((Class)ItemUseBlacklistEvent.class, "on-use");
/*    */   
/*    */   private final Class<? extends BlacklistEvent> eventClass;
/*    */   private final String ruleName;
/*    */   
/*    */   EventType(Class<? extends BlacklistEvent> eventClass, String ruleName) {
/* 37 */     this.eventClass = eventClass;
/* 38 */     this.ruleName = ruleName;
/*    */   }
/*    */   
/*    */   public Class<? extends BlacklistEvent> getEventClass() {
/* 42 */     return this.eventClass;
/*    */   }
/*    */   
/*    */   public String getRuleName() {
/* 46 */     return this.ruleName;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\blacklist\event\EventType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */