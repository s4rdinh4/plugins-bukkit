/*    */ package com.sk89q.worldguard.internal.flywaydb.core.internal.util.logging.slf4j;
/*    */ 
/*    */ import com.sk89q.worldguard.internal.flywaydb.core.internal.util.logging.Log;
/*    */ import com.sk89q.worldguard.internal.flywaydb.core.internal.util.logging.LogCreator;
/*    */ import org.apache.commons.logging.LogFactory;
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
/*    */ public class Slf4jLogCreator
/*    */   implements LogCreator
/*    */ {
/*    */   public Log createLogger(Class<?> clazz) {
/* 27 */     return new Slf4jLog(LogFactory.getLog(clazz));
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\internal\flywaydb\core\interna\\util\logging\slf4j\Slf4jLogCreator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */