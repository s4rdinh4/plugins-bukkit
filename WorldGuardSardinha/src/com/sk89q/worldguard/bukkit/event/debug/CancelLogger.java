/*    */ package com.sk89q.worldguard.bukkit.event.debug;
/*    */ 
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
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
/*    */ public class CancelLogger
/*    */ {
/* 32 */   private List<CancelAttempt> entries = new ArrayList<CancelAttempt>();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void log(boolean before, boolean after, StackTraceElement[] stackTrace) {
/* 42 */     this.entries.add(new CancelAttempt(before, after, stackTrace));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public List<CancelAttempt> getCancels() {
/* 51 */     return (List<CancelAttempt>)ImmutableList.copyOf(this.entries);
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\bukkit\event\debug\CancelLogger.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */