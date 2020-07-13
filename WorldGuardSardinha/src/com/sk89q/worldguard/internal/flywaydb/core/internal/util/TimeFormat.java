/*    */ package com.sk89q.worldguard.internal.flywaydb.core.internal.util;
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
/*    */ public class TimeFormat
/*    */ {
/*    */   public static String format(long millis) {
/* 36 */     return String.format("%02d:%02d.%03ds", new Object[] { Long.valueOf(millis / 60000L), Long.valueOf(millis % 60000L / 1000L), Long.valueOf(millis % 1000L) });
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\internal\flywaydb\core\interna\\util\TimeFormat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */