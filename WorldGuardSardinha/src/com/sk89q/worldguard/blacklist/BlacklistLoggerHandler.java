/*    */ package com.sk89q.worldguard.blacklist;
/*    */ 
/*    */ import com.sk89q.worldguard.blacklist.event.BlacklistEvent;
/*    */ import com.sk89q.worldguard.blacklist.logger.LoggerHandler;
/*    */ import java.util.HashSet;
/*    */ import java.util.Set;
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
/*    */ public class BlacklistLoggerHandler
/*    */   implements LoggerHandler
/*    */ {
/* 33 */   private Set<LoggerHandler> handlers = new HashSet<LoggerHandler>();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void addHandler(LoggerHandler handler) {
/* 42 */     this.handlers.add(handler);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void removeHandler(LoggerHandler handler) {
/* 51 */     this.handlers.remove(handler);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void clearHandlers() {
/* 58 */     this.handlers.clear();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void logEvent(BlacklistEvent event, String comment) {
/* 68 */     for (LoggerHandler handler : this.handlers) {
/* 69 */       handler.logEvent(event, comment);
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void close() {
/* 78 */     for (LoggerHandler handler : this.handlers)
/* 79 */       handler.close(); 
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\blacklist\BlacklistLoggerHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */