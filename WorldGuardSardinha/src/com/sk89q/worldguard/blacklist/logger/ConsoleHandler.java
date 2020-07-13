/*    */ package com.sk89q.worldguard.blacklist.logger;
/*    */ 
/*    */ import com.sk89q.worldedit.blocks.ItemType;
/*    */ import com.sk89q.worldguard.blacklist.event.BlacklistEvent;
/*    */ import java.util.logging.Level;
/*    */ import java.util.logging.Logger;
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
/*    */ public class ConsoleHandler
/*    */   implements LoggerHandler
/*    */ {
/*    */   private String worldName;
/*    */   private final Logger logger;
/*    */   
/*    */   public ConsoleHandler(String worldName, Logger logger) {
/* 34 */     this.worldName = worldName;
/* 35 */     this.logger = logger;
/*    */   }
/*    */ 
/*    */   
/*    */   public void logEvent(BlacklistEvent event, String comment) {
/* 40 */     this.logger.log(Level.INFO, "[" + this.worldName + "] " + event.getLoggerMessage() + ((comment != null) ? (" (" + comment + ")") : ""));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static String getFriendlyItemName(int id) {
/* 51 */     ItemType type = ItemType.fromID(id);
/* 52 */     if (type != null) {
/* 53 */       return type.getName() + " (#" + id + ")";
/*    */     }
/* 55 */     return "#" + id;
/*    */   }
/*    */   
/*    */   public void close() {}
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\blacklist\logger\ConsoleHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */