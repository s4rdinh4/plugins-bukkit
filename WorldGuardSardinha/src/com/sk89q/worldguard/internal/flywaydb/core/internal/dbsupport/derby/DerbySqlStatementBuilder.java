/*    */ package com.sk89q.worldguard.internal.flywaydb.core.internal.dbsupport.derby;
/*    */ 
/*    */ import com.sk89q.worldguard.internal.flywaydb.core.internal.dbsupport.SqlStatementBuilder;
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
/*    */ public class DerbySqlStatementBuilder
/*    */   extends SqlStatementBuilder
/*    */ {
/*    */   protected String extractAlternateOpenQuote(String token) {
/* 26 */     if (token.startsWith("$$")) {
/* 27 */       return "$$";
/*    */     }
/* 29 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\igors\Desktop\worldguard-6.0.0-beta-05.jar!\com\sk89q\worldguard\internal\flywaydb\core\internal\dbsupport\derby\DerbySqlStatementBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */