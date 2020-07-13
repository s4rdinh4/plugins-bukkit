/*    */ package com.sk89q.worldguard.util.logging;
/*    */ 
/*    */ import com.google.common.base.Preconditions;
/*    */ import java.util.logging.Handler;
/*    */ import java.util.logging.LogRecord;
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
/*    */ public class RecordMessagePrefixer
/*    */   extends Handler
/*    */ {
/*    */   private final Logger parentLogger;
/*    */   private final String prefix;
/*    */   
/*    */   public RecordMessagePrefixer(Logger parentLogger, String prefix) {
/* 34 */     Preconditions.checkNotNull(parentLogger);
/* 35 */     Preconditions.checkNotNull(prefix);
/*    */     
/* 37 */     this.parentLogger = parentLogger;
/* 38 */     this.prefix = prefix;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void publish(LogRecord record) {
/* 44 */     record.setMessage(this.prefix + record.getMessage());
/* 45 */     this.parentLogger.log(record);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void flush() {}
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void close() throws SecurityException {}
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void register(Logger logger, String prefix) {
/* 63 */     Preconditions.checkNotNull(logger);
/*    */ 
/*    */     
/* 66 */     String className = RecordMessagePrefixer.class.getCanonicalName();
/*    */     
/* 68 */     logger.setUseParentHandlers(false);
/* 69 */     for (Handler handler : logger.getHandlers()) {
/* 70 */       if (handler.getClass().getCanonicalName().equals(className)) {
/* 71 */         logger.removeHandler(handler);
/*    */       }
/*    */     } 
/* 74 */     logger.addHandler(new RecordMessagePrefixer(logger.getParent(), prefix));
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguar\\util\logging\RecordMessagePrefixer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */