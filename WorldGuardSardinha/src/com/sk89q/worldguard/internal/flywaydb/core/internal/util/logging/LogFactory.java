/*    */ package com.sk89q.worldguard.internal.flywaydb.core.internal.util.logging;
/*    */ 
/*    */ import com.sk89q.worldguard.internal.flywaydb.core.internal.util.FeatureDetector;
/*    */ import com.sk89q.worldguard.internal.flywaydb.core.internal.util.logging.android.AndroidLogCreator;
/*    */ import com.sk89q.worldguard.internal.flywaydb.core.internal.util.logging.apachecommons.ApacheCommonsLogCreator;
/*    */ import com.sk89q.worldguard.internal.flywaydb.core.internal.util.logging.javautil.JavaUtilLogCreator;
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
/*    */ public class LogFactory
/*    */ {
/*    */   private static LogCreator logCreator;
/*    */   
/*    */   public static void setLogCreator(LogCreator logCreator) {
/* 43 */     LogFactory.logCreator = logCreator;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static Log getLog(Class<?> clazz) {
/* 53 */     if (logCreator == null) {
/* 54 */       FeatureDetector featureDetector = new FeatureDetector(Thread.currentThread().getContextClassLoader());
/* 55 */       if (featureDetector.isAndroidAvailable()) {
/* 56 */         logCreator = (LogCreator)new AndroidLogCreator();
/* 57 */       } else if (featureDetector.isApacheCommonsLoggingAvailable()) {
/* 58 */         logCreator = (LogCreator)new ApacheCommonsLogCreator();
/*    */       } else {
/* 60 */         logCreator = (LogCreator)new JavaUtilLogCreator();
/*    */       } 
/*    */     } 
/*    */     
/* 64 */     return logCreator.createLogger(clazz);
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\internal\flywaydb\core\interna\\util\logging\LogFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */