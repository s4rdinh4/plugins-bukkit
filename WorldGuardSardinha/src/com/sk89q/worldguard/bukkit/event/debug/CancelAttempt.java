/*    */ package com.sk89q.worldguard.bukkit.event.debug;
/*    */ 
/*    */ import com.google.common.base.Preconditions;
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
/*    */ public class CancelAttempt
/*    */ {
/*    */   private final boolean before;
/*    */   private final boolean after;
/*    */   private final StackTraceElement[] stackTrace;
/*    */   
/*    */   public CancelAttempt(boolean before, boolean after, StackTraceElement[] stackTrace) {
/* 43 */     Preconditions.checkNotNull(stackTrace, "stackTrace");
/* 44 */     this.before = before;
/* 45 */     this.after = after;
/* 46 */     this.stackTrace = stackTrace;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean getBefore() {
/* 55 */     return this.before;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean getAfter() {
/* 64 */     return this.after;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public StackTraceElement[] getStackTrace() {
/* 73 */     return this.stackTrace;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\bukkit\event\debug\CancelAttempt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */