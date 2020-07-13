/*    */ package com.sk89q.worldguard.blacklist;
/*    */ 
/*    */ import com.sk89q.worldguard.blacklist.event.BlacklistEvent;
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
/*    */ class TrackedEvent
/*    */ {
/*    */   @Nullable
/*    */   private BlacklistEvent lastEvent;
/* 30 */   private long time = 0L;
/*    */   
/*    */   public boolean matches(BlacklistEvent other) {
/* 33 */     if (this.lastEvent == null) {
/* 34 */       return false;
/*    */     }
/* 36 */     long now = System.currentTimeMillis();
/* 37 */     return (other.getEventType() == this.lastEvent.getEventType() && this.time > now - 3000L);
/*    */   }
/*    */   
/*    */   public void resetTimer() {
/* 41 */     this.time = System.currentTimeMillis();
/*    */   }
/*    */   
/*    */   public void setLastEvent(@Nullable BlacklistEvent lastEvent) {
/* 45 */     this.lastEvent = lastEvent;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\blacklist\TrackedEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */