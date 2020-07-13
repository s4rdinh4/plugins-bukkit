/*    */ package com.sk89q.worldguard.internal.flywaydb.core.internal.util.logging.slf4j;
/*    */ 
/*    */ import com.sk89q.worldguard.internal.flywaydb.core.internal.util.logging.Log;
/*    */ import org.apache.commons.logging.Log;
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
/*    */ public class Slf4jLog
/*    */   implements Log
/*    */ {
/*    */   private final Log logger;
/*    */   
/*    */   public Slf4jLog(Log logger) {
/* 35 */     this.logger = logger;
/*    */   }
/*    */   
/*    */   public void debug(String message) {
/* 39 */     this.logger.debug(message);
/*    */   }
/*    */   
/*    */   public void info(String message) {
/* 43 */     this.logger.info(message);
/*    */   }
/*    */   
/*    */   public void warn(String message) {
/* 47 */     this.logger.warn(message);
/*    */   }
/*    */   
/*    */   public void error(String message) {
/* 51 */     this.logger.error(message);
/*    */   }
/*    */   
/*    */   public void error(String message, Exception e) {
/* 55 */     this.logger.error(message, e);
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\internal\flywaydb\core\interna\\util\logging\slf4j\Slf4jLog.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */