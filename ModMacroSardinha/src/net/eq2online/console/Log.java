/*    */ package net.eq2online.console;
/*    */ 
/*    */ import com.mumfrey.liteloader.util.log.LiteLoaderLogger;
/*    */ import java.io.PrintWriter;
/*    */ import java.io.StringWriter;
/*    */ import java.text.MessageFormat;
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
/*    */ public final class Log
/*    */ {
/*    */   public static void info(String info) {
/* 23 */     LiteLoaderLogger.info(info.replace("%", "%%"), new Object[0]);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void info(Object data) {
/* 33 */     if (data == null)
/* 34 */       return;  LiteLoaderLogger.info(("[" + data.getClass().getSimpleName() + "] " + data.toString()).replace("%", "%%"), new Object[0]);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void info(String format, Object... args) {
/* 45 */     LiteLoaderLogger.info(MessageFormat.format(format, args).replace("%", "%%"), new Object[0]);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void printStackTrace(Throwable th) {
/* 55 */     StringWriter writer = new StringWriter();
/* 56 */     th.printStackTrace(new PrintWriter(writer));
/* 57 */     String stackTrace = writer.toString();
/* 58 */     info(stackTrace);
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\etc\TC_Macro.jar!\net\eq2online\console\Log.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */