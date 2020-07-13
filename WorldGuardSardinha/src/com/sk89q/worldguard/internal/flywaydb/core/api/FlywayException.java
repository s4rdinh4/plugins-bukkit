/*    */ package com.sk89q.worldguard.internal.flywaydb.core.api;
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
/*    */ public class FlywayException
/*    */   extends RuntimeException
/*    */ {
/*    */   public FlywayException(String message, Throwable cause) {
/* 29 */     super(message, cause);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public FlywayException(String message) {
/* 38 */     super(message);
/*    */   }
/*    */   
/*    */   public FlywayException() {}
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\internal\flywaydb\core\api\FlywayException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */